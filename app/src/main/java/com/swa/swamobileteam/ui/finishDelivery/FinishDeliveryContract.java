package com.swa.swamobileteam.ui.finishDelivery;

import com.swa.swamobileteam.ui.base.BaseModel;
import com.swa.swamobileteam.ui.base.BasePresenter;
import com.swa.swamobileteam.ui.base.BaseView;

import io.reactivex.Completable;

public interface FinishDeliveryContract {
    interface View extends BaseView {
        void navigateToDeliveriesList();

        void showLoadingError();

        void showLoadingDialog();

        void hideLoadingDialog();
    }
    interface Presenter extends BasePresenter<View> {
        void finish(int id);
    }
    interface Model extends BaseModel {
        Completable finishDelivery(int id);
    }
}