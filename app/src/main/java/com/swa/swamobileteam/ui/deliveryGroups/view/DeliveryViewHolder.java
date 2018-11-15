package com.swa.swamobileteam.ui.deliveryGroups.view;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swa.swamobileteam.R;
import com.swa.swamobileteam.ui.deliveryGroups.DeliveryGroupsContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeliveryViewHolder extends RecyclerView.ViewHolder implements DeliveryGroupsContract.DeliveryView{

    private Resources resources;
    private int deliveryId;

    @BindView(R.id.text_date_divider)
    TextView dateDivider;
    @BindView(R.id.text_delivery_time)
    TextView deliveryPeriod;
    @BindView(R.id.text_parcel_id)
    TextView parcelId;
    @BindView(R.id.text_parcel_address)
    TextView parcelAddress;
    @BindView(R.id.text_delivery_weight)
    TextView parcelWeight;
    @BindView(R.id.text_estimated_time)
    TextView estimatedTime;
    @BindView(R.id.button_delivery_action)
    Button deliveryAction;

    private OnDeliveryActionsClickListener listener;

    DeliveryViewHolder(@NonNull View itemView){
        super(itemView);
        ButterKnife.bind(this, itemView);
        resources = itemView.getResources();
    }

    @Override
    public void setActionButtonText(boolean isInProgress) {
        if (isInProgress) {
            deliveryAction.setText(resources.getString(R.string.text_finish));
        }
        else {
            deliveryAction.setText(resources.getString(R.string.text_mark_as_current));
        }
    }

    @Override
    public void showDateDivider(String date) {
        dateDivider.setVisibility(View.VISIBLE);
        dateDivider.setText(date);
    }

    @Override
    public void hideDateDivider() {
        dateDivider.setVisibility(View.GONE);
    }

    @Override
    public void setTimePeriod(String time) {
        deliveryPeriod.setText(time);
    }

    @Override
    public void setParcelId(int id) {
        deliveryId = id;
        parcelId.setText(resources.getString(R.string.text_parcel_id, id));
    }

    @Override
    public void setAddress(String address) {
        parcelAddress.setText(address);
    }

    @Override
    public void setEstimatedTime(String time) {
        estimatedTime.setText(resources.getString(R.string.text_estimated_time, time));
    }

    @Override
    public void setListener(OnDeliveryActionsClickListener listener) {
        this.listener = listener;
    }

    @OnClick(R.id.button_details)
    public void onDetailsClick() {
        listener.onDetails(this.deliveryId);
    }

    @OnClick(R.id.button_delivery_action)
    public void onActionClick() {
        listener.onAction(this.deliveryId);
    }

    public interface OnDeliveryActionsClickListener {
        void onDetails(int deliveryId);

        void onAction(int deliveryId);
    }
}
