package po;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: liu
 * Date: 13-9-27
 * Time: 上午10:06
 * To change this template use File | Settings | File Templates.
 */
public class ConfigBean {

    private Employee employee;
    private java.util.List<Employee> employees;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
