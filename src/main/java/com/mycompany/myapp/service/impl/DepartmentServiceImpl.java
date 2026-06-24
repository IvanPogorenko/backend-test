package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Department;
import com.mycompany.myapp.repository.DepartmentRepository;
import com.mycompany.myapp.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Department}.
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private static final Logger LOG = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Mono<Department> save(Department department) {
        LOG.debug("Request to save Department : {}", department);
        return departmentRepository.save(department);
    }

    @Override
    public Mono<Department> update(Department department) {
        LOG.debug("Request to update Department : {}", department);
        return departmentRepository.save(department);
    }

    @Override
    public Mono<Department> partialUpdate(Department department) {
        LOG.debug("Request to partially update Department : {}", department);

        return departmentRepository
            .findById(department.getId())
            .map(existingDepartment -> {
                if (department.getDepartmentName() != null) {
                    existingDepartment.setDepartmentName(department.getDepartmentName());
                }

                return existingDepartment;
            })
            .flatMap(departmentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<Department> findAll() {
        LOG.debug("Request to get all Departments");
        return departmentRepository.findAll();
    }

    /**
     *  Get all the departments where JobHistory is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Flux<Department> findAllWhereJobHistoryIsNull() {
        LOG.debug("Request to get all departments where JobHistory is null");
        return departmentRepository.findAllWhereJobHistoryIsNull();
    }

    public Mono<Long> countAll() {
        return departmentRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Department> findOne(Long id) {
        LOG.debug("Request to get Department : {}", id);
        return departmentRepository.findById(id);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Department : {}", id);
        return departmentRepository.deleteById(id);
    }
}
