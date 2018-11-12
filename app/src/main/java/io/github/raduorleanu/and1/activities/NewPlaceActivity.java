package io.github.raduorleanu.and1.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import io.github.raduorleanu.and1.R;
import io.github.raduorleanu.and1.database.PlacesDb;
import io.github.raduorleanu.and1.interfaces.IDatabasePlaceAdapter;
import io.github.raduorleanu.and1.interfaces.IDatabaseResponse;
import io.github.raduorleanu.and1.models.Place;
import io.github.raduorleanu.and1.models.User;

public class NewPlaceActivity extends AppCompatActivity implements IDatabaseResponse {

    //public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText idText;
    private EditText nameText;
    private IDatabasePlaceAdapter db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);


        db = new PlacesDb();

        idText = findViewById(R.id.edit_place_id);
        nameText = findViewById(R.id.edit_place_name);

        final Button button = findViewById(R.id.button_save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(idText.getText()) || TextUtils.isEmpty(nameText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    Place pl = new Place();
                    pl.setId(idText.getText().toString());
                    pl.setName(nameText.getText().toString());
//                    Place.PlaceBuilder builder = new Place.PlaceBuilder(idText.getText().toString());
                    db.addPlace(pl);
                    String id = idText.getText().toString();
                    replyIntent.putExtra("new_id", id);
                    String name = nameText.getText().toString();
                    replyIntent.putExtra("new_name", name);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }

    @Override
    public void alreadyGoingCallback(List<User> places) {
        // here you can update the gui
    }
}