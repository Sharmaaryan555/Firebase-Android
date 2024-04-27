package com.example.firebaseconnectivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private Button logout;

    private EditText edit;
    private Button database;

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logout = findViewById(R.id.logout);
        edit = findViewById(R.id.edit);
        database = findViewById(R.id.add);
        listView = findViewById(R.id.listview);

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
//                    FirebaseDatabase.getInstance().getReference().child("Languages").push().child("Name").setValue(txt_name);
                    FirebaseDatabase.getInstance().getReference().child("Information").child("Branch1").child("email").setValue(txt_name);
                }
            }
        });

        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Information");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
//                    list.add(snapshot.getValue().toString());
                    Information info = snapshot.getValue(Information.class);
                    String txt = info.getName() + " : " + info.getEmail();
                    list.add(txt);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}