package com.swa.swamobileteam.ui.finishDelivery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.swa.swamobileteam.R;
import com.swa.swamobileteam.ui.MainActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class FinishDeliveryActivity extends AppCompatActivity implements FinishDeliveryContract.View {

    private static final String ID = "id";
    private static final String CLIENT = "client";
    private static final String ADDRESS = "address";

    @BindView(R.id.toolbar_finish)
    Toolbar toolbar;
    @BindView(R.id.text_address)
    TextView address;
    @BindView(R.id.text_date)
    TextView date;
    @BindView(R.id.text_time)
    TextView time;
    @BindView(R.id.text_client)
    TextView client;

    private AlertDialog dialog;

    @Inject
    FinishDeliveryContract.Presenter presenter;

    public static Intent newInstance(Context context, int id, String client, String address) {
        Intent intent = new Intent(context, FinishDeliveryActivity.class);
        intent.putExtra(ID, id);
        intent.putExtra(CLIENT, client);
        intent.putExtra(ADDRESS, address);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_delivery);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);
        setToolbar();
        setFields();
        presenter.attachView(this, true);
    }

    @Override
    public void navigateToDeliveriesList() {
        Toast.makeText(this,
                getString(R.string.text_delivery_finish, getIntent().getIntExtra(ID, -1)),
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoadingError() {
        Toast.makeText(this, getString(R.string.text_loading_error), Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoadingDialog() {
        dialog = new AlertDialog.Builder(this)
                .setView(R.layout.item_loading_dialog)
                .setCancelable(false)
                .create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @OnClick(R.id.button_finish_delivery)
    public void onFinish() {
        presenter.finish(getIntent().getIntExtra(ID, -1));
    }

    private void setToolbar() {
        toolbar.setTitle("Finish delivery #" + String.valueOf(getIntent().getIntExtra(ID, -1)));
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.navigation_back));
        toolbar.setNavigationOnClickListener((v) -> onBackPressed());
    }

    private void setFields() {
        address.setText(getIntent().getStringExtra(ADDRESS));
        date.setText("November 15, 2018");
        time.setText(getTime());
        client.setText(getIntent().getStringExtra(CLIENT));
    }

    private String getTime() {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        String hours;
        String minutes;
        if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
            hours = "0" + calendar.get(Calendar.HOUR_OF_DAY);
        }
        else {
            hours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        }
        if (calendar.get(Calendar.MINUTE) < 10) {
            minutes = "0" + calendar.get(Calendar.MINUTE);
        }
        else {
            minutes = String.valueOf(calendar.get(Calendar.MINUTE));
        }
        return hours + ":" + minutes;
    }
}