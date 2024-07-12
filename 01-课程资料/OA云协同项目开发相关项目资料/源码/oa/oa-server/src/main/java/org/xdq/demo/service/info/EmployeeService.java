package org.xdq.demo.service.info;

import org.xdq.demo.common.page.PageVo;
import org.xdq.demo.dto.EmpDto;
import org.xdq.demo.dto.EmpQueryDto;
import org.xdq.demo.model.Dep;
import org.xdq.demo.model.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
    PageVo<Employee> getEmployeePage(EmpQueryDto empQueryDto);

    List<Dep> getAllEnableDepList();

    void addEmp(EmpDto empDto);


    void updateEmp(EmpDto empDto);

    void removeEmp(String... ids);

    void changeStatus(String id, Integer status);

    void createUser(Map<String, String> userIdMap);

    void cancelUser(Map<String, String> userIdMap);

    void setLeader(Map<String, String> empIdMap);
}
