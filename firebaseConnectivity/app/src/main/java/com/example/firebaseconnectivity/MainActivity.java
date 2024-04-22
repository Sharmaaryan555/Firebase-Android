package com.example.firebaseconnectivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button logout;

    private EditText edit;
    private Button database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        database = findViewById(R.id.add);

//        FirebaseDatabase.getInstance().getReference().child("Programming Knowledge").child("Android").setValue("abcd");

//        HashMap<String , Object> map = new HashMap<>();
//        map.put("Name","Aryan");
//        map.put("Class","B.Tech");
//        map.put("Roll number","2821262");

//        FirebaseDatabase.getInstance().getReference().child("Programming Knowledge").child("Multiple Values").updateChildren(map);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "Logged Out ", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,StartActivity.class));
            }
        });

        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_name = edit.getText().toString();
                if(txt_name.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "No name is Entered", Toast.LENGTH_SHORT).show();
                }
                else {
                    FirebaseDatabase.getInstance().getReference().child("Collage").push().child("Name").setValue(txt_name);
                }
            }
        });


    }
}