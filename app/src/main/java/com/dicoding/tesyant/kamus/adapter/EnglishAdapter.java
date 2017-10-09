package com.dicoding.tesyant.kamus.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dicoding.tesyant.kamus.R;

import java.util.ArrayList;

/**
 * Created by tesyant on 10/5/17.
 */

public class EnglishAdapter extends RecyclerView.Adapter<EnglishAdapter.EnglishHolder> {

    private ArrayList<String> mData = new ArrayList<>();
    Context context;
    private LayoutInflater mInflater;
    private Activity activity;

    CustomItemClickListener listener;

    public EnglishAdapter (Context context, ArrayList<String> data, CustomItemClickListener listener) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mData = data;
        this.listener = listener;
    }

    @Override
    public EnglishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_english_row, parent, false);
        final EnglishHolder myViewHolder = new EnglishHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    public void addItem(ArrayList<String> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(EnglishHolder holder, int position) {
        holder.tvVocab.setText(mData.get(position).toString().trim());
        Log.e("Check", "word " + mData.get(position).toString() + "inserted");
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class EnglishHolder extends RecyclerView.ViewHolder {
        private TextView tvVocab;

        public EnglishHolder(View itemView) {
            super(itemView);
            tvVocab = (TextView) itemView.findViewById(R.id.tv_itemvocab);
        }
    }
}
