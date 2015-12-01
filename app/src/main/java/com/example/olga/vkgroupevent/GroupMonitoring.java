package com.example.olga.vkgroupevent;


import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by Olga on 01.12.2015.
 */
public class GroupMonitoring {

    private HashMap<Integer, Date> _groups;

    public GroupMonitoring()
    {
        Calendar cal = Calendar.getInstance();
        Date current = cal.getTime();

        _groups = new HashMap<Integer, Date>();
        _groups.put(108397509, current);


    }

    public void Monitor()
    {
        while (true)
        {
            // Делаем запрос к последним записям, есть что новее?
            VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.OWNER_ID, -108397509, VKApiConst.COUNT, 5));
                    //VKApi.groups().

                    request.executeWithListener(new VKRequest.VKRequestListener() {
                        @Override
                        public void onComplete(VKResponse response) {
//Do complete stuff
                            Object o = response.parsedModel;

                        }

                        public void onError(VKError error) {
//Do error stuff
                        }

                        @Override
                        public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
//I don't really believe in progress
                        }
            });

            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
