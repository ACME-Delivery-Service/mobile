package com.swa.swamobileteam.ui.authorization;

import com.swa.swamobileteam.ui.base.BaseModel;
import com.swa.swamobileteam.ui.base.BasePresenter;
import com.swa.swamobileteam.ui.base.BaseView;

import io.reactivex.Completable;

public interface AuthorizationContract {
    interface View extends BaseView {
        String getLogin();

        String getPassword();

        void resetPassword();

        void showNoLogin();

        void showNoPassword();

        void showWrongLogin();

        void successLogin();

        void showLoadingDialog();

        void hideLoadingDialog();

    }

    interface Presenter extends BasePresenter<AuthorizationContract.View> {
        void login();

        void autoLogin();
    }

    /**
     * Model acts as a facade that combines different interchangeable parts to provide
     * an interface required for Presenter.
     */
    interface Model extends BaseModel {
        /**
         * Performs user authentication with given credentials.
         * @param login User's login.
         * @param password User's password.
         * @return onSuccess if user was successfully authenticated, onFailure otherwise.
         */
        Completable authenticate(String login, String password);
    }
}
