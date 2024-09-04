package com.example.usermicroservice.domain.bussiness.helper;

import com.example.domain.entityException.CustomEntityException;
import com.example.domain.entityHelper.EntityHelper;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class EntityHelperImp implements EntityHelper {
//    private Consumer<Throwable> onCatch;

    @Override
    public <T> EntityHelper notNull(T entity) throws CustomEntityException {
        if (entity == null) throw new CustomEntityException("data is null");
        return this;
    }
//    public EntityHelperImp isEmailValid(String email) throws CustomEntityException {
//        boolean isEmailValid = RequestValidator.validate(email, EmailValidator.VALIDATOR);
//        if (!isEmailValid) throw new CustomEntityException("Email is not valid");
//        return this;
//    }
//    public EntityHelperImp isPasswordValid(String password) throws CustomAuthException {
//        boolean isPasswordValid = RequestValidator.validate(password, EmailValidator.VALIDATOR);
//        if (!isPasswordValid) throw new CustomAuthException("Password is not valid");
//        return this;
//    }


//    private void catchException(Throwable t)  {
//        if (onCatch == null) throw new RuntimeException(t);
//        onCatch.accept(t);
//    }
//
//    public void onCatch(Consumer<Throwable> onCatch) {
//        this.onCatch = onCatch;
//    }
//    public EntityHelper onProcess(Runnable onProcess){
//        try {
//            onProcess.run();
//        }catch (Exception e) {
//            catchException(e);
//        }
//        return this;
//    }
}