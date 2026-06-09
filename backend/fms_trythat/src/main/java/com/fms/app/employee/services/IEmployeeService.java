package com.fms.app.employee.services;

import java.util.List;

import com.fms.app.employee.models.Employee;

public interface IEmployeeService {

	public List<Employee> getEmployess();

	public Employee getEmployeeByID(int emId);

	public Employee saveEmployee(Employee emp);

	public void deleteEmployee(Employee emp);

	public long deleteEmployee(int empId);

	public boolean checkEmployeeExists(int empId);

	public Employee getEmpByEmail(String email);
}
