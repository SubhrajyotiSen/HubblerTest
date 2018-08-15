package com.subhrajyoti.hubblertest.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.subhrajyoti.hubblertest.R;
import com.subhrajyoti.hubblertest.data.MyPair;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private ArrayList<MyPair> myPairArrayList;

    public RecyclerViewAdapter(ArrayList<MyPair> myPairArrayList) {
        this.myPairArrayList = myPairArrayList;
    }

    public void addItem(MyPair myPair) {
        myPairArrayList.add(myPair);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        holder.field1.setText(myPairArrayList.get(position).getString1());
        holder.field2.setText(myPairArrayList.get(position).getString2());
    }

    @Override
    public int getItemCount() {
        return myPairArrayList.size();
    }
}
