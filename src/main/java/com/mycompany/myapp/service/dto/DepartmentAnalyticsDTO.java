package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * DTO для агрегированной статистики по подразделению.
 * <p>
 * Содержит информацию о количестве сотрудников, зарплатной статистике,
 * количестве руководителей и распределении сотрудников по должностям.
 *
 * @see com.mycompany.myapp.domain.Department
 */
public class DepartmentAnalyticsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long departmentId;

    private String departmentName;

    private int employeeCount;

    private Double averageSalary;

    private Long minSalary;

    private Long maxSalary;

    private long managerCount;

    private long employeesWithoutManager;

    private Map<String, Integer> jobDistribution;

    /**
     * Конструктор по умолчанию, необходимый для Jackson.
     */
    public DepartmentAnalyticsDTO() {
        // Пустой конструктор для Jackson десериализации
    }

    /**
     * Полный конструктор для создания DTO с агрегированной статистикой.
     *
     * @param departmentId           идентификатор подразделения
     * @param departmentName         название подразделения
     * @param employeeCount          общее количество сотрудников
     * @param averageSalary          средняя заработная плата
     * @param minSalary              минимальная заработная плата
     * @param maxSalary              максимальная заработная плата
     * @param managerCount           количество руководителей в подразделении
     * @param employeesWithoutManager количество сотрудников без руководителя
     * @param jobDistribution        распределение сотрудников по должностям
     */
    public DepartmentAnalyticsDTO(
        Long departmentId,
        String departmentName,
        int employeeCount,
        Double averageSalary,
        Long minSalary,
        Long maxSalary,
        long managerCount,
        long employeesWithoutManager,
        Map<String, Integer> jobDistribution
    ) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.employeeCount = employeeCount;
        this.averageSalary = averageSalary;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.managerCount = managerCount;
        this.employeesWithoutManager = employeesWithoutManager;
        this.jobDistribution = jobDistribution;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public Double getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(Double averageSalary) {
        this.averageSalary = averageSalary;
    }

    public Long getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Long minSalary) {
        this.minSalary = minSalary;
    }

    public Long getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Long maxSalary) {
        this.maxSalary = maxSalary;
    }

    public long getManagerCount() {
        return managerCount;
    }

    public void setManagerCount(long managerCount) {
        this.managerCount = managerCount;
    }

    public long getEmployeesWithoutManager() {
        return employeesWithoutManager;
    }

    public void setEmployeesWithoutManager(long employeesWithoutManager) {
        this.employeesWithoutManager = employeesWithoutManager;
    }

    public Map<String, Integer> getJobDistribution() {
        return jobDistribution;
    }

    public void setJobDistribution(Map<String, Integer> jobDistribution) {
        this.jobDistribution = jobDistribution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DepartmentAnalyticsDTO that = (DepartmentAnalyticsDTO) o;
        return Objects.equals(departmentId, that.departmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departmentId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DepartmentAnalyticsDTO{" +
            "departmentId=" + departmentId +
            ", departmentName='" + departmentName + "'" +
            ", employeeCount=" + employeeCount +
            ", averageSalary=" + averageSalary +
            ", minSalary=" + minSalary +
            ", maxSalary=" + maxSalary +
            ", managerCount=" + managerCount +
            ", employeesWithoutManager=" + employeesWithoutManager +
            ", jobDistribution=" + jobDistribution +
            "}";
    }
}
