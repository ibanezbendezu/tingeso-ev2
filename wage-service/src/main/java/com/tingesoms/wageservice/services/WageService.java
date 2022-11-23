package com.tingesoms.wageservice.services;

import com.tingesoms.wageservice.entities.*;
import com.tingesoms.wageservice.repositories.*;
import com.tingesoms.wageservice.web.model.WageDetailVo;
import com.tingesoms.wageservice.web.model.WageVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WageService {

    private final WageRepository wageRepository;
    private final EmployeeService employeeService;

    public WageService(WageRepository wageRepository, EmployeeService employeeService) {
        this.wageRepository = wageRepository;
        this.employeeService = employeeService;
    }

    public List<WageVo> getWages(){
        List<WageEntity> wages = wageRepository.findAll();

        List<WageVo> wagesVo = new ArrayList<>();

        for (WageEntity wage : wages) {
            WageVo vo = new WageVo();
            vo.setId(wage.getId());
            vo.setDate(wage.getDate());
            vo.setDetail(wage.getDetail().stream().map(wd -> {
                WageDetailVo detailVo = new WageDetailVo();
                detailVo.setId(wd.getId());
                detailVo.setName(wd.getName());
                detailVo.setType(wd.getType());
                detailVo.setAmount(wd.getAmount());
                return detailVo;
            }).collect(Collectors.toList()));
            vo.setEmployee(employeeService.employeeByRut(wage.getEmployeeRut()));

            wagesVo.add(vo);
        }

        return wagesVo;
    }
}
