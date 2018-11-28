package io.almp.flatmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import io.almp.flatmanager.service.FirebaseMessagingService;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
