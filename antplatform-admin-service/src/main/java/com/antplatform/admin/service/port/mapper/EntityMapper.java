package com.antplatform.admin.service.port.mapper;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/7/8 17:26:12
 * @description:
 */
public interface EntityMapper<D, E> {
    /**
     * DTO转Entity
     *
     * @param dto
     * @return
     */
    E toEntity(D dto);

    /**
     * Entity转DTO
     *
     * @param entity
     * @return
     */
    D toDto(E entity);

    /**
     * DTO集合转Entity集合
     *
     * @param dtoList
     * @return
     */
    Collection<E> toEntity(Collection<D> dtoList);

    /**
     * Entity集合转DTO集合
     *
     * @param entityList
     * @return
     */
    Collection<D> toDto(Collection<E> entityList);
}
