package com.qboxus.musictok.Settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.qboxus.musictok.Main_Menu.MainMenuActivity;
import com.qboxus.musictok.R;
import com.qboxus.musictok.SimpleClasses.ApiRequest;
import com.qboxus.musictok.SimpleClasses.Callback;
import com.qboxus.musictok.SimpleClasses.Functions;
import com.qboxus.musictok.SimpleClasses.Variables;

import org.json.JSONException;
import org.json.JSONObject;

public class RedeemActivity extends AppCompatActivity {

    TextView coins;
    public String coindCount;
    Spinner spinner;
    public String requestType;
    String accountId;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reddem);
        coins = findViewById(R.id.tv_count);
        spinner = findViewById(R.id.spinner);
        editText = findViewById(R.id.edit_text);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                accountId = editable.toString();

            }
        });

        if (getIntent().getStringExtra("coins") != null) {
            coindCount = getIntent().getStringExtra("coins");
        }

        coins.setText(coindCount);


        final String[] paymentTypes = getResources().getStringArray(R.array.payment);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.black));
                requestType = paymentTypes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.i("", "");
            }
        });

        findViewById(R.id.btn_redeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accountId != null && !TextUtils.isEmpty(accountId)) {
                    callApiToRedeem();
                } else {
                    Toast.makeText(RedeemActivity.this, "Please enter your account ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void callApiToRedeem() {
        JSONObject params = new JSONObject();

        try {
            params.put("fb_id", Variables.user_id);
            params.put("coin", coindCount);
            params.put("type", requestType);
            params.put("account", accountId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Functions.Show_loader(this, false, false);
        ApiRequest.Call_Api(this, Variables.reedem, params, new Callback() {
            @Override
            public void Responce(String resp) {
                Functions.cancel_loader();
                try {
                    JSONObject jsonObject = new JSONObject(resp);
                    String code = jsonObject.optString("code");
                    if (code.equalsIgnoreCase("200")) {
                        onBackPressed();
                        Intent intent = new Intent(RedeemActivity.this, MainMenuActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(RedeemActivity.this, "Request Submitted Successfully", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
