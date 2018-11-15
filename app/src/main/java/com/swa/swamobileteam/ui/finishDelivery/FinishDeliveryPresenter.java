package com.swa.swamobileteam.ui.finishDelivery;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class FinishDeliveryPresenter implements FinishDeliveryContract.Presenter {

    @Nullable
    private FinishDeliveryContract.View view;
    private FinishDeliveryContract.Model model;

    private CompositeDisposable disposable = new CompositeDisposable();


    @Inject
    FinishDeliveryPresenter(FinishDeliveryContract.Model model) {
        this.model = model;
    }

    @Override
    public void attachView(FinishDeliveryContract.View view, boolean isNew) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void stop() {
        if (disposable != null) {
            disposable.clear();
        }
    }

    @Override
    public void finish(int id) {
        if (view != null) {
            view.showLoadingDialog();
            disposable.add(model.finishDelivery(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> {
                                view.hideLoadingDialog();
                                view.navigateToDeliveriesList();
                            },
                            error -> {
                                view.hideLoadingDialog();
                                view.showLoadingError();
                            }
                    )
            );
        }
    }
}