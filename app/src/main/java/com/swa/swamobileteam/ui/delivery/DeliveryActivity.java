package com.swa.swamobileteam.ui.delivery;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.swa.swamobileteam.R;
import com.swa.swamobileteam.ui.delivery.view.ParcelView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;


public class DeliveryActivity extends AppCompatActivity implements DeliveryContract.View {
    private final static String DELIVERY_ID = "delivery_id";

    @BindView(R.id.toolbar_delivery)
    Toolbar toolbar;
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

    @Inject
    DeliveryContract.Presenter presenter;

    public static Intent newInstance(Context context, int deliveryId) {
        Intent intent = new Intent(context, DeliveryActivity.class);
        intent.putExtra(DELIVERY_ID, deliveryId);
        return intent;
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

    private void setToolbar() {
        toolbar.setTitle("Parcel #" + getIntent().getStringExtra(DELIVERY_ID));
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.navigation_back));
        toolbar.setNavigationOnClickListener((v) -> onBackPressed());
    }

}
