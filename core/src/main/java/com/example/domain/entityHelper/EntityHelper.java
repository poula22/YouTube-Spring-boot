package com.example.domain.entityHelper;

import com.example.domain.entityException.CustomEntityException;

public interface EntityHelper {
    <T> EntityHelper notNull(T entity) throws CustomEntityException;
}
