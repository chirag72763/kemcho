package com.qboxus.musictok.Home;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.qboxus.musictok.R;


public class CustomDialogBuilder {
    private Context mContext;
    private Dialog mBuilder = null;

    public CustomDialogBuilder(Context context) {
        this.mContext = context;
        if (mContext != null) {
            mBuilder = new Dialog(mContext);
            mBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mBuilder.setCancelable(false);
            mBuilder.setCanceledOnTouchOutside(false);

            if (mBuilder.getWindow() != null) {
                mBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }


    public void showSendCoinResultDialogue(final boolean success, final OnResultButtonClick onResultButtonClick) {
        if (mContext == null)
            return;

        TextView send_title,send_message;
        mBuilder.setCancelable(true);
        mBuilder.setCanceledOnTouchOutside(true);
        mBuilder.setContentView(R.layout.lout_send_result_popup);
        send_title = mBuilder.findViewById(R.id.tv_send_title);
        send_message = mBuilder.findViewById(R.id.tv_send_message);
        if (success)
        {
            send_title.setText("Coin sent to the creator\\nsuccessfully..");
            send_message.setText("Coin sent to the creator\\nsuccessfully..");
            mBuilder.findViewById(R.id.lout_purchase).setVisibility(View.GONE);
            mBuilder.findViewById(R.id.lout_ok).setVisibility(View.VISIBLE);

        }else {
            send_title.setText("Insufficient Balance !");
            send_message.setText("Add some stars to\\nyour wallet !!");
            mBuilder.findViewById(R.id.lout_purchase).setVisibility(View.VISIBLE);
            mBuilder.findViewById(R.id.lout_ok).setVisibility(View.GONE);
        }
        mBuilder.findViewById(R.id.lout_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBuilder.dismiss();
                onResultButtonClick.onButtonClick(success);
            }
        });
        mBuilder.show();
    }

    public void showSendCoinDialogue(final OnCoinDismissListener onCoinDismissListener) {
        if (mContext == null)
            return;

        mBuilder.setCancelable(true);
        mBuilder.setCanceledOnTouchOutside(true);

        mBuilder.setContentView(R.layout.lout_send_coin);
        mBuilder.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBuilder.dismiss();
                onCoinDismissListener.onCancelDismiss();
            }
        });
        mBuilder.findViewById(R.id.lout_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBuilder.dismiss();
                onCoinDismissListener.on5Dismiss();
            }
        });
        mBuilder.findViewById(R.id.lout_10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBuilder.dismiss();
                onCoinDismissListener.on10Dismiss();
            }
        });
        mBuilder.findViewById(R.id.lout_20).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBuilder.dismiss();
                onCoinDismissListener.on20Dismiss();
            }
        });
        mBuilder.show();

    }

    public void hideLoadingDialog() {
        try {
            mBuilder.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnResultButtonClick {
        void onButtonClick(boolean success);
    }

    public interface OnDismissListener {
        void onPositiveDismiss();

        void onNegativeDismiss();
    }

    public interface OnCoinDismissListener {
        void onCancelDismiss();

        void on5Dismiss();

        void on10Dismiss();

        void on20Dismiss();

    }

}