package com.swa.swamobileteam.ui.deliveryGroups;

import android.view.View;

import com.swa.swamobileteam.data.deliveries.Location;
import com.swa.swamobileteam.ui.deliveryGroups.view.DeliveryViewHolder;
import com.swa.swamobileteam.utils.DateFormatter;
import com.swa.swamobileteam.utils.DeliveryType;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.internal.operators.single.SingleCache;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class DeliveryGroupsPresenter implements DeliveryGroupsContract.Presenter,
        DeliveryViewHolder.OnDeliveryActionsClickListener {

    private DeliveryGroupsContract.View view;
    private DeliveryGroupsContract.Model model;
    private CompositeDisposable disposable = new CompositeDisposable();
    private int deliveriesCount = 0;
    private boolean isRefresh = false;

    @Inject
    DeliveryGroupsPresenter(DeliveryGroupsContract.Model model) {
        this.model = model;
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
            if (view.getType() != null) {
                deliveryView.setListener(this);
                DeliveriesListItem delivery;
                if (view.getType().equals(DeliveryType.New)) {
                    delivery = model.getScheduledDeliveryListItem(position);
                } else {
                    delivery = model.getInProgressDeliveryListItem(position);
                }

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
                deliveryView.setAddress(delivery.getAddress().getAddress());
                deliveryView.setWeight(delivery.getWeight());
                deliveryView.setActionButtonText(delivery.getInProgress());
                setEstimatedTime(deliveryView, delivery.getAddress().getLocation());
            }
        }
    }

    @Override
    public int getDeliveriesCount() {
        return deliveriesCount;
    }

    @Override
    public void loadDeliveries() {
        if (view != null) {
            if (view.getType() != null) {
                if (view.getType().equals(DeliveryType.New)) {
                    disposable.add(model.loadScheduledDeliveries()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    deliveriesCount -> {
                                        Timber.d(String.valueOf(deliveriesCount));
                                        view.hideLoadingBar();
                                        if (deliveriesCount > 0) {
                                            this.deliveriesCount += deliveriesCount;
                                            if (isRefresh) {
                                                isRefresh = false;
                                                view.endRefreshment();
                                            }
                                            view.notifyDataSetChanged();
                                        } else {
                                            view.showNoDeliveries();
                                        }
                                    },
                                    error -> loadDeliveries()
                            )
                    );
                }
                if (view.getType().equals(DeliveryType.InProgress)) {
                    disposable.add(model.loadInProgressDeliveries()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    deliveriesCount -> {
                                        view.hideLoadingBar();
                                        if (deliveriesCount > 0) {
                                            this.deliveriesCount += deliveriesCount;
                                            if (isRefresh) {
                                                isRefresh = false;
                                                view.endRefreshment();
                                            }
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
        }
    }

    @Override
    public void pullToRefresh() {
        deliveriesCount = 0;
        isRefresh = true;
        loadDeliveries();
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
        if (type.equals(DeliveryType.New)) {
            dateAbove = model.getScheduledDeliveryListItem(position - 1).getDeliveryPeriod().getStart();
            dateBelow = model.getScheduledDeliveryListItem(position).getDeliveryPeriod().getStart();
        }
        else {
            dateAbove = model.getInProgressDeliveryListItem(position - 1).getDeliveryPeriod().getStart();
            dateBelow = model.getInProgressDeliveryListItem(position).getDeliveryPeriod().getStart();
        }
        return dateAbove.after(dateBelow) && !(dateAbove.getDate() == dateBelow.getDate() && dateAbove.getMonth() == dateBelow.getMonth() && dateAbove.getYear() == dateBelow.getYear());
    }

    @Override
    public void onDetails(String deliveryId) {
        if (view != null) {
            view.navigateToDelivery(deliveryId);
        }
    }

    @Override
    public void onAction(String deliveryId) {

    }
}
