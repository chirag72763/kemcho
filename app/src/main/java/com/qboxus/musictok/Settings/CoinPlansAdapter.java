package com.qboxus.musictok.Settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qboxus.musictok.R;

import java.util.ArrayList;
import java.util.List;

public class CoinPlansAdapter extends RecyclerView.Adapter<CoinPlansAdapter.CoinPlansViewHolder> {
    private ArrayList<CoinPlan.Data> mList = new ArrayList<>();
    private OnRecyclerViewItemClick onRecyclerViewItemClick;


    public OnRecyclerViewItemClick getOnRecyclerViewItemClick() {
        return onRecyclerViewItemClick;
    }

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    @NonNull
    @Override
    public CoinPlansViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coin_plans, parent, false);
        return new CoinPlansViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinPlansViewHolder holder, int position) {
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
        Button byu;
        CoinPlansViewHolder(@NonNull View itemView) {
            super(itemView);
            coin=itemView.findViewById(R.id.coin);
            byu=itemView.findViewById(R.id.btn_buy);
        }

        public void setModel(final CoinPlan.Data plan, final int position) {
            coin.setText(plan.getCoinAmount()+"Coins");
            byu.setText("$"+plan.getCoinPlanPrice());
            byu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onRecyclerViewItemClick.onBuyButtonClick(plan, position);

                }
            });
        }

    }
}

