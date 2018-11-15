package com.swa.swamobileteam.ui.finishDelivery;


import com.swa.swamobileteam.data.deliveries.DeliveriesListRepository;
import com.swa.swamobileteam.data.deliveries.DeliveryOrderStatus;
import com.swa.swamobileteam.transportApi.CredentialsManager;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Completable;

public class FinishDeliveryModel implements FinishDeliveryContract.Model {

    private DeliveriesListRepository repo;
    private CredentialsManager credentialsManager;

    @Inject
    FinishDeliveryModel(@Named("ScheduleRepository") DeliveriesListRepository repo,
                        CredentialsManager credentialsManager) {
        this.repo = repo;
        this.credentialsManager = credentialsManager;
    }

    @Override
    public Completable finishDelivery(int id) {
        return repo.updateDeliveryOrderStatus(id, DeliveryOrderStatus.FINISHED, credentialsManager.getApiAuthenticationToken());
    }
}