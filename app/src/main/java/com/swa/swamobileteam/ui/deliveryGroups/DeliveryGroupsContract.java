package com.swa.swamobileteam.ui.deliveryGroups;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.swa.swamobileteam.data.deliveries.DeliveryOrderStatus;
import com.swa.swamobileteam.data.deliveries.Location;
import com.swa.swamobileteam.ui.base.BaseModel;
import com.swa.swamobileteam.ui.base.BasePresenter;
import com.swa.swamobileteam.ui.base.BaseView;
import com.swa.swamobileteam.ui.deliveryGroups.view.DeliveryViewHolder;
import com.swa.swamobileteam.utils.DeliveryType;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DeliveryGroupsContract {

    interface View extends BaseView {
        /**
         * Hides the initial loading bar
         */
        void hideLoadingBar();

        /**
         * Shows the message that there are no deliveries
         */
        void showNoDeliveries();

        /**
         * Hides the message about absence of deliveries
         */
        void hideNoDeliveries();

        /**
         * Get type of the deliveries fragment
         * @return delivery type
         */
        @Nullable
        DeliveryType getType();

        /**
         * Notify RecyclerView's adapter that data has changed
         */
        void notifyDataSetChanged();

        /**
         * Stop refreshing animation
         */
        void endRefreshment();

        /**
         * Navigate to the delivery details screen
         */
        void navigateToDelivery(int deliveryId);

        void showLoadingError();
    }
    interface Presenter extends BasePresenter<View> {
        /**
         * Bind delivery view to the adapter
         * @param deliveryView view to be bound
         * @param position binding position
         */
        void onBindDeliveryGroup(DeliveryView deliveryView, int position);

        /**
         * Returns number of deliveries in the recycler view
         * @return number of the deliveries
         */
        int getDeliveriesCount();

        /**
         * Method to load deliveries from the model
         */
        void loadDeliveries();

        /**
         * Refresh data
         */
        void pullToRefresh();
    }

    interface Model extends BaseModel {

        /**
         * Marks the desired delivery as being in progress.
         * @param deliveryID identifier of the delivery.
         */
        Completable updateDeliveryOrderStatus(@NonNull int deliveryID, @NonNull DeliveryOrderStatus newStatus, @NonNull String token);

        /**
         * Returns minimum the time (in seconds) required to drive to given destination.
         * @param location Location to calculate ETA to.
         */
        Single<Double> getETA(@NonNull Location location);

        /**
         * Refreshes list of scheduled deliveries
         */
        Single<Integer> refreshDeliveries(DeliveryType type, @NonNull String token);

        /**
         * Returns delivery item given its index
         * @param index of delivery item
         * @return delivery iten on given index
         */
        DeliveriesListItem  getDeliveryListItem(DeliveryType type, int index);

        /**
         * Method loads deliveries from repository and returns their count
         * @return
         */
        Single<Integer> loadDeliveries(DeliveryType type, @NonNull String token);
    }

    interface DeliveryView extends BaseView{
        /**
         * Set action button's text to "finish" or "mark as current"
         * @param isInProgress is this delivery in progress now
         */
        void setActionButtonText(boolean isInProgress);

        /**
         * Shows the date above the delivery
         * @param date date of the delivery
         */
        void showDateDivider(String date);

        /**
         * Hides the date above the delivery
         */
        void hideDateDivider();

        /**
         * Sets the time of the delivery
         * @param time time of the delivery
         */
        void setTimePeriod(String time);

        /**
         * Sets PARCEL id of the delivery
         * @param id identifier of the delivery
         */
        void setParcelId(int id);

        /**
         * Sets address of the delivery
         * @param address address of the delivery
         */
        void setAddress(String address);

        /**
         * Sets the estimated time of the delivery
         * @param time estimated time of the delvery
         */
        void setEstimatedTime(String time);

        /**
         * Sets listener to handle buttons "Finish"/"Mark as current" and "Details" clicks
         * @param listener listener of the actions
         */
        void setListener(DeliveryViewHolder.OnDeliveryActionsClickListener listener);
    }
}
