package com.swa.swamobileteam.ui.deliveryGroups.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.swa.swamobileteam.R;
import com.swa.swamobileteam.ui.delivery.DeliveryActivity;
import com.swa.swamobileteam.ui.deliveryGroups.DeliveryGroupsContract;
import com.swa.swamobileteam.utils.DeliveryType;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public class DeliveriesFragment extends Fragment implements DeliveryGroupsContract.View {

    private static final String DELIVERY_STATE = "delivery_state";

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_deliveries)
    RecyclerView deliveries;
    @BindView(R.id.progress_bar_center)
    ProgressBar loadingBar;
    @BindView(R.id.text_no_deliveries)
    TextView textNoDeliveries;


    private Unbinder unbinder;
    private DeliveriesAdapter adapter;

    @Inject
    DeliveryGroupsContract.Presenter presenter;

    public static DeliveriesFragment newInstance(DeliveryType type){
        DeliveriesFragment fragment = new DeliveriesFragment();
        Bundle args = new Bundle();
        args.putSerializable(DELIVERY_STATE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_delivery_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        AndroidSupportInjection.inject(this);
        presenter.attachView(this, true);
        setRecyclerView();
        setSwipeRefreshLayout();
        presenter.loadDeliveries();

    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
        presenter.detachView();
    }

    private void setRecyclerView(){
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        deliveries.setLayoutManager(lm);
        adapter = new DeliveriesAdapter(presenter);
        deliveries.setAdapter(adapter);
    }

    @Override
    public void hideLoadingBar() {
        loadingBar.setVisibility(View.GONE);
    }

    @Override
    public void showNoDeliveries() {
        textNoDeliveries.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoDeliveries() {
        textNoDeliveries.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public DeliveryType getType() {
        if (getArguments() != null) {
            return (DeliveryType) getArguments().getSerializable(DELIVERY_STATE);
        }
        return null;
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void endRefreshment() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void navigateToDelivery(int deliveryId) {
        startActivity(DeliveryActivity.newInstance(getContext(), deliveryId));
    }

    @Override
    public void showLoadingError() {
        Toast.makeText(getContext(), getString(R.string.text_loading_error), Toast.LENGTH_LONG).show();
    }

    private void setSwipeRefreshLayout() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(() -> presenter.pullToRefresh());
        }
    }
}
