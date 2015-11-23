package com.example.olga.vkgroupevent;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiGroups;
import com.vk.sdk.api.model.VKApiCommunityArray;
import com.vk.sdk.util.VKUtil;

import java.util.ArrayList;

import static com.example.olga.vkgroupevent.R.layout.group_activity;

/**
 * A login screen that offers login via email/password.
 */
public class GroupActivity extends FragmentActivity {

    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.group_activity);

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, listItems);

        VKRequest request = VKApi.groups().getById(VKParameters.from(VKApiConst.GROUP_ID, "schoolvolley"));

        //VKApi.groups().

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response)
            {
//Do complete stuff
                ListView editText = (ListView)findViewById(R.id.listView);
                 VKApiCommunityArray o =  (VKApiCommunityArray)response.parsedModel;
                listItems.add(o.get(0).name);
                editText.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
            public void onError(VKError error) {
//Do error stuff
            }
            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
//I don't really believe in progress
            }
        });

        //mApplication = (MyApplication) getApplication();
        //fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        //VKSdk.login(this, sMyScope);
    }
}

