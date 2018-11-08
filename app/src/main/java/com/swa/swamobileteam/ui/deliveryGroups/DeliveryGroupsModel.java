package com.swa.swamobileteam.ui.deliveryGroups;

import android.support.annotation.NonNull;

import com.swa.swamobileteam.data.deliveries.DeliveryScheduleRepository;
import com.swa.swamobileteam.data.deliveries.DeliveryScheduleRepositoryImpl;
import com.swa.swamobileteam.data.deliveries.InProgressDeliveriesRepository;
import com.swa.swamobileteam.data.deliveries.InProgressDeliveriesRepositoryImpl;
import com.swa.swamobileteam.data.deliveries.Location;
import com.swa.swamobileteam.data.deliveries.RouteRepository;
import com.swa.swamobileteam.data.deliveries.RouteRepositoryImpl;
import com.swa.swamobileteam.utils.DeliveryType;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DeliveryGroupsModel implements DeliveryGroupsContract.Model {
    private DeliveryScheduleRepository scheduleRepository;
    private InProgressDeliveriesRepository inProgressDeliveriesRepository;
    private RouteRepository routeRepository;

    @Inject
    DeliveryGroupsModel() {
        this.scheduleRepository = new DeliveryScheduleRepositoryImpl();
        this.inProgressDeliveriesRepository = new InProgressDeliveriesRepositoryImpl();
        this.routeRepository = new RouteRepositoryImpl();
    }

    @Override
    public Completable markDeliveryAsInProgress(@NonNull String deliveryID) {
        return inProgressDeliveriesRepository.markDeliveryAsInProgress(deliveryID);
    }

    @Override
    public Single<Double> getETA(@NonNull Location location) {
        return routeRepository.getETA(location);
    }

    @Override
    public Single<Integer> refreshDeliveries(DeliveryType type) {
        if (type.equals(DeliveryType.New)) {
            return refreshScheduledDeliveries();
        }
        else {
            return refreshInProgressDeliveries();
        }
    }

    @Override
    public DeliveriesListItem getDeliveryListItem(DeliveryType type, int index) {
        if (type.equals(DeliveryType.New)) {
            return getScheduledDeliveryListItem(index);
        }
        else {
            return getInProgressDeliveryListItem(index);
        }
    }

    @Override
    public Single<Integer> loadDeliveries(DeliveryType type) {
        if (type.equals(DeliveryType.New)) {
            return loadScheduledDeliveries();
        }
        else {
            return loadInProgressDeliveries();
        }
    }


    private Single<Integer> refreshScheduledDeliveries() {
        return scheduleRepository.refresh();
    }

    private DeliveriesListItem getScheduledDeliveryListItem(int index) {
        return scheduleRepository.getDeliveryListItem(index);
    }

    private Single<Integer> refreshInProgressDeliveries() {
        return inProgressDeliveriesRepository.refresh();
    }

    private DeliveriesListItem getInProgressDeliveryListItem(int index) {
        return inProgressDeliveriesRepository.getDeliveryListItem(index);
    }

    private Single<Integer> loadInProgressDeliveries() {
        return inProgressDeliveriesRepository.loadDeliveries();
    }


    private Single<Integer> loadScheduledDeliveries() {
        return scheduleRepository.loadDeliveries();
    }
}
