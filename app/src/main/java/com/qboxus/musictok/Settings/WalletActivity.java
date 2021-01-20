package com.qboxus.musictok.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.qboxus.musictok.R;
import com.qboxus.musictok.SimpleClasses.ApiRequest;
import com.qboxus.musictok.SimpleClasses.Callback;
import com.qboxus.musictok.SimpleClasses.Variables;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WalletActivity extends AppCompatActivity {

    TextView coins,coins2,spending_coin;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        findViewById(R.id.add_coin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBuyBottomSheet();
            }
        });

        coins = findViewById(R.id.tv_count);
        coins2 = findViewById(R.id.tv_total_earning);
        spending_coin = findViewById(R.id.tv_total_spending);

        swipeRefreshLayout = findViewById(R.id.swife);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                get_Data();
            }
        });
        swipeRefreshLayout.setRefreshing(true);

        get_Data();
    }

    private void showBuyBottomSheet() {
        CoinPurchaseSheetFragment fragment = new CoinPurchaseSheetFragment();
        fragment.show(getSupportFragmentManager(), fragment.getClass().getSimpleName());
    }

    private void get_Data() {
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("fb_id", Variables.sharedPreferences.getString(Variables.u_id, "0"));
            parameters.put("token", Variables.sharedPreferences.getString(Variables.device_token, "Null"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


        ApiRequest.Call_Api(this, Variables.getWallet, parameters, new Callback() {
            @Override
            public void Responce(String resp) {
                swipeRefreshLayout.setRefreshing(false);

                Parse_data(resp);
            }
        });
    }

    public void Parse_data(String responce) {

        swipeRefreshLayout.setRefreshing(false);
        try {
            JSONObject jsonObject = new JSONObject(responce);
            String code = jsonObject.optString("code");
            if (code.equalsIgnoreCase("200")) {

                JSONArray msg = jsonObject.optJSONArray("msg");
                for (int i = 0; i < msg.length(); i++) {
                    final JSONObject itemdata = msg.optJSONObject(i);
                    // final int coin = itemdata.optInt("coin");
                    coins.setText(itemdata.optString("coin"));
                    coins2.setText(itemdata.optString("coin"));

                    final int coin = itemdata.optInt("coin");
                    spending_coin.setText(itemdata.optString("my_wallet"));

                    findViewById(R.id.reedem).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (coin >= 2) {
                                Intent intent = new Intent(WalletActivity.this, RedeemActivity.class);
                                intent.putExtra("coins", itemdata.optString("coin"));
                                startActivity(intent);
                            } else {
                                Toast.makeText(WalletActivity.this, "Not enouge balance", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}
