package com.example.usermicroservice.bussiness;

import com.example.domain.entityException.CustomEntityException;
import com.example.usermicroservice.domain.bussiness.exception.CustomAuthException;
import com.example.usermicroservice.domain.bussiness.mapper.SignUpRequestDtoMapper;
import com.example.usermicroservice.domain.bussiness.mapper.UserMapper;
import com.example.usermicroservice.domain.bussiness.model.login.LoginRequestDto;
import com.example.usermicroservice.domain.bussiness.model.AuthResponseDto;
import com.example.usermicroservice.domain.bussiness.model.signUp.SignUpRequestDto;
import com.example.usermicroservice.domain.bussiness.AuthService;
import com.example.usermicroservice.domain.bussiness.UserService;
import com.example.usermicroservice.domain.bussiness.helper.EntityHelperImp;
import com.example.usermicroservice.domain.entity.UserEntity;
import com.example.usermicroservice.persistance.UserJpaRepository;
import com.example.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, AuthService {
    private final UserJpaRepository userJpaRepository;
    private final EntityHelperImp entityHelper;
    private final JwtUtil jwtUtil = new JwtUtil();

    @Override
    public UserEntity findUserById(long id) throws CustomEntityException {
        UserEntity user = userJpaRepository
                .findById(id)
                .orElse(null);
        entityHelper.notNull(user);
        return user;
    }
    @Override
    public UserEntity saveAndUpdateUserInfo(UserEntity userEntity) throws CustomEntityException {
        entityHelper.notNull(userEntity);
        userJpaRepository.save(userEntity);
        return userEntity;
    }
    @Override
    public AuthResponseDto login(LoginRequestDto request) throws CustomAuthException, CustomEntityException {
        entityHelper.notNull(request);

        UserEntity user = userJpaRepository
                .findUserEntityByNameAndPassword(request.userName(), request.password())
                .orElse(null);

        if (user == null) throw new CustomAuthException("invalid userName Or Password");

        String token = jwtUtil.generateToken(UserMapper.INSTANCE.fromEntityToSecurity(user));
        return new AuthResponseDto(token);
    }
    @Override
    public AuthResponseDto signUp(SignUpRequestDto user) throws CustomAuthException, CustomEntityException {
        entityHelper.notNull(user);
        UserEntity entity = SignUpRequestDtoMapper.INSTANCE.toUserEntity(user);

        try {
            userJpaRepository.save(entity);
        } catch (Exception e) {
            throw new CustomAuthException("user already exist");
        }

        String token = jwtUtil.generateToken(UserMapper.INSTANCE.fromEntityToSecurity(entity));
        return new AuthResponseDto(token);
    }
}