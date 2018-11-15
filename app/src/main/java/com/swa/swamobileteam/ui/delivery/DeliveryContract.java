package com.swa.swamobileteam.ui.delivery;

import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.swa.swamobileteam.data.deliveries.Address;
import com.swa.swamobileteam.data.deliveries.DeliveryOrderStatus;
import com.swa.swamobileteam.data.deliveries.DeliveryPeriod;
import com.swa.swamobileteam.data.deliveries.HumanContacts;
import com.swa.swamobileteam.ui.base.BaseModel;
import com.swa.swamobileteam.ui.base.BasePresenter;
import com.swa.swamobileteam.ui.base.BaseView;
import com.swa.swamobileteam.ui.delivery.view.ParcelView;
import com.swa.swamobileteam.ui.deliveryGroups.DeliveryGroupsContract;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DeliveryContract {
    interface View extends BaseView {

        /**
         * Sets the time of the delivery
         * @param time time of the delivery
         */
        void setTimePeriod(String time);

        /**
         * Sets address of the delivery
         * @param address address of the delivery
         */
        void setAddress(String address);

        void setName(String name);

        void setPhone(String phone);

        void setTimeRemaining(long time);

        void setActionButton(DeliveryOrderStatus deliveryOrderStatus);

        void navigateToMap(Uri coordsUri);

        void callPhone(String phone);

        void addParcel(android.view.View parcel);

        ParcelView createParcelView();

        Resources getResource();

        void hideProgressBar();

        void navigateToFinishDeliveryActivity(int id, String client, String address);

        void navigateToMainActivity();

        void showLoadingDialog();

        void hideLoadingDialog();

        void showLoadingError();
    }

    interface Presenter extends BasePresenter<DeliveryContract.View> {
        void getInfo(int deliveryId);

        void openMap();

        void callClient();

        void callOperator();

        void onAction();

        int getId();
    }

    interface Model extends BaseModel {
        /**
         * Retrieves derailed information about a delivery.
         * @param deliveryID Identifier of the delivery to retrieve info for.
         */
        Single<DeliveryInfo> getDeliveryInfo(int deliveryID, @NonNull String token);

        /**
         * Calculates how much time is left to finish the delivery in seconds.
         * @param period Delivery period of the delivery.
         */
        Double getRemainingTime(DeliveryPeriod period);

        Completable markAsCurrent(String token);

        DeliveryOrderStatus getStatus();

        int getId();

        HumanContacts getClientInfo();

        Address getFinalAddress();
    }
}