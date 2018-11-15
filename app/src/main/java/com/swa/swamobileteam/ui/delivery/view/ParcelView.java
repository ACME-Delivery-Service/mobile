package com.swa.swamobileteam.ui.delivery.view;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.swa.swamobileteam.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParcelView {

    @BindView(R.id.text_parcel_name)
    TextView name;
    @BindView(R.id.text_dimensions)
    TextView dimensions;
    @BindView(R.id.text_weight)
    TextView weight;
    @BindView(R.id.text_parcel_id)
    TextView id;


    private View resultView;

    public ParcelView(LayoutInflater inflater) {
        resultView = inflater.inflate(R.layout.item_parcel, null);
        ButterKnife.bind(this, resultView);
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setDimensions(String dimensions) {
        this.dimensions.setText(dimensions);
    }

    public void setWeight(String weight) {
        this.weight.setText(weight);
    }

    public void setId(String id) {
        this.id.setText(id);
    }

    public View getView() {
        return resultView;
    }
}
