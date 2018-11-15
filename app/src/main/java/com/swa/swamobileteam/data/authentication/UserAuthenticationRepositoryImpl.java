package com.swa.swamobileteam.data.authentication;

import com.swa.swamobileteam.transportApi.CredentialsManager;
import com.swa.swamobileteam.transportApi.TransportApiClient;
import com.swa.swamobileteam.transportApi.authentication.LoginRequestParams;

import io.reactivex.Completable;

public class UserAuthenticationRepositoryImpl implements UserAuthenticationRepository {
    private TransportApiClient apiClient;
    private CredentialsManager credentialsManager;

    public UserAuthenticationRepositoryImpl(TransportApiClient apiClient, CredentialsManager credentialsManager) {
        this.apiClient = apiClient;
        this.credentialsManager = credentialsManager;
    }

    @Override
    public Completable authenticate(String login, String password) {
        return apiClient.login(new LoginRequestParams(login, password)).flatMapCompletable(loginResponse -> {
            credentialsManager.saveApiAuthenticationToken(loginResponse.getToken());
            return Completable.complete();
        });
    }
}
