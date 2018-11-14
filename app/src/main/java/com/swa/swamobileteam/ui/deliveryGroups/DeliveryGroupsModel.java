package com.swa.swamobileteam.ui.deliveryGroups;

import android.support.annotation.NonNull;

import com.swa.swamobileteam.data.deliveries.DeliveriesListRepository;
import com.swa.swamobileteam.data.deliveries.DeliveryOrderStatus;
import com.swa.swamobileteam.data.deliveries.DeliveryScheduleRepository;
import com.swa.swamobileteam.data.deliveries.InProgressDeliveriesRepository;
import com.swa.swamobileteam.data.deliveries.Location;
import com.swa.swamobileteam.data.deliveries.RouteRepository;
import com.swa.swamobileteam.data.deliveries.RouteRepositoryImpl;
import com.swa.swamobileteam.utils.DeliveryType;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

public class DeliveryGroupsModel implements DeliveryGroupsContract.Model {
    private DeliveriesListRepository scheduleRepository;
    private DeliveriesListRepository inProgressDeliveriesRepository;
    private RouteRepository routeRepository;

    @Inject
    DeliveryGroupsModel(RouteRepository routeRepository, DeliveriesListRepository scheduleRepository, DeliveriesListRepository inProgressDeliveriesRepository) {
        this.scheduleRepository = scheduleRepository;
        this.inProgressDeliveriesRepository = inProgressDeliveriesRepository;
        this.routeRepository = routeRepository;
    }

    // TODO: update presenters to use new method syntax which accept which action to perform
    @Override
    public Completable updateDeliveryOrderStatus(@NonNull int deliveryID, @NonNull DeliveryOrderStatus newStatus, @NonNull String token) {
        return scheduleRepository.updateDeliveryOrderStatus(deliveryID, newStatus, token);
    }

    @Override
    public Single<Double> getETA(@NonNull Location location) {
        return routeRepository.getETA(location);
    }

    @Override
    public Single<Integer> refreshDeliveries(DeliveryType type, @NonNull String token) {
        return getRepositoryForType(type).refresh(token);
    }

    @Override
    public DeliveriesListItem getDeliveryListItem(DeliveryType type, int index) {
        return getRepositoryForType(type).getDeliveryListItem(index);
    }

    @Override
    public Single<Integer> loadDeliveries(DeliveryType type, @NonNull String token) {
        return getRepositoryForType(type).loadDeliveries(token);
    }

    private DeliveriesListRepository getRepositoryForType(DeliveryType type) {
        if (type.equals(DeliveryType.New)) {
            return scheduleRepository;
        } else {
            return inProgressDeliveriesRepository;
        }
    }
}
