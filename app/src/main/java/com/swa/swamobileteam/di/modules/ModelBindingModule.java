package com.swa.swamobileteam.di.modules;

import com.swa.swamobileteam.di.ActivityScope;
import com.swa.swamobileteam.di.FragmentScope;
import com.swa.swamobileteam.ui.authorization.AuthorizationContract;
import com.swa.swamobileteam.ui.authorization.AuthorizationModel;
import com.swa.swamobileteam.ui.delivery.DeliveryContract;
import com.swa.swamobileteam.ui.delivery.DeliveryModel;
import com.swa.swamobileteam.ui.deliveryGroups.DeliveryGroupsContract;
import com.swa.swamobileteam.ui.deliveryGroups.DeliveryGroupsModel;
import com.swa.swamobileteam.ui.finishDelivery.FinishDeliveryContract;
import com.swa.swamobileteam.ui.finishDelivery.FinishDeliveryModel;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ModelBindingModule {

    @Binds
    @ActivityScope
    public abstract AuthorizationContract.Model bindAuthorizationModel(AuthorizationModel model);

    @Binds
    @FragmentScope
    public abstract DeliveryGroupsContract.Model bindDeliveryGroupsModel(DeliveryGroupsModel model);

    @Binds
    @ActivityScope
    public abstract DeliveryContract.Model bindDeliveryModel(DeliveryModel model);

    @Binds
    @ActivityScope
    public abstract FinishDeliveryContract.Model bindFinishDeliveryModel(FinishDeliveryModel model);

}
