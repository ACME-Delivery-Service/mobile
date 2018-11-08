package com.swa.swamobileteam.data.authentication;

import com.swa.swamobileteam.transportApi.TransportApiClient;

import io.reactivex.Completable;

public class UserAuthenticationRepositoryImpl implements UserAuthenticationRepository {
    private TransportApiClient apiClient;

    @Override
    public Completable authenticate(String login, String password) {
        if (login.equals("login") && password.equals("password")) {
            return Completable.complete();
        } else {
            return Completable.error(new Error("Incorrect Login or Password"));
        }
    }
}
