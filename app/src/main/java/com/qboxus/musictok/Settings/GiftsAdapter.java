package com.qboxus.musictok.Settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qboxus.musictok.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GiftsAdapter extends RecyclerView.Adapter<GiftsAdapter.CoinPlansViewHolder> {
    private ArrayList<CoinPlan.Data> mList = new ArrayList<>();
    private GiftsAdapter.OnRecyclerViewItemClick onRecyclerViewItemClick;


    public GiftsAdapter.OnRecyclerViewItemClick getOnRecyclerViewItemClick() {
        return onRecyclerViewItemClick;
    }

    public void setOnRecyclerViewItemClick(GiftsAdapter.OnRecyclerViewItemClick onRecyclerViewItemClick) {
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    @NonNull
    @Override
    public GiftsAdapter.CoinPlansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gift_send, parent, false);
        return new GiftsAdapter.CoinPlansViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GiftsAdapter.CoinPlansViewHolder holder, int position) {
        holder.setModel(mList.get(position), position);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateData(List<CoinPlan.Data> list) {
        mList = (ArrayList<CoinPlan.Data>) list;
        notifyDataSetChanged();

    }

    public interface OnRecyclerViewItemClick {
        void onBuyButtonClick(CoinPlan.Data data, int position);
    }


    class CoinPlansViewHolder extends RecyclerView.ViewHolder {

        TextView coin;
        ImageView img_thumb;
        CoinPlansViewHolder(@NonNull View itemView) {
            super(itemView);
            coin=itemView.findViewById(R.id.coin);
            img_thumb=itemView.findViewById(R.id.img_thumb);
        }

        public void setModel(final CoinPlan.Data plan, final int position) {
            coin.setText(plan.getCoinAmount()+"Coins");
            Picasso.get()
                    .load(plan.getGiftIcon())
                    .into(img_thumb);

             itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecyclerViewItemClick.onBuyButtonClick(plan, position);

                }
            });
        }

    }
}

