package com.swa.swamobileteam.ui.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.swa.swamobileteam.R;
import com.swa.swamobileteam.ui.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTouch;
import dagger.android.AndroidInjection;

public class AuthorizationActivity extends AppCompatActivity implements AuthorizationContract.View {

    @BindView(R.id.edit_login_field)
    TextInputEditText login;
    @BindView(R.id.edit_password_field)
    TextInputEditText password;
    @BindView(R.id.text_login_wrapper)
    TextInputLayout loginWrapper;
    @BindView(R.id.text_password_wrapper)
    TextInputLayout passwordWrapper;

    private AlertDialog dialog;

    @Inject
    AuthorizationContract.Presenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);
        presenter.attachView(this, true);
        presenter.autoLogin();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter.stop();
    }

    @Override
    public String getLogin() {
        return login.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return password.getText().toString().trim();
    }

    @Override
    public void resetPassword() {
        password.setText("");
    }

    @Override
    public void showNoLogin() {
        loginWrapper.setError(getString(R.string.msg_no_username));
        loginWrapper.setErrorTextColor(ContextCompat.getColorStateList(this, R.color.colorBlack));
        passwordWrapper.setErrorEnabled(true);
        passwordWrapper.setError(null);
    }

    @Override
    public void showNoPassword() {
        passwordWrapper.setError(getString(R.string.msg_no_password));
        passwordWrapper.setErrorTextColor(ContextCompat.getColorStateList(this, R.color.colorBlack));
        loginWrapper.setErrorEnabled(true);
        loginWrapper.setError(null);
    }

    @Override
    public void showWrongLogin() {
        passwordWrapper.setError(" ");
        loginWrapper.setError(getString(R.string.msg_wrong_username));
        loginWrapper.setErrorTextColor(ContextCompat.getColorStateList(this, R.color.colorBlack));
    }

    @Override
    public void successLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoadingDialog() {
        dialog = new AlertDialog.Builder(this)
                .setView(R.layout.item_loading_dialog)
                .setCancelable(false)
                .create();
        dialog.show();

    }

    @Override
    public void hideLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @OnClick(R.id.button_sign_in)
    public void onLogin(View view) {
        presenter.login();
    }

    @OnTouch(R.id.layout_container)
    public boolean hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (inputMethodManager != null)
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

    @OnEditorAction(R.id.edit_password_field)
    protected boolean onEditPasswordFinished(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            presenter.login();
        }
        return false;
    }


}
