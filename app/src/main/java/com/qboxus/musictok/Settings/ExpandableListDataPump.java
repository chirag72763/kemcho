package com.qboxus.musictok.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> account = new ArrayList<String>();
        account.add("Profile Setting");
        account.add("Saved Video");
        account.add("Request Verification");
        account.add("Data Use");
        account.add("Delete Account");

        List<String> privacy = new ArrayList<String>();
        privacy.add("Private Account");
        privacy.add("Who can comment?");
        privacy.add("Who can tag?");
        privacy.add("Who can like?");
        privacy.add("Hide my account from search engines");
        privacy.add("Hide download button from account");
        privacy.add("Block user list");
        privacy.add("Block Chat");

        List<String> accessibility = new ArrayList<String>();
        accessibility.add("Animated Thumbnail");
        accessibility.add("Theme & Colour");
        accessibility.add("Generate Account QR Code");

        List<String> activity = new ArrayList<String>();
        activity.add("Spend Time on Kemcho");
        activity.add("Reach");
        activity.add("Likes");
        activity.add("Gift received");

        List<String> notification = new ArrayList<String>();
        notification.add("Comment");
        notification.add("Tag");
        notification.add("Messages");
        notification.add("Likes");
        notification.add("Share");
        notification.add("Follower");
        notification.add("Fan");
        notification.add("Live");

        expandableListDetail.put("Account Settings", account);
        expandableListDetail.put("Privacy Settings", privacy);
        expandableListDetail.put("Accessibility", accessibility);
        expandableListDetail.put("Your Activity", activity);
        expandableListDetail.put("Notification Settings", notification);

        return expandableListDetail;
    }
}
