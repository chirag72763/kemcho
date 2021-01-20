package com.qboxus.musictok.Settings;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.qboxus.musictok.Main_Menu.MainMenuActivity;
import com.qboxus.musictok.R;
import com.qboxus.musictok.SimpleClasses.API_CallBack;
import com.qboxus.musictok.SimpleClasses.ApiRequest;
import com.qboxus.musictok.SimpleClasses.Callback;
import com.qboxus.musictok.SimpleClasses.Functions;
import com.qboxus.musictok.SimpleClasses.Variables;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CoinPurchaseSheetRozFragment extends BottomSheetDialogFragment implements PaymentResultListener {


    View view;
    Context context;
    CoinPlansAdapter adapter = new CoinPlansAdapter();
    public String coins = "";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog1) {
                BottomSheetDialog dialog = (BottomSheetDialog) dialog1;
                dialog.setCanceledOnTouchOutside(true);
            }
        });
        return bottomSheetDialog;
    }

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_purchase_coin_sheet, container, false);
        context = getContext();
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        initView();
        initListeners();
        return view;
    }

    private void initListeners() {
        adapter.setOnRecyclerViewItemClick(new CoinPlansAdapter.OnRecyclerViewItemClick() {
            @Override
            public void onBuyButtonClick(CoinPlan.Data data, int position) {
                coins = data.getCoinAmount();
              //  bp.purchase(getActivity(), data.getPlaystoreProductId());
                startPayment(data.getCoinAmount());
            }
        });
    }

    private void startPayment(String coinAmount) {
        final Activity activity = getActivity();

        final Checkout co = new Checkout();
        co.setKeyID("rzp_test_oMajfCfeTlbIHz");

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", coinAmount);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9876543210");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    private void initView() {
        fetchCoinPlans();
        Checkout.preload(getContext());

    }

    private void fetchCoinPlans() {
        Log.d(Variables.tag, MainMenuActivity.token);
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("fb_id", Variables.sharedPreferences.getString(Variables.u_id, "0"));
            parameters.put("token", MainMenuActivity.token);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiRequest.Call_Api(context, Variables.coin_plan, parameters, new Callback() {
            @Override
            public void Responce(String resp) {
                Parse_data(resp);
            }
        });

    }

    private void Parse_data(String responce) {
        try {
            JSONObject jsonObject = new JSONObject(responce);
            String code = jsonObject.optString("code");
            if (code.equals("200")) {
                JSONArray msgArray = jsonObject.getJSONArray("msg");

                ArrayList<CoinPlan.Data> temp_list = new ArrayList();
                for (int i = 0; i < msgArray.length(); i++) {
                    JSONObject itemdata = msgArray.optJSONObject(i);
                    CoinPlan.Data item = new CoinPlan.Data();
                    item.setCoinAmount(itemdata.optString("coin_amount"));
                    item.setCoinPlanPrice(itemdata.optString("coin_plan_price"));
                    item.setPlaystoreProductId(itemdata.optString("playstore_product_id"));
                    temp_list.add(item);
                }

                adapter.updateData(temp_list);

            } else {
                Toast.makeText(context, "" + jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    Boolean coinSend = false;

    private void purchaseCoin() {
        Functions.Call_Api_For_purchase_coin(getActivity(),coins ,new API_CallBack() {

            @Override
            public void ArrayData(ArrayList arrayList) {

            }

            @Override
            public void OnSuccess(String responce) {
                Log.d("sendCoinResult",responce);
                try {
                    JSONObject jsonObject=new JSONObject(responce);
                    String code=jsonObject.optString("code");
                    if(code.equals("200")){
                        JSONArray msgArray=jsonObject.getJSONArray("msg");
                        for (int i=0;i<msgArray.length();i++) {
                            JSONObject itemdata = msgArray.optJSONObject(i);
                            coinSend = itemdata.optBoolean("status");
                            showPurchaseResultToast(coinSend);
                        }



                    }else {
                        Toast.makeText(context, ""+jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

            @Override
            public void OnFail(String responce) {

            }
        });

    }

    private void showPurchaseResultToast(Boolean status) {
        dismiss();
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) view.findViewById(R.id.custom_toast_layout_id));

        TextView tvToastMessage = (TextView) layout.findViewById(R.id.tv_toast_message);
        ImageView imageView =  layout.findViewById(R.id.image);

        if (status != null && status) {
            tvToastMessage.setText("Coins Added To Your Wallet\nSuccessfully..");
        } else {
            String string = "Something Went Wrong !";
            tvToastMessage.setText(string);
        }
        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }


    @Override
    public void onPaymentSuccess(String s) {
        purchaseCoin();
    }

    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(context, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
          //  Log.e(TAG, "Exception in onPaymentError", e);
        }
    }
}
