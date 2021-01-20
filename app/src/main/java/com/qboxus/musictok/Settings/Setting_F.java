package com.qboxus.musictok.Settings;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.qboxus.musictok.Accounts.Request_Varification_F;
import com.qboxus.musictok.Main_Menu.MainMenuActivity;
import com.qboxus.musictok.Main_Menu.RelateToFragment_OnBack.RootFragment;
import com.qboxus.musictok.R;
import com.qboxus.musictok.SimpleClasses.Variables;
import com.qboxus.musictok.SimpleClasses.Webview_F;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class Setting_F extends RootFragment implements View.OnClickListener {

    View view;
    Context context;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    public Setting_F() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_setting, container, false);
        context=getContext();
        expandableListView = (ExpandableListView)view.findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });
        view.findViewById(R.id.Goback).setOnClickListener(this);
        view.findViewById(R.id.request_verification_txt).setOnClickListener(this);
        view.findViewById(R.id.privacy_policy_txt).setOnClickListener(this);
        view.findViewById(R.id.logout_txt).setOnClickListener(this);

        view.findViewById(R.id.wallet_txt).setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.Goback:
                getActivity().onBackPressed();
                break;

            case R.id.request_verification_txt:
                Open_request_verification();
                break;

            case R.id.privacy_policy_txt:
                Open_Privacy_url();
                break;
            case R.id.wallet_txt:
             startActivity(new Intent(context,WalletActivity.class));
                break;
            case R.id.logout_txt:
                Logout();
                break;
        }
    }


    public void Open_request_verification(){
        Request_Varification_F request_varification_f = new Request_Varification_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        transaction.addToBackStack(null);
        transaction.replace(R.id.Setting_F, request_varification_f).commit();
    }

    public void Open_Privacy_url(){
        Webview_F webview_f = new Webview_F();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left, R.anim.in_from_left, R.anim.out_to_right);
        Bundle bundle=new Bundle();
        bundle.putString("url",Variables.privacy_policy);
        bundle.putString("title","Privacy Policy");
        webview_f.setArguments(bundle);
        transaction.addToBackStack(null);
        transaction.replace(R.id.Setting_F, webview_f).commit();
    }

    // this will erase all the user info store in locally and logout the user
    public void Logout(){
        SharedPreferences.Editor editor= Variables.sharedPreferences.edit();
        editor.putString(Variables.u_id,"").clear();
        editor.putString(Variables.u_name,"").clear();
        editor.putString(Variables.u_pic,"").clear();
        editor.putBoolean(Variables.islogin,false).clear();
        editor.commit();
        getActivity().finish();
        startActivity(new Intent(getActivity(), MainMenuActivity.class));
    }


}
