package com.sky.controller.admin;

import com.github.pagehelper.PageHelper;
import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "EmployeeManagement")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "EmployeeLogin")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation(value = "EmployeeLogout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * Add employee
     *
     * @param employeeDTO
     * @return
     */
    @PostMapping
    @ApiOperation(value = "AddEmployee")
    public Result addEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("Add employee: {}", employeeDTO);
        employeeService.addEmployee(employeeDTO);
        return Result.success();
    }

    /**
     * List employees
     *
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "ListEmployees")
    public Result<PageResult> employeePageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("List employees: {}", employeePageQueryDTO);
        PageResult pageResult = employeeService.employeePageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * Enable or disable employee
     *
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "EnableOrDisableEmployee")
    public Result EnableOrDisableEmployee(@PathVariable Integer status, Long id) {
        log.info("Enable or disable employee: status={}, id={}", status, id);
        employeeService.EnableOrDisableEmployee(status, id);
        return Result.success();
    }

    /**
     * Get employee by id
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "GetEmployeeByID")
    public Result<Employee> getByID(@PathVariable Long id) {
        log.info("Get employee by id: id={}", id);
        return Result.success(employeeService.getByID(id));
    }

    /**
     * Update employee
     *
     * @param employeeDTO
     * @return
     */
    @PutMapping
    @ApiOperation(value = "UpdateEmployee")
    public Result updateEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("Update employee: {}", employeeDTO);
        employeeService.updateEmployee(employeeDTO);
        return Result.success();
    }

}
