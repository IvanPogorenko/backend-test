package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.DepartmentAnalyticsDTO;
import reactor.core.publisher.Mono;

/**
 * Сервисный интерфейс для получения агрегированной аналитики по подразделению.
 * <p>
 * Предоставляет метод для вычисления статистики по подразделению,
 * включая количество сотрудников, зарплатную статистику, количество руководителей
 * и распределение сотрудников по должностям.
 *
 * @see com.mycompany.myapp.domain.Department
 * @see DepartmentAnalyticsDTO
 */
public interface DepartmentAnalyticsService {
    /**
     * Получить агрегированную статистику по подразделению.
     * <p>
     * Вычисляет следующие показатели:
     * <ul>
     *   <li>общее количество сотрудников подразделения;</li>
     *   <li>среднюю заработную плату;</li>
     *   <li>минимальную заработную плату;</li>
     *   <li>максимальную заработную плату;</li>
     *   <li>количество руководителей (сотрудников, являющихся руководителями хотя бы одного сотрудника);</li>
     *   <li>количество сотрудников без руководителя;</li>
     *   <li>распределение сотрудников по должностям.</li>
     * </ul>
     *
     * @param departmentId идентификатор подразделения
     * @return {@link Mono} с {@link DepartmentAnalyticsDTO}, или пустой Mono, если подразделение не найдено
     */
    Mono<DepartmentAnalyticsDTO> getAnalytics(Long departmentId);
}
