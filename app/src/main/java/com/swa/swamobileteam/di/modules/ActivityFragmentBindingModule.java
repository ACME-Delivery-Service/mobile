package com.swa.swamobileteam.di.modules;

import com.swa.swamobileteam.di.ActivityScope;
import com.swa.swamobileteam.di.FragmentScope;
import com.swa.swamobileteam.ui.authorization.AuthorizationActivity;
import com.swa.swamobileteam.ui.delivery.DeliveryActivity;
import com.swa.swamobileteam.ui.deliveryGroups.view.DeliveriesFragment;
import com.swa.swamobileteam.ui.finishDelivery.FinishDeliveryActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityFragmentBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = {PresenterBingingModule.class, ModelBindingModule.class})
    abstract AuthorizationActivity authorizationActivity();

    @FragmentScope
    @ContributesAndroidInjector(modules = {PresenterBingingModule.class, ModelBindingModule.class})
    abstract DeliveriesFragment newDeliveriesFragment();

    @ActivityScope
    @ContributesAndroidInjector(modules = {PresenterBingingModule.class, ModelBindingModule.class})
    abstract DeliveryActivity deliveryActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = {PresenterBingingModule.class, ModelBindingModule.class})
    abstract FinishDeliveryActivity finishDeliveryActivity();

}
