package io.github.raduorleanu.and1.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.raduorleanu.and1.R;
import io.github.raduorleanu.and1.models.User;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private final LayoutInflater mInflater;
    private ArrayList<User> users;

    public UserListAdapter(Context context, ArrayList<User> users) {
        mInflater = LayoutInflater.from(context);
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = mInflater.inflate(R.layout.recyclerview_user, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder userViewHolder, int i) {
        if (users != null) {
            final User user = users.get(i);
            userViewHolder.userName.setText(user.getUsername());
            userViewHolder.userId.setText(String.valueOf(user.getId()));
        } else {
            userViewHolder.userName.setText("No user");
        }
    }

    @Override
    public int getItemCount() {
        if(users != null) {
            return users.size();
        }
        return 0;
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        private final TextView userName;
        private final TextView userId;
        private final Button addUser;

        private UserViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name_);
            userId = itemView.findViewById(R.id.user_id_);
            addUser = itemView.findViewById(R.id.add_me_button);
        }
    }

}
