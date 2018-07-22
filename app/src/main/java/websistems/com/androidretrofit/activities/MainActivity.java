package websistems.com.androidretrofit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import websistems.com.androidretrofit.models.DefaultResponse;
import websistems.com.androidretrofit.R;
import websistems.com.androidretrofit.api.RetrofitClient;
import websistems.com.androidretrofit.storage.SharedPrefManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtEmail, edtPass, edtName, edtSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPassword);
        edtName = findViewById(R.id.edtName);
        edtSchool = findViewById(R.id.edtSchool);

        findViewById(R.id.btnSignup).setOnClickListener(this);
        findViewById(R.id.txtLogin).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignup:
                userSignUp();
                break;

            case R.id.txtLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userSignUp() {
        String email = edtEmail.getText().toString().trim();
        String pass = edtPass.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String school = edtSchool.getText().toString().trim();

        if (email.isEmpty()){
            edtEmail.setError("Email is required");
            edtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.setError("Enter a valid email");
            edtEmail.requestFocus();
            return;
        }

        if (pass.isEmpty()){
            edtPass.setError("Password required");
            edtPass.requestFocus();
            return;
        }

        if (pass.length() < 6){
            edtPass.setError("Password should be atleast 6 character long");
            edtPass.requestFocus();
            return;
        }

        if (name.isEmpty()){
            edtName.setError("Name required");
            edtName.requestFocus();
            return;
        }

        if (school.isEmpty()){
            edtSchool.setError("School required");
            edtSchool.requestFocus();
            return;
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(email, pass, name, school);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201){
                    DefaultResponse dr = response.body();
                    Toast.makeText(MainActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();
                } else if (response.code() == 422){
                    Toast.makeText(MainActivity.this, "User Already Exists", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }
}
