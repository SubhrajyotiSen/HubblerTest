package com.subhrajyoti.hubblertest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.subhrajyoti.hubblertest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.field1)
    TextView field1;
    @BindView(R.id.field2)
    TextView field2;

    public MainViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

}
