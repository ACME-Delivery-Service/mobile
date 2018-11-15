package com.swa.swamobileteam.ui.deliveryGroups;

import com.swa.swamobileteam.data.deliveries.DeliveryOrderStatus;
import com.swa.swamobileteam.data.deliveries.Location;
import com.swa.swamobileteam.transportApi.CredentialsManager;
import com.swa.swamobileteam.ui.deliveryGroups.view.DeliveryViewHolder;
import com.swa.swamobileteam.utils.DeliveryType;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class DeliveryGroupsPresenter implements DeliveryGroupsContract.Presenter,
        DeliveryViewHolder.OnDeliveryActionsClickListener {

    private DeliveryGroupsContract.View view;
    private DeliveryGroupsContract.Model model;
    private CredentialsManager credentialsManager;
    private CompositeDisposable disposable = new CompositeDisposable();
    private int deliveriesCount = 0;

    @Inject
    DeliveryGroupsPresenter(DeliveryGroupsContract.Model model,
                            CredentialsManager credentialsManager) {
        this.model = model;
        this.credentialsManager = credentialsManager;
    }

    @Override
    public void attachView(DeliveryGroupsContract.View view, boolean isNew) {
        this.view = view;
    }

    @Override
    public void detachView() {
        this.view = null;
    }

    @Override
    public void stop() {
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void onBindDeliveryGroup(DeliveryGroupsContract.DeliveryView deliveryView, int position) {
        if (view != null) {
            deliveryView.setListener(this);
            DeliveriesListItem delivery;
            delivery = model.getDeliveryListItem(view.getType(), position);

            //Setting date divider
            Date start = delivery.getDeliveryPeriod().getStart();
            Date end = delivery.getDeliveryPeriod().getEnd();
            if (needDateDivider(position, view.getType())) {
                deliveryView.showDateDivider(getDate(start));
            }
            else {
                deliveryView.hideDateDivider();
            }

            //Converting time
            deliveryView.setTimePeriod(getTime(start) + " - " + getTime(end));
            deliveryView.setParcelId(delivery.getId());
            deliveryView.setAddress(delivery.getAddressTo().getAddress());
            if (view.getType().equals(DeliveryType.New)) {
                deliveryView.setActionButtonText(false);
            }
            else {
                deliveryView.setActionButtonText(true);
            }
            setEstimatedTime(deliveryView, delivery.getAddressTo().getLocation());
        }
    }

    @Override
    public int getDeliveriesCount() {
        return deliveriesCount;
    }

    @Override
    public void loadDeliveries() {
        if (view != null) {
            Timber.d("here");
            String token = "Token "+credentialsManager.getApiAuthenticationToken();
            Timber.d(token);
            disposable.add(model.loadDeliveries(view.getType(), token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            deliveriesCount -> {
                                view.hideLoadingBar();
                                if (deliveriesCount > 0) {
                                    this.deliveriesCount += deliveriesCount;
                                    view.notifyDataSetChanged();
                                } else {
                                    view.showNoDeliveries();
                                }
                            },
                            error -> loadDeliveries()
                    )
            );
        }
    }

    @Override
    public void pullToRefresh() {
        if (view != null) {
            String token = "Token "+credentialsManager.getApiAuthenticationToken();
            Timber.d(token);
            disposable.add(model.refreshDeliveries(view.getType(), token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            (deliveriesCount) -> {
                                this.deliveriesCount = deliveriesCount;
                                view.endRefreshment();
                                view.hideLoadingBar();
                                view.notifyDataSetChanged();
                            },
                            error -> {
                                Timber.d(error);
                                view.showLoadingError();
                                view.hideLoadingBar();
                                view.endRefreshment();
                            }
                    )
            );
        }
    }

    @Override
    public void updateItemETA(Double seconds, int index) {
        if (view != null) {
            model.updateItemETA(Math.floor(seconds / 60), index, view.getType());
            view.notifyItemChanged(index);
        }
    }

    private void setEstimatedTime(DeliveryGroupsContract.DeliveryView deliveryView, Location location) {
        disposable.add(model.getETA(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        time -> deliveryView.setEstimatedTime(String.valueOf(Math.round(time))),
                        error -> setEstimatedTime(deliveryView, location)
                )
        );
    }

    private String getDate(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        StringBuilder dateStr = new StringBuilder();

        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            dateStr.append("0").append(calendar.get(Calendar.DAY_OF_MONTH));
        }
        else {
            dateStr.append(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        }

        dateStr.append(".");
        if (calendar.get(Calendar.MONTH)+1 < 10) {
            dateStr.append("0").append(calendar.get(Calendar.MONTH)+1);
        }
        else {
            dateStr.append(String.valueOf(calendar.get(Calendar.MONTH)+1));
        }
        dateStr.append(".");
        dateStr.append(calendar.get(Calendar.YEAR));
        return dateStr.toString();
    }

    private String getTime(Date date) {
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(date);
        String hours;
        String minutes;
        if (calendar.get(Calendar.HOUR_OF_DAY) < 10) {
            hours = "0" + calendar.get(Calendar.HOUR_OF_DAY);
        }
        else {
            hours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        }
        if (calendar.get(Calendar.MINUTE) < 10) {
            minutes = "0" + calendar.get(Calendar.MINUTE);
        }
        else {
            minutes = String.valueOf(calendar.get(Calendar.MINUTE));
        }
        return hours + ":" + minutes;
    }

    private boolean needDateDivider(int position, DeliveryType type) {
        if (position == 0) {
            return true;
        }
        Date dateAbove;
        Date dateBelow;
        dateAbove = model.getDeliveryListItem(type, position - 1).getDeliveryPeriod().getStart();
        dateBelow = model.getDeliveryListItem(type, position).getDeliveryPeriod().getStart();
        return dateAbove.after(dateBelow) && !(dateAbove.getDate() == dateBelow.getDate() && dateAbove.getMonth() == dateBelow.getMonth() && dateAbove.getYear() == dateBelow.getYear());
    }

    @Override
    public void onDetails(int deliveryId) {
        if (view != null) {
            view.navigateToDelivery(deliveryId);
        }
    }

    @Override
    public void onAction(int deliveryId) {
        if (view.getType().equals(DeliveryType.New)) {
            disposable.add(model.updateDeliveryOrderStatus(
                    deliveryId, DeliveryOrderStatus.IN_PROCESS, "Token "+credentialsManager.getApiAuthenticationToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> pullToRefresh(),
                            error -> view.showLoadingError()
                    )
            );
        } else {
            disposable.add(model.updateDeliveryOrderStatus(
                    deliveryId, DeliveryOrderStatus.FINISHED, "Token "+credentialsManager.getApiAuthenticationToken())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            () -> pullToRefresh(),
                            error -> view.showLoadingError()
                    )
            );
        }
    }
}
