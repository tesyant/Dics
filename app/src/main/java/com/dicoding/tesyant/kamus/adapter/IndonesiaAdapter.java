package com.dicoding.tesyant.kamus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dicoding.tesyant.kamus.R;
import com.dicoding.tesyant.kamus.model.IndonesiaModel;

import java.util.ArrayList;

/**
 * Created by tesyant on 10/8/17.
 */

public class IndonesiaAdapter extends RecyclerView.Adapter<IndonesiaAdapter.IndonesiaHolder> {

    private ArrayList<IndonesiaModel> mData = new ArrayList<>();
    Context context;
    private LayoutInflater mInflater;

    public IndonesiaAdapter (Context context, ArrayList<IndonesiaModel> data) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mData = data;
    }


    @Override
    public IndonesiaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    public void addItem(ArrayList<IndonesiaModel> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(IndonesiaHolder holder, int position) {
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

    public static class IndonesiaHolder extends RecyclerView.ViewHolder {
        private TextView tvVocab;

        public IndonesiaHolder(View itemView) {
            super(itemView);

            tvVocab = (TextView) itemView.findViewById(R.id.tv_itemvocab);
        }
    }
}
