package websistems.com.androidretrofit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import websistems.com.androidretrofit.R;
import websistems.com.androidretrofit.activities.LoginActivity;
import websistems.com.androidretrofit.activities.MainActivity;
import websistems.com.androidretrofit.models.User;
import websistems.com.androidretrofit.storage.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtView = findViewById(R.id.txtView);

        User user = SharedPrefManager.getInstance(this).getUser();

        txtView.setText("Welcome Back " + user.getName());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
