package com.swa.swamobileteam.di.modules;

import com.swa.swamobileteam.di.ActivityScope;
import com.swa.swamobileteam.di.FragmentScope;
import com.swa.swamobileteam.ui.authorization.AuthorizationContract;
import com.swa.swamobileteam.ui.authorization.AuthorizationPresenter;
import com.swa.swamobileteam.ui.delivery.DeliveryContract;
import com.swa.swamobileteam.ui.delivery.DeliveryPresenter;
import com.swa.swamobileteam.ui.deliveryGroups.DeliveryGroupsContract;
import com.swa.swamobileteam.ui.deliveryGroups.DeliveryGroupsPresenter;
import com.swa.swamobileteam.ui.finishDelivery.FinishDeliveryContract;
import com.swa.swamobileteam.ui.finishDelivery.FinishDeliveryPresenter;

import dagger.Binds;
import dagger.Module;

@Module
abstract class PresenterBingingModule {

    @Binds
    @ActivityScope
    public abstract AuthorizationContract.Presenter bindAuthorizationPresenter(AuthorizationPresenter presenter);

    @Binds
    @FragmentScope
    public abstract DeliveryGroupsContract.Presenter bindDeliveryGroupsPresenter(
            DeliveryGroupsPresenter presenter
    );

    @Binds
    @ActivityScope
    public abstract DeliveryContract.Presenter bindDeliveryPresenter(DeliveryPresenter presenter);

    @Binds
    @ActivityScope
    public abstract FinishDeliveryContract.Presenter bindFinishDeliveryPresenter(FinishDeliveryPresenter presenter);

}
