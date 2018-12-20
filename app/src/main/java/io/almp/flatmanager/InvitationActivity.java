package io.almp.flatmanager;

import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InvitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        String invitationCodeString = this.getSharedPreferences("_", MODE_PRIVATE).getString("invitation_code", "null");
        TextView invitationCodeTextView = findViewById(R.id.invitation_code_text_view);
        invitationCodeTextView.setText(invitationCodeString);

        String fullInvitation = "Join my flat in Flatmate with this code: " + invitationCodeString;

        Button shareCode = findViewById(R.id.share_invitation_button);
        shareCode.setOnClickListener(v-> ShareCompat.IntentBuilder.from(this).setChooserTitle("Invite fiends").setType("text/plain").setText(fullInvitation).startChooser());
    }
}
