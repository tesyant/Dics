package com.dicoding.tesyant.kamus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dicoding.tesyant.kamus.R;
import com.dicoding.tesyant.kamus.model.EnglishModel;

import java.util.ArrayList;

/**
 * Created by tesyant on 10/5/17.
 */

public class EnglishAdapter extends RecyclerView.Adapter<EnglishAdapter.EnglishHolder> {

    private ArrayList<EnglishModel> mData = new ArrayList<>();
    Context context;
    private LayoutInflater mInflater;

    public EnglishAdapter (Context context, ArrayList<EnglishModel> data) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mData = data;
    }


    @Override
    public EnglishHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public void addItem(ArrayList<EnglishModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(EnglishHolder holder, int position) {
        holder.tvVocab.setText(mData.get(position).getVocab());
        Log.e("Check", "word " + mData.get(position).getVocab() + "inserted");
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
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
