package com.swa.swamobileteam.ui.authorization;

import com.swa.swamobileteam.data.authentication.UserAuthenticationRepository;
import com.swa.swamobileteam.data.authentication.UserAuthenticationRepositoryImpl;

import javax.inject.Inject;

import io.reactivex.Completable;

public class AuthorizationModel implements AuthorizationContract.Model {
    private UserAuthenticationRepository authRepository;

    @Inject
    public AuthorizationModel(UserAuthenticationRepository userAuthenticationRepository) {
        this.authRepository = userAuthenticationRepository;
    }

    @Override
    public Completable authenticate(String login, String password) {
        return authRepository.authenticate(login, password);
    }
}
