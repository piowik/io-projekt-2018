package io.almp.flatmanager.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import io.almp.flatmanager.ChatActivity;
import io.almp.flatmanager.MainActivity;
import io.almp.flatmanager.R;
import io.almp.flatmanager.RentActivity;
import io.almp.flatmanager.model.api.SimpleErrorAnswer;
import io.almp.flatmanager.rest.ApiInterface;
import io.almp.flatmanager.rest.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mateusz Zaremba on 27.11.2018.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Class openClass = MainActivity.class;
        int photo = R.drawable.common_google_signin_btn_icon_dark;
        String name = "";
        String description = "";
        if (remoteMessage.getData().size() > 0) {
            Log.e("Got data:", remoteMessage.getData().toString() + "");
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("message");
            String imageUrl = remoteMessage.getData().get("image");
            String channel = remoteMessage.getData().get("chann_id");
            if (channel.equals("1")) {
                channel = getString(R.string.channel_id_chat);
                openClass = ChatActivity.class;
                photo = R.drawable.ic_chat_black_128dp;
                name = getString(R.string.channel_name_chat);
                description = getString(R.string.channel_chat_description);

                Intent bIntent = new Intent("reload_message_list");
                bIntent.putExtra("newMessage", "1");
                LocalBroadcastManager.getInstance(this).sendBroadcast(bIntent);

            } else if (channel.equals("2")) {
                channel = getString(R.string.channel_id_rent);
                openClass = RentActivity.class;
                photo = R.drawable.ic_attach_money_black_24dp;
                name = getString(R.string.channel_name_rent);
                description = getString(R.string.channel_rent_description);
            }
            if (!TextUtils.isEmpty(imageUrl)) {
                Log.e("Image not empty", "not");
                if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {
                    Bitmap bitmap = getBitmapFromURL(imageUrl);
                    if (bitmap != null)
                        showImageNotification(bitmap, title, body, channel, openClass, photo,name,description);
                    else
                        showNotification(title, body, channel, openClass, photo,name,description);
                }
            } else
                showNotification(title, body, channel, openClass, photo,name,description);
            Log.e("DataTitle", title);
        } else if (remoteMessage.getNotification() != null) {
            Log.e("NotificationTitle", "T:" + remoteMessage.getNotification().getTitle());
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            showNotification(title, body, "", openClass, photo,name,description);
        }

    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("newToken", token);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fbtoken", token).apply();
        long uid = getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L);
        if (uid != 0L)
            sendPost(uid, token);

    }

    private void showNotification(String title, String message, String channel, Class openClass, int photo,String name, String description) {
        createNotificationChannel(channel,name,description);
        Intent i = new Intent(this, openClass);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channel)
                .setSmallIcon(photo)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setChannelId(channel);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, mBuilder.build());
    }

    private void showImageNotification(Bitmap bitmap, String title, String message, String channel, Class classToOpen, int photo,String name, String description) {
        createNotificationChannel(channel,name,description);
        Intent i = new Intent(this, classToOpen);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(bitmap);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, channel)
                .setSmallIcon(photo)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(bigPictureStyle)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setChannelId(channel);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(0, mBuilder.build());
    }


    private void createNotificationChannel(String channel_, String name_, String description_) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channel_, name_, importance);
            channel.setDescription(description_);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void sendPost(Long uid, String fbTokenStr) {
        ApiInterface mAPIService = ApiUtils.getAPIService();

        mAPIService.updateFirebaseToken(uid, fbTokenStr).enqueue(new Callback<SimpleErrorAnswer>() {
            @Override
            public void onResponse(Call<SimpleErrorAnswer> call, Response<SimpleErrorAnswer> response) {
                Log.e("RespMsg", response.message() + "!");
                Log.e("RespBody", response.toString() + "!");
                if (response.isSuccessful()) {
                    if (!response.body().isError())
                        Log.e("POST", "Updated firebase token");
                    else
                        Log.e("POST", "Error response while updating firebase token.");
                }
            }

            @Override
            public void onFailure(Call<SimpleErrorAnswer> call, Throwable t) {
                Log.e("POST", "Unable to submit post to API.");
            }
        });
    }

    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fbtoken", "empty");
    }

}