package io.almp.flatmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.google.firebase.messaging.FirebaseMessaging;

import io.almp.flatmanager.service.FirebaseMessagingService;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(this.getSharedPreferences("_", MODE_PRIVATE).getLong("user_id", 0L)!=0L &&
                !getSharedPreferences("_", MODE_PRIVATE).getString("user_token", "empty").equals("empty")){
            if (getSharedPreferences("_", MODE_PRIVATE).getInt("flat_id", 0) == 0) {
                Intent intent = new Intent(this, NoFlatActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
            else {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

        }
        LoginFragment fragment = new LoginFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
        Log.e("FCMTOKEN", "TOKEN: " + FirebaseMessagingService.getToken(this));
        FirebaseMessaging.getInstance().subscribeToTopic("global")
                .addOnCompleteListener(task -> {
                    String msg = "subscribed to global";
                    if (!task.isSuccessful()) {
                        msg = "couldnt subscribe to global";
                    }
                    Log.d("FCM", msg);
                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                });
    }


}
