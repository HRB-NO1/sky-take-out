package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * Add employee
     * @param employeeDTO
     */
    void addEmployee(EmployeeDTO employeeDTO);

    /**
     * List employee
     * @param employeePageQueryDTO
     */
    PageResult employeePageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * Enable or disable employee
     * @param status
     * @param id
     */
    void EnableOrDisableEmployee(Integer status, Long id);

    /**
     * Get employee by id
     * @param id
     * @return
     */
    Employee getByID(Long id);

    /**
     * Update employee
     * @param employeeDTO
     */
    void updateEmployee(EmployeeDTO employeeDTO);
}
