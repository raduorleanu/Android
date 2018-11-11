package io.github.raduorleanu.and1.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileReader;

import io.github.raduorleanu.and1.R;
import io.github.raduorleanu.and1.models.Place;

public class NewPlaceActivity extends AppCompatActivity {

    //public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";

    private EditText idText;
    private EditText nameText;

    FirebaseDatabase db;
    DatabaseReference placesRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_place);

        // setDb
        db = FirebaseDatabase.getInstance();
        placesRef = db.getReference("places");

        idText = findViewById(R.id.edit_place_id);
        nameText = findViewById(R.id.edit_place_name);

        final Button button = findViewById(R.id.button_save);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Place newPlace = new Place();
                int idField = Integer.parseInt(idText.getText().toString());

                newPlace.setId(idField);
                newPlace.setAddress("PlaceHolder");
                newPlace.setName(nameText.getText().toString());
                newPlace.setPictureUrl("lol");

                placesRef.push().setValue(newPlace);
                finish();


                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(idText.getText()) || TextUtils.isEmpty(nameText.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String id = idText.getText().toString();
                    replyIntent.putExtra("new_id", id);
                    setResult(RESULT_OK, replyIntent);

                    String name = nameText.getText().toString();
                    replyIntent.putExtra("new_name", name);
                    setResult(RESULT_OK, replyIntent);
                }
//
//                if (TextUtils.isEmpty(nameText.getText())) {
//                    setResult(RESULT_CANCELED, replyIntent);
//                } else {
//                    String name = nameText.getText().toString();
//                    replyIntent.putExtra("new_name", name);
//                    setResult(RESULT_OK, replyIntent);
//                }
                finish();
            }
        });
    }
}
