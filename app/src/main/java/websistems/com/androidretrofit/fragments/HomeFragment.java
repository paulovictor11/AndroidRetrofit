package websistems.com.androidretrofit.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import websistems.com.androidretrofit.R;
import websistems.com.androidretrofit.storage.SharedPrefManager;

public class HomeFragment extends Fragment{

    private TextView txtEmail, txtName, txtSchool;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtEmail = view.findViewById(R.id.txtEmail);
        txtName = view.findViewById(R.id.txtName);
        txtSchool = view.findViewById(R.id.txtSchool);

        txtEmail.setText(SharedPrefManager.getInstance(getActivity()).getUser().getEmail());
        txtName.setText(SharedPrefManager.getInstance(getActivity()).getUser().getName());
        txtSchool.setText(SharedPrefManager.getInstance(getActivity()).getUser().getSchool());
    }
}
