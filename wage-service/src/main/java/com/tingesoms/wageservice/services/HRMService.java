package com.tingesoms.wageservice.services;

import com.tingesoms.wageservice.entities.*;
import com.tingesoms.wageservice.entities.enums.DetailType;
import com.tingesoms.wageservice.models.EmployeeModel;
import com.tingesoms.wageservice.models.WorkedDayModel;
import com.tingesoms.wageservice.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Stream;

@Service
public class HRMService {

    @Autowired
    WageRepository wageRepository;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    WorkedDayService workedDayService;
    @Autowired
    JustificationService justificationService;
    @Autowired
    ApprovalService approvalService;
    @Autowired
    WorkingDaysRepository workingDaysRepository;
    @Autowired
    WageDetailRepository wageDetailRepository;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Descuento restrasos

    // Calcula descuento según minutos de atraso.
    // > 10: 1%, > 25: 3%, >= 45: 6%
    public double lateDiscount(EmployeeModel employee, YearMonth yearMonth) {

        double lateDiscount = 0;
        double totalLate = 0;

        if (workedDayService.getWorkedDaysByRut(employee.getRut()) != null) {

            List<WorkedDayModel> monthWorkedDays = Stream.of(workedDayService.getWorkedDaysByRut(employee.getRut()))
                    .filter(wd -> wd.getDate().getMonth() == yearMonth.getMonth())
                    .filter(wd -> wd.getDate().getYear() == yearMonth.getYear())
                    .toList();

            long fixedMonthlyWage = employee.getCategory().getFixedMonthlyWage();

            for (WorkedDayModel monthWorkedDay : monthWorkedDays) {
                if (monthWorkedDay.getMinutesLate() > 10 &
                        monthWorkedDay.getMinutesLate() <= 25) {
                    totalLate = totalLate + 0.01;
                } else if (monthWorkedDay.getMinutesLate() > 25 &
                        monthWorkedDay.getMinutesLate() <= 45) {
                    totalLate = totalLate + 0.03;
                } else if (monthWorkedDay.getMinutesLate() > 45) {
                    totalLate = totalLate + 0.06;
                }
            }

            lateDiscount = totalLate * fixedMonthlyWage;

        } else {
            return lateDiscount;
        }

        return lateDiscount;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Descuento inasistencias
    public long totalAbsences(EmployeeModel employee, int workingDays, YearMonth yearMonth) {

        long attendances = Stream.of(workedDayService.getWorkedDaysByRut(employee.getRut()))
                .filter(wd -> wd.getDate().getMonth() == yearMonth.getMonth())
                .filter(wd -> wd.getDate().getYear() == yearMonth.getYear())
                .count();

        return workingDays - attendances;
    }

    // Calcula descuento según dias de inasistencia.
    // 15% de sueldo base por cada día.
    public double absenceDiscount(EmployeeModel employee, long totalAbsences) {

        return totalAbsences * (employee.getCategory().getFixedMonthlyWage() * 0.15 );
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Bonificación horas extra
    public double totalOvertime(EmployeeModel employee, YearMonth yearMonth) {

        double totalOvertime = 0;

        if (workedDayService.getWorkedDaysByRut(employee.getRut()) != null) {
            totalOvertime = Stream.of(workedDayService.getWorkedDaysByRut(employee.getRut()))
                    .filter(wd -> wd.getDate().getMonth() == yearMonth.getMonth())
                    .filter(wd -> wd.getDate().getYear() == yearMonth.getYear())
                    .mapToDouble(WorkedDayModel::getOvertime).sum();
        }

        return totalOvertime;
    }

    // Calcula bonificacion según horas extra.
    // 'A': 25000, 'B': 20000, 'C': 10000
    public double overtimeBonus(EmployeeModel employee, double totalOvertime) {

        double overtimeBonus;

        if (employee.getCategory().getType() == 'A') {
            overtimeBonus = totalOvertime * 25000;
        } else if (employee.getCategory().getType() == 'B') {
            overtimeBonus = totalOvertime * 20000;
        } else {
            overtimeBonus = totalOvertime * 10000;
        }

        return overtimeBonus;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Bonificación años de servicio
    public long serviceYears(EmployeeModel employee) {

        LocalDate to = LocalDate.now();
        Period period = Period.between(employee.getHireDate(), to);

        return period.getYears() + (period.getMonths() / 12) + (period.getDays() / 365);
    }

    // Calcula bonificacion según tiempo de servicio.
    // < 5: 0%, >= 5: 5%, >= 10: 8%, >= 15: 11%, >= 20: 14% >= 25: 17%
    public double serviceYearsBonus(EmployeeModel employee, Long serviceYears) {

        double serviceYearsBonus = 0;

        if (serviceYears >= 5 && serviceYears < 10) {
            serviceYearsBonus = employee.getCategory().getFixedMonthlyWage() * 0.05;
        } else if (serviceYears >= 10 && serviceYears < 15) {
            serviceYearsBonus = employee.getCategory().getFixedMonthlyWage() * 0.08;
        } else if (serviceYears >= 15 && serviceYears < 20) {
            serviceYearsBonus = employee.getCategory().getFixedMonthlyWage() * 0.11;
        } else if (serviceYears >= 20 && serviceYears < 25) {
            serviceYearsBonus = employee.getCategory().getFixedMonthlyWage() * 0.14;
        } else if (serviceYears >= 25) {
            serviceYearsBonus = employee.getCategory().getFixedMonthlyWage() * 0.17;
        }

        return serviceYearsBonus;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //SUELDO
    public void createWage(EmployeeModel employee) {

        if (workedDayService.getWorkedDaysByRut(employee.getRut()) != null) {

            List<WorkedDayModel> workedDays = List.of(workedDayService.getWorkedDaysByRut(employee.getRut()));
            LocalDate lastDate = workedDays.get(workedDays.size() - 1).getDate();
            YearMonth yearMonth = YearMonth.from(lastDate);
            WageEntity newWage = new WageEntity();

            newWage.setDate(LocalDate.now());
            newWage.setEmployeeRut(employee.getRut());
            this.wageRepository.save(newWage);

            //SUELDO FIJO
            WageDetailEntity fixedMonthlyWage = new WageDetailEntity();
            fixedMonthlyWage.setName("FMW");
            fixedMonthlyWage.setWage(newWage);
            fixedMonthlyWage.setType(DetailType.POSITIVE_NI);
            fixedMonthlyWage.setAmount(BigDecimal.valueOf(employee.getCategory().getFixedMonthlyWage()));
            this.wageDetailRepository.save(fixedMonthlyWage);

            //BONO AñOS DE SERVICIO
            WageDetailEntity serviceYearsBonus = new WageDetailEntity();
            serviceYearsBonus.setName("SY-B");
            serviceYearsBonus.setWage(newWage);
            serviceYearsBonus.setType(DetailType.POSITIVE_NI);

            serviceYearsBonus.setAmount(BigDecimal.valueOf(
                    serviceYearsBonus(employee, serviceYears(employee))));

            this.wageDetailRepository.save(serviceYearsBonus);

            //BONO HORAS EXTRAS
            WageDetailEntity overtimeBonus = new WageDetailEntity();
            overtimeBonus.setName("OT-B");
            overtimeBonus.setWage(newWage);
            overtimeBonus.setType(DetailType.POSITIVE_NI);
            if (Stream.of(approvalService.getApprovalsByRut(employee.getRut()))
                    .filter(a -> workedDays.get(workedDays.size() - 1).getDate().getMonth().equals(a.getApprovalDate().getMonth()))
                    .findAny()
                    .orElse(null) != null ) {

                overtimeBonus.setAmount(BigDecimal.valueOf(
                        overtimeBonus(employee, totalOvertime(employee, yearMonth))));

            } else { overtimeBonus.setAmount(BigDecimal.valueOf(0)); }
            this.wageDetailRepository.save(overtimeBonus);

            //DESCUENTO ATRASOS
            WageDetailEntity lateDiscount = new WageDetailEntity();
            lateDiscount.setName("L-D");
            lateDiscount.setWage(newWage);
            lateDiscount.setType(DetailType.NEGATIVE_NI);

            lateDiscount.setAmount(BigDecimal.valueOf(lateDiscount(employee, yearMonth)));

            this.wageDetailRepository.save(lateDiscount);

            //DESCUENTO INASISTENCIAS
            WageDetailEntity absenceDiscount = new WageDetailEntity();
            absenceDiscount.setName("A-D");
            absenceDiscount.setWage(newWage);
            absenceDiscount.setType(DetailType.NEGATIVE_NI);
            if (Stream.of(justificationService.getJustificationsByRut(employee.getRut()))
                    .filter(j -> workedDays.get(workedDays.size() - 1).getDate().getMonth().equals(j.getJustificationDate().getMonth()))
                    .filter(j -> j.getStatus().equals(Boolean.TRUE))
                    .findAny()
                    .orElse(null) == null ) {

                WorkingDaysEntity days = workingDaysRepository.findByMonth((newWage.getDate().getMonth().getValue()));

                absenceDiscount.setAmount(BigDecimal.valueOf(
                        absenceDiscount(employee, totalAbsences(employee, days.getAmount(), yearMonth))));

            } else { absenceDiscount.setAmount(BigDecimal.valueOf(0)); }
            this.wageDetailRepository.save(absenceDiscount);

            //SUELDO BRUTO
            WageDetailEntity grossWage = new WageDetailEntity();
            grossWage.setName("GW");
            grossWage.setWage(newWage);
            grossWage.setType(DetailType.POSITIVE_I);
            BigDecimal bonus = fixedMonthlyWage.getAmount().add(serviceYearsBonus.getAmount());
            BigDecimal discount = ( overtimeBonus.getAmount().add(lateDiscount.getAmount()) ).add(absenceDiscount.getAmount());
            grossWage.setAmount(bonus.subtract(discount));
            this.wageDetailRepository.save(grossWage);

            //DESCUENTO PENSION
            WageDetailEntity pensionContribution = new WageDetailEntity();
            pensionContribution.setName("PC");
            pensionContribution.setWage(newWage);
            pensionContribution.setType(DetailType.NEGATIVE_I);
            pensionContribution.setAmount(grossWage.getAmount().multiply(BigDecimal.valueOf(0.1)));
            this.wageDetailRepository.save(pensionContribution);

            //DESCUENTO PLAN DE SALUD
            WageDetailEntity healthContribution = new WageDetailEntity();
            healthContribution.setName("HC");
            healthContribution.setWage(newWage);
            healthContribution.setType(DetailType.NEGATIVE_I);
            healthContribution.setAmount(grossWage.getAmount().multiply(BigDecimal.valueOf(0.08)));
            this.wageDetailRepository.save(healthContribution);

            //SUELDO LIQUIDO
            WageDetailEntity net_pay = new WageDetailEntity();
            net_pay.setName("NP");
            net_pay.setWage(newWage);
            net_pay.setType(DetailType.POSITIVE_I);
            net_pay.setAmount(grossWage.getAmount().subtract(pensionContribution.getAmount().add(healthContribution.getAmount())));
            this.wageDetailRepository.save(net_pay);
        }
    }

    public void generateWages() {
        try {
            List<EmployeeModel> employees = List.of(this.employeeService.getAllEmployees());
            employees.forEach(this::createWage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
