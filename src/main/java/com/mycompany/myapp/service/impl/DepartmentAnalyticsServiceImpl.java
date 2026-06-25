package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Department;
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.domain.Job;
import com.mycompany.myapp.repository.DepartmentRepository;
import com.mycompany.myapp.repository.EmployeeRepository;
import com.mycompany.myapp.repository.JobRepository;
import com.mycompany.myapp.service.DepartmentAnalyticsService;
import com.mycompany.myapp.service.dto.DepartmentAnalyticsDTO;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Реализация сервиса для получения агрегированной аналитики по подразделению.
 * <p>
 * Вычисляет статистику по подразделению с использованием реактивных операций:
 * общее количество сотрудников, зарплатная статистика (средняя, минимальная,
 * максимальная), количество руководителей и распределение по должностям.
 * <p>
 * Все операции выполняются без блокировки с использованием Mono/Flux.
 *
 * @see DepartmentAnalyticsService
 * @see DepartmentAnalyticsDTO
 */
@Service
@Transactional
public class DepartmentAnalyticsServiceImpl implements DepartmentAnalyticsService {

    private static final Logger LOG = LoggerFactory.getLogger(DepartmentAnalyticsServiceImpl.class);

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final JobRepository jobRepository;

    /**
     * Конструктор сервиса аналитики подразделений.
     *
     * @param departmentRepository репозиторий подразделений
     * @param employeeRepository   репозиторий сотрудников
     * @param jobRepository        репозиторий должностей
     */
    public DepartmentAnalyticsServiceImpl(
        DepartmentRepository departmentRepository,
        EmployeeRepository employeeRepository,
        JobRepository jobRepository
    ) {
        this.departmentRepository = departmentRepository;
        this.employeeRepository = employeeRepository;
        this.jobRepository = jobRepository;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Реализация использует реактивный подход для получения данных из нескольких
     * источников и их агрегирования в единый DTO. Если подразделение не найдено,
     * возвращается пустой Mono.
     */
    @Override
    @Transactional(readOnly = true)
    public Mono<DepartmentAnalyticsDTO> getAnalytics(Long departmentId) {
        LOG.debug("Request to get analytics for Department : {}", departmentId);

        return departmentRepository
            .findById(departmentId)
            .flatMap(department -> buildAnalytics(departmentId, department));
    }

    /**
     * Построить агрегированную аналитику для подразделения.
     * <p>
     * Параллельно получает сотрудников и должности подразделения,
     * затем вычисляет все необходимые статистические показатели.
     *
     * @param departmentId идентификатор подразделения
     * @param department   сущность подразделения
     * @return {@link Mono} с {@link DepartmentAnalyticsDTO}
     */
    private Mono<DepartmentAnalyticsDTO> buildAnalytics(Long departmentId, Department department) {
        Flux<Employee> employeesFlux = employeeRepository.findByDepartment(departmentId).cache();
        Flux<Job> jobsFlux = jobRepository.findByEmployeeDepartmentId(departmentId).cache();

        return Mono.zip(
            employeesFlux.collectList(),
            jobsFlux.collectList(),
            employeeRepository.findManagerIdsByDepartmentId(departmentId).collectList()
        ).map(tuple -> {
            var employees = tuple.getT1();
            var jobs = tuple.getT2();
            var managerIds = tuple.getT3();

            int employeeCount = employees.size();

            // Зарплатная статистика
            Double averageSalary = employees
                .stream()
                .map(Employee::getSalary)
                .filter(salary -> salary != null)
                .mapToLong(Long::longValue)
                .average()
                .orElse(0.0);

            Long minSalary = employees
                .stream()
                .map(Employee::getSalary)
                .filter(salary -> salary != null)
                .min(Long::compareTo)
                .orElse(0L);

            Long maxSalary = employees
                .stream()
                .map(Employee::getSalary)
                .filter(salary -> salary != null)
                .max(Long::compareTo)
                .orElse(0L);

            // Количество сотрудников без руководителя
            long employeesWithoutManager = employees
                .stream()
                .filter(employee -> employee.getManagerId() == null)
                .count();

            // Количество уникальных руководителей в подразделении
            long managerCount = managerIds.size();

            // Распределение по должностям
            Map<String, Integer> jobDistribution = new LinkedHashMap<>();
            jobs.forEach(job -> {
                String title = job.getJobTitle() != null ? job.getJobTitle() : "Unknown";
                jobDistribution.merge(title, 1, Integer::sum);
            });

            return new DepartmentAnalyticsDTO(
                department.getId(),
                department.getDepartmentName(),
                employeeCount,
                averageSalary,
                minSalary,
                maxSalary,
                managerCount,
                employeesWithoutManager,
                jobDistribution
            );
        });
    }
}
