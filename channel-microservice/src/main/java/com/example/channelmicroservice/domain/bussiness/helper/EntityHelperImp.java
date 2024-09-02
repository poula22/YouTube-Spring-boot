package com.example.channelmicroservice.domain.bussiness.helper;

import com.example.domain.entityException.CustomEntityException;
import com.example.domain.entityHelper.EntityHelper;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EntityHelperImp implements EntityHelper {
    @Override
    public <T> EntityHelper notNull(T entity) throws CustomEntityException {
        if (entity == null) throw new CustomEntityException("data is null");
        return this;
    }
}