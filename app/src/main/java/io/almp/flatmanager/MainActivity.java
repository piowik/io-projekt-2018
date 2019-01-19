package io.almp.flatmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.messaging.FirebaseMessaging;

import io.almp.flatmanager.rest.ApiUtils;

/**
 *  Main class for activity in menu.
 */

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        int flat_id = getSharedPreferences("_", MODE_PRIVATE).getInt("flat_id", 0);
        String invitationCode = getSharedPreferences("_", MODE_PRIVATE).getString("invitation_code", "");
        Log.e("Invitation code", invitationCode);
        ImageView mainImageView = findViewById(R.id.image_view_main);
        String imageUrl = ApiUtils.BASE_URL + "images/flats/" + flat_id + ".png";
        GlideApp.with(this)
                .load(imageUrl)
                .apply(RequestOptions.fitCenterTransform())
                .into(mainImageView);
        LinearLayout chatLayout = findViewById(R.id.chat);
        LinearLayout dutiesLayout = findViewById(R.id.duties);
        LinearLayout rentLayout = findViewById(R.id.rent);
        LinearLayout shoppingLayout = findViewById(R.id.shopping);

        chatLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
        });
        dutiesLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, DutiesActivity.class);
            startActivity(intent);
        });

        rentLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, RentActivity.class);
            startActivity(intent);
        });
        shoppingLayout.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShoppingActivity.class);
            startActivity(intent);
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_invite_friends) {
            Intent intent = new Intent(this, InvitationActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_manage_flat) {
            Intent intent = new Intent(this, ManageFlatActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_logout) {

            SharedPreferences sharedPref = getSharedPreferences("_", MODE_PRIVATE);
            io.almp.flatmanager.service.FirebaseMessagingService.sendPost(getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L),"empty");
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putLong("user_id", 0L);
            editor.putInt("flat_id", -1);
            editor.putString("user_token", "empty");
            editor.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
