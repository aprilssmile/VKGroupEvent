package com.example.olga.vkgroupevent;

import android.app.Application;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends FragmentActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    private static final String[] sMyScope = new String[]{
            VKScope.NOHTTPS,
            VKScope.GROUPS
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    ArrayAdapter<String> adapter;
    String[] items;

    Thread t;
    GroupMonitoring m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.navigation_bar);

        m = new GroupMonitoring();

        t = new Thread(new Runnable() {
            public void run() {
                m.Monitor();

                Context context = getApplicationContext();

                Intent intent = new Intent(context, LoginActivity.class);
// use System.currentTimeMillis() to have a unique ID for the pending intent
                PendingIntent pIntent = PendingIntent.getActivity(context, (int) System.currentTimeMillis(), intent, 0);

// build notification
// the addAction re-use the same intent to keep the example short
                Notification n  = new Notification.Builder(context)
                        .setContentTitle("New mail from " + "test@gmail.com")
                        .setContentText("Subject")
                        .setSmallIcon(R.drawable.ic_ab_app)
                        .setContentIntent(pIntent)
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_ab_app, "Call", pIntent)
                        .addAction(R.drawable.ic_ab_app, "More", pIntent)
                        .addAction(R.drawable.ic_ab_app, "And more", pIntent).build();


                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(0, n);
            }
        });

        items = new String[] {"Вход", "Список групп"};

        adapter = new ArrayAdapter<String>(this, R.layout.list_item, items);
        navigation_list=(ListView) findViewById(R.id.left_drawer);
        navigation_list.setAdapter(adapter);

        mPlanetTitles = new String[] {"1", "2"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_ab_done,  /* nav drawer icon to replace 'Up' caret */
                R.string.action_sign_in,  /* "open drawer" description */
                R.string.error_invalid_email  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
               // getActionBar().setTitle("123");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
               // getActionBar().setTitle("234");
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        navigation_list.setOnItemClickListener(new DrawerItemClickListener());

        if (VKSdk.isLoggedIn())
        {
            items[0] = "Выход";
            t.start();
            adapter.notifyDataSetChanged();
        }
    }

    ListView navigation_list;

    private class DrawerItemClickListener implements AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            selectItem(i);
        }
    }

    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        if (position == 0 && items[0] == "Вход") {
            VKSdk.login(this, sMyScope);
            items[0] = "Выход";
            t.start();
            adapter.notifyDataSetChanged();
        }
        else if (position == 0){
            VKSdk.logout();
            items[0] = "Вход";
            t.interrupt();
            adapter.notifyDataSetChanged();
        }

    }

    private void startTestActivity() {
        startActivity(new Intent(this, GroupActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                // Пользователь успешно авторизовался
                //startTestActivity();
            }
            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

