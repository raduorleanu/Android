package io.github.raduorleanu.and1.view_models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import io.github.raduorleanu.and1.models.User;

public class UserViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<User>> users;

    public UserViewModel(@NonNull Application application) {
        super(application);
        this.users = new MutableLiveData<>();
    }

    public void setUsers(ArrayList<User> users) {
        this.users.setValue(users);
    }

    public MutableLiveData<ArrayList<User>> getUsers() {
        return users;
    }
}
