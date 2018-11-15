package com.swa.swamobileteam.ui.delivery;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.swa.swamobileteam.R;
import com.swa.swamobileteam.data.deliveries.DeliveryOrderStatus;
import com.swa.swamobileteam.ui.MainActivity;
import com.swa.swamobileteam.ui.delivery.view.ParcelView;
import com.swa.swamobileteam.ui.finishDelivery.FinishDeliveryActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;


public class DeliveryActivity extends AppCompatActivity implements DeliveryContract.View {
    private final static String DELIVERY_ID = "delivery_id";

    @BindView(R.id.appbar_delivery)
    AppBarLayout appBar;
    @BindView(R.id.toolbar_delivery)
    Toolbar toolbar;
    @BindView(R.id.layout_main)
    ScrollView mainLayout;
    @BindView(R.id.timeTextView)
    TextView time;
    @BindView(R.id.text_time_remaining)
    TextView timeRemaining;
    @BindView(R.id.text_client_name)
    TextView clientName;
    @BindView(R.id.phoneTextView)
    TextView phone;
    @BindView(R.id.addressTextView)
    TextView address;
    @BindView(R.id.list_parcels)
    LinearLayout parcels;
    @BindView(R.id.button_action)
    AppCompatButton actionButton;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private AlertDialog dialog;

    @Inject
    DeliveryContract.Presenter presenter;

    public static Intent newInstance(Context context, int deliveryId) {
        Intent intent = new Intent(context, DeliveryActivity.class);
        intent.putExtra(DELIVERY_ID, deliveryId);
        return intent;
    }

    public DeliveryActivity() {
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);
        ButterKnife.bind(this);
        AndroidInjection.inject(this);
        setToolbar();
        presenter.attachView(this, true);
        presenter.getInfo(getIntent().getExtras().getInt(DELIVERY_ID));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter.stop();
    }


    @Override
    public void setTimePeriod(String time) {
        this.time.setText(time);
    }

    @Override
    public void setAddress(String address) {
        this.address.setText(address);
    }

    @Override
    public void setName(String name) {
        this.clientName.setText(name);
    }

    @Override
    public void setPhone(String phone) {
        this.phone.setText(phone);
    }

    @Override
    public void setTimeRemaining(long time) {
        this.timeRemaining.setText(getString(R.string.text_time_remaining, time));
    }

    @Override
    public void setActionButton(DeliveryOrderStatus deliveryOrderStatus) {
        if (deliveryOrderStatus.equals(DeliveryOrderStatus.PENDING)) {
            actionButton.setText(getString(R.string.text_mark_as_current));
        }
        else {
            actionButton.setText(getString(R.string.text_finish));
        }
    }


    @Override
    public void navigateToMap(Uri coordsUri) {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, coordsUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    public void callPhone(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    @Override
    public void addParcel(View parcel) {
        parcels.addView(parcel);
    }

    @Override
    public ParcelView createParcelView() {
        return new ParcelView(getLayoutInflater());
    }

    @Override
    public Resources getResource() {
        return getResources();
    }

    @Override
    public void hideProgressBar() {
        this.progressBar.setVisibility(View.GONE);
        this.appBar.setVisibility(View.VISIBLE);
        this.mainLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void navigateToFinishDeliveryActivity(int id, String client, String address) {
        Intent intent = FinishDeliveryActivity.newInstance(this, id, client, address);
        startActivity(intent);
    }

    @Override
    public void navigateToMainActivity() {
        Toast.makeText(this, getString(R.string.text_success_mark, presenter.getId()), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
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

    @Override
    public void showLoadingError() {
        Toast.makeText(this, getString(R.string.text_loading_error), Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.image_button_call)
    public void callClient() {
        presenter.callClient();
    }

    @OnClick(R.id.button_to_map)
    public void openMap() {
        presenter.openMap();
    }

    @OnClick(R.id.button_contact_operator)
    public void callOperator() {
        presenter.callOperator();
    }

    @OnClick(R.id.button_action)
    public void onAction(){
        presenter.onAction();
    }

    private void setToolbar() {
        toolbar.setTitle("Delivery #" + String.valueOf(getIntent().getIntExtra(DELIVERY_ID, -1)));
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.navigation_back));
        toolbar.setNavigationOnClickListener((v) -> onBackPressed());
    }
}
