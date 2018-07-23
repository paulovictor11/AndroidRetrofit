package websistems.com.androidretrofit.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import websistems.com.androidretrofit.R;
import websistems.com.androidretrofit.activities.LoginActivity;
import websistems.com.androidretrofit.activities.ProfileActivity;
import websistems.com.androidretrofit.api.RetrofitClient;
import websistems.com.androidretrofit.models.DefaultResponse;
import websistems.com.androidretrofit.models.LoginResponse;
import websistems.com.androidretrofit.models.User;
import websistems.com.androidretrofit.storage.SharedPrefManager;

public class SettingsFragment extends Fragment implements View.OnClickListener{

    private EditText edtEmail, edtName, edtSchool;
    private EditText edtCurrentePassword, edtNewPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtEmail = view.findViewById(R.id.edtUpdateEmail);
        edtName = view.findViewById(R.id.edtUpdateName);
        edtSchool = view.findViewById(R.id.edtUpdateSchool);
        edtCurrentePassword = view.findViewById(R.id.edtCurrentPasword);
        edtNewPassword = view.findViewById(R.id.edtNewPasword);

        view.findViewById(R.id.btnSave).setOnClickListener(this);
        view.findViewById(R.id.btnChange).setOnClickListener(this);
        view.findViewById(R.id.btnLogout).setOnClickListener(this);
        view.findViewById(R.id.btnDelete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave:
                updateProfile();
                break;

            case R.id.btnChange:
                updatePassword();
                break;

            case R.id.btnLogout:
                logout();
                break;

            case R.id.btnDelete:
                break;
        }
    }

    private void updateProfile() {
        String email = edtEmail.getText().toString().trim();
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

        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .updateUser(
                        user.getId(),
                        email,
                        name,
                        school
                );
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();

                if (!response.body().isError()){
                    SharedPrefManager.getInstance(getActivity()).saveUser(response.body().getUser());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    private void updatePassword() {
        String currentPassword = edtCurrentePassword.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();

        if (currentPassword.isEmpty()){
            edtCurrentePassword.setError("Password required");
            edtCurrentePassword.requestFocus();
            return;
        }

        if (currentPassword.length() < 6){
            edtCurrentePassword.setError("Password should be atleast 6 character long");
            edtCurrentePassword.requestFocus();
            return;
        }

        if (newPassword.isEmpty()){
            edtNewPassword.setError("Password required");
            edtNewPassword.requestFocus();
            return;
        }

        if (newPassword.length() < 6){
            edtNewPassword.setError("Password should be atleast 6 character long");
            edtNewPassword.requestFocus();
            return;
        }

        User user = SharedPrefManager.getInstance(getActivity()).getUser();

        Call<DefaultResponse> call= RetrofitClient
                .getInstance()
                .getApi()
                .updatePassword(
                        currentPassword,
                        newPassword,
                        user.getEmail()
                );
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }

    private void logout() {
        SharedPrefManager.getInstance(getActivity()).clear();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
