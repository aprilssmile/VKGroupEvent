package com.example.olga.vkgroupevent;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


public class GroupMonitoring {
    private HashMap<Integer, Date> _groups;

    private HashMap<Long, WallMessages.Response.Item> _messages;

    public GroupMonitoring() {
        Calendar cal = Calendar.getInstance();
        Date current = cal.getTime();

        _groups = new HashMap<Integer, Date>();
        _groups.put(108397509, current);

        _messages = new HashMap<Long, WallMessages.Response.Item>();
    }

    public void Monitor() {
        while (true) {
            // Делаем запрос к последним записям, есть что новее?
            VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, -108397509, VKApiConst.COUNT, 5));

            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        WallMessages messages = mapper.readValue(response.responseString, WallMessages.class);

                        for (int i = 0; i < messages.response.items.length; i++)
                        {
                            if (!_messages.containsKey(messages.response.items[i].id)) {
                                _messages.put(messages.response.items[i].id, messages.response.items[i]);


                            }
                        }
                        Integer t = 0;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                public void onError(VKError error) {
//Do error stuff
                    int i = 123;
                }

                @Override
                public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
//I don't really believe in progress
                }
            });

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
