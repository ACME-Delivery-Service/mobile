package com.swa.swamobileteam.ui.authorization;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.swa.swamobileteam.ui.deliveryGroups.DeliveryGroupsContract;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AuthorizationPresenter implements AuthorizationContract.Presenter {

    private final String LOGIN = "login";
    private final String PASSWORD = "password";

    @Nullable
    private AuthorizationContract.View view;
    private AuthorizationContract.Model model;
    private CompositeDisposable disposable = new CompositeDisposable();
    private SharedPreferences preferences;

    @Inject
    AuthorizationPresenter(AuthorizationContract.Model model, SharedPreferences preferences) {
        this.model = model;
        this.preferences = preferences;
    }

    @Override
    public void attachView(AuthorizationContract.View view, boolean isNew) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void stop() {
        if (disposable != null) {
            disposable.clear();
        }
    }

    @Override
    public void login() {
        if (view != null) {
            String login = view.getLogin();
            String password = view.getPassword();
            if (login.isEmpty()) {
                view.showNoLogin();
            } else if (password.isEmpty()) {
                view.showNoPassword();
            } else {
                makeLoginRequest(login, password);
            }
        }
    }

    @Override
    public void autoLogin() {
        if (view != null) {
            if (!preferences.getString(LOGIN, "").isEmpty() &&
                    !preferences.getString(PASSWORD, "").isEmpty()) {
                makeLoginRequest(preferences.getString(LOGIN, ""), preferences.getString(PASSWORD, ""));
            }
        }
    }

    private void makeLoginRequest(String login, String password) {
        if (view != null) {
            view.showLoadingDialog();
            disposable.add(model.authenticate(login, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {
                                view.hideLoadingDialog();
                                preferences.edit().putString(LOGIN, login).apply();
                                preferences.edit().putString(PASSWORD, password).apply();
                                view.successLogin();
                        },
                            (error) -> {
                                view.hideLoadingDialog();;
                                view.showWrongLogin();
                            }
                    )
            );
        }
    }
}
