package io.github.raduorleanu.and1.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Objects;

import io.github.raduorleanu.and1.R;
import io.github.raduorleanu.and1.adapters.UserListAdapter;
import io.github.raduorleanu.and1.models.User;
import io.github.raduorleanu.and1.view_models.UserViewModel;

public class AlreadyGoing extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_going);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ArrayList<User> users = (ArrayList<User>) getIntent().getSerializableExtra("data");

        RecyclerView recyclerView = findViewById(R.id.recyclerview_user_content);
        final UserListAdapter adapter = new UserListAdapter(this, users);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.setUsers(users);

        userViewModel.getUsers().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(@Nullable ArrayList<User> users) {
                //add changes if any
            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

}
