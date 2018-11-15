package com.swa.swamobileteam.ui.delivery;
import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.swa.swamobileteam.R;
import com.swa.swamobileteam.data.deliveries.DeliveryOrderStatus;
import com.swa.swamobileteam.data.deliveries.Location;
import com.swa.swamobileteam.data.deliveries.ParcelInfo;
import com.swa.swamobileteam.data.deliveries.ParcelInfo.Dimensions;
import com.swa.swamobileteam.transportApi.CredentialsManager;
import com.swa.swamobileteam.ui.delivery.view.ParcelView;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class DeliveryPresenter implements DeliveryContract.Presenter{

    private final String OPERATOR_PHONE_NUMBER = "+79932398037";

    @Nullable
    private DeliveryContract.View view;
    private DeliveryContract.Model model;
    private CredentialsManager credentialsManager;
    private CompositeDisposable disposable = new CompositeDisposable();
    private String phone;
    private Location location;

    @Inject
    DeliveryPresenter(DeliveryContract.Model model, CredentialsManager credentialsManager) {
        this.model = model;
        this.credentialsManager = credentialsManager;
    }

    @Override
    public void attachView(DeliveryContract.View view, boolean isNew) {
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
    public void getInfo(int deliveryId){
        if (view != null) {
            disposable.add(model.getDeliveryInfo(deliveryId, credentialsManager.getApiAuthenticationToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::setView,
                            error -> getInfo(deliveryId)
                    )
            );
        }
    }

    @Override
    public void openMap() {
        if (view != null) {
            String coords = "google.navigation:q=" + String.valueOf(location.getLatitude()) + "," + String.valueOf(location.getLongitude());
            Uri gmmUri = Uri.parse(coords);
            view.navigateToMap(gmmUri);
        }
    }

    @Override
    public void callClient() {
        if (view != null) {
            view.callPhone(this.phone);
        }
    }

    @Override
    public void callOperator() {
        if (view != null) {
            view.callPhone(OPERATOR_PHONE_NUMBER);
        }
    }

    @Override
    public void onAction() {
        if (view != null) {
            if (model.getStatus().equals(DeliveryOrderStatus.PENDING)) {
                view.showLoadingDialog();
                disposable.add(model.markAsCurrent(credentialsManager.getApiAuthenticationToken())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    view.hideLoadingDialog();
                                    view.navigateToMainActivity();
                                },
                                error -> {
                                    view.hideLoadingDialog();
                                    view.showLoadingError();
                                }
                        ));
            }
            else {
                String client = model.getClientInfo().getFirstName() + " " + model.getClientInfo().getLastName();
                String destination = model.getFinalAddress().getAddress();
                view.navigateToFinishDeliveryActivity(model.getId(), client, destination);
            }
        }

    }

    @Override
    public int getId() {
        return model.getId();
    }

    private void setView(DeliveryInfo deliveryInfo) {
        if (view != null) {
            view.hideProgressBar();

            //Set time period
            Date start = deliveryInfo.getDeliveryPeriod().getStart();
            Date end = deliveryInfo.getDeliveryPeriod().getEnd();
            view.setTimePeriod(getTime(start) + " - "+ getTime(end));

            //Set time remaining
            view.setTimeRemaining(Math.round(model.getRemainingTime(deliveryInfo.getDeliveryPeriod())));

            //Set client info
            view.setName(deliveryInfo.getCustomerInfo().getContacts().getFirstName() + " "
                    + deliveryInfo.getCustomerInfo().getContacts().getLastName());
            view.setPhone(deliveryInfo.getCustomerInfo().getContacts().getPhoneNumber());
            this.phone = deliveryInfo.getCustomerInfo().getContacts().getPhoneNumber();

            //Set address
            view.setAddress(deliveryInfo.getAddressTo().getAddress());
            this.location = deliveryInfo.getAddressTo().getLocation();

            //Set PARCEL info
            for(ParcelInfo parcel : deliveryInfo.getParcelsInfo()) {
                ParcelView parcelView = this.view.createParcelView();
                Resources resources = view.getResource();
                parcelView.setName(resources.getString(
                        R.string.text_parcel_name, parcel.getDescription()));
                parcelView.setDimensions(resources.getString(
                        R.string.text_dimensions, parcel.getDimensions().getX(), parcel.getDimensions().getY(), parcel.getDimensions().getZ()
                ));
                parcelView.setWeight(resources.getString(
                        R.string.text_weight, parcel.getWeight()));
                parcelView.setId(resources.getString(
                        R.string.text_parcel_details_id, parcel.getId()));
                this.view.addParcel(parcelView.getView());
            }

            view.setActionButton(deliveryInfo.getDeliveryStatus());
        }
    }

    private String getTime(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
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