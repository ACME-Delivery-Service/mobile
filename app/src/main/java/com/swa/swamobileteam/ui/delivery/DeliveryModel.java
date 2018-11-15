package com.swa.swamobileteam.ui.delivery;

import android.support.annotation.NonNull;

import com.swa.swamobileteam.data.deliveries.Address;
import com.swa.swamobileteam.data.deliveries.DeliveriesListRepository;
import com.swa.swamobileteam.data.deliveries.DeliveryDetailsRepository;
import com.swa.swamobileteam.data.deliveries.DeliveryDetailsRepositoryImpl;
import com.swa.swamobileteam.data.deliveries.DeliveryOrderStatus;
import com.swa.swamobileteam.data.deliveries.DeliveryPeriod;
import com.swa.swamobileteam.data.deliveries.HumanContacts;
import com.swa.swamobileteam.transportApi.TransportApiClient;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DeliveryModel implements DeliveryContract.Model {
    private DeliveryDetailsRepository deliveryDetailsRepository;
    private DeliveriesListRepository scheduleRepository;
    private DeliveryInfo deliveryInfo;

    @Inject
    public DeliveryModel(DeliveryDetailsRepository repository,
                         @Named("ScheduleRepository")DeliveriesListRepository scheduleRepository) {
        this.deliveryDetailsRepository = repository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Single<DeliveryInfo> getDeliveryInfo(int deliveryID, @NonNull String token) {
            return deliveryDetailsRepository.getDeliveryInfo(deliveryID, "Token "+token).flatMap(
                    (deliveryInfo1 -> {
                        this.deliveryInfo = deliveryInfo1;
                        return Single.just(deliveryInfo1);
                    })
            );
    }

    @Override
    public Double getRemainingTime(DeliveryPeriod period) {
        return deliveryDetailsRepository.getRemainingTime(period);
    }

    @Override
    public Completable markAsCurrent(String token) {
        return scheduleRepository.updateDeliveryOrderStatus(deliveryInfo.getId(), DeliveryOrderStatus.IN_PROCESS, token);
    }

    @Override
    public DeliveryOrderStatus getStatus() {
        return deliveryInfo.getDeliveryStatus();
    }

    @Override
    public int getId() {
        return deliveryInfo.getId();
    }

    @Override
    public HumanContacts getClientInfo() {
        return deliveryInfo.getCustomerInfo().getContacts();
    }

    @Override
    public Address getFinalAddress() {
        return deliveryInfo.getAddressTo();
    }

}
