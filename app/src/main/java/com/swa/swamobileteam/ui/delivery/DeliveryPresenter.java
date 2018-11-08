package com.swa.swamobileteam.ui.delivery;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.swa.swamobileteam.data.deliveries.Location;
import com.swa.swamobileteam.data.deliveries.ParcelInfo;
import com.swa.swamobileteam.data.deliveries.ParcelInfo.Dimensions;

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
    private CompositeDisposable disposable = new CompositeDisposable();
    private String phone;
    private Location location;

    @Inject
    DeliveryPresenter(DeliveryContract.Model model) {
        this.model = model;
    }

    @Override
    public void getInfo(){
        if (view != null) {
            disposable.add(model.getDeliveryInfo("")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            this::setView,
                            error -> getInfo()
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
    public void attachView(DeliveryContract.View view, boolean isNew) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void stop() {
    }

    private void setView(DeliveryInfo deliveryInfo) {
        if (view != null) {
            //Set time period
            Date start = deliveryInfo.getDeliveryPeriod().getStart();
            Date end = deliveryInfo.getDeliveryPeriod().getEnd();
            view.setTimePeriod(getTime(start) + " - "+ getTime(end));

            //Set time remaining
            view.setTimeRemaining(Math.round(model.getRemainingTime(deliveryInfo.getDeliveryPeriod())));

            //Set client info
            view.setName(deliveryInfo.getClientInfo().getFirstName() + " "
                    + deliveryInfo.getClientInfo().getLastName());
            view.setPhone(deliveryInfo.getClientInfo().getPhoneNumber());
            this.phone = deliveryInfo.getClientInfo().getPhoneNumber();

            //Set address
            view.setAddress(deliveryInfo.getAddress().getAddress());
            this.location = deliveryInfo.getAddress().getLocation();

            //Set PARCEL info
            view.setParcelName("X-box");
            view.setParcelId(deliveryInfo.getId());
            view.setWeight(deliveryInfo.getParcelInfo().getWeight());
            Dimensions dimensions = deliveryInfo.getParcelInfo().getDimensions();
            view.setDimensions(dimensions.getX(), dimensions.getY(), dimensions.getZ());

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