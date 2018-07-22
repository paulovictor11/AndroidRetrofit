package websistems.com.androidretrofit.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import websistems.com.androidretrofit.R;
import websistems.com.androidretrofit.models.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder>{

    private Context context;
    private List<User> users;

    public UsersAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_users, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        User user = users.get(position);

        holder.txtName.setText(user.getName());
        holder.txtEmail.setText(user.getEmail());
        holder.txtSchool.setText(user.getSchool());
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }

    class UsersViewHolder extends RecyclerView.ViewHolder{

        TextView txtName, txtEmail, txtSchool;

        UsersViewHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtRcName);
            txtEmail = itemView.findViewById(R.id.txtRcEmail);
            txtSchool = itemView.findViewById(R.id.txtRcSchool);
        }
    }
}
