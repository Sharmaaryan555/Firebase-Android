package com.example.firebaseconnectivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button logout;

    private EditText edit;
    private Button database;
    private Uri imageUri;

    private static final int IMAGE_REQUEST  = 2;

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
//                String txt_name = edit.getText().toString();
//                if(txt_name.isEmpty())
//                {
//                    Toast.makeText(MainActivity.this, "No name is Entered", Toast.LENGTH_SHORT).show();
//                }
//                else {
////                    FirebaseDatabase.getInstance().getReference().child("Languages").push().child("Name").setValue(txt_name);
//                    FirebaseDatabase.getInstance().getReference().child("Information").child("Branch1").child("email").setValue(txt_name);
//                }
                OpenImage();
                
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

//        FireStore DataBase

//        FirebaseFirestore db = FirebaseFirestore.getInstance();

//        Map<String ,Object> city = new HashMap<>();
//        city.put("Name" ,"Panipat");
//        city.put("State","Haryana");
//        city.put("Country","India");

//        db.collection("Cities").document("JSR").set(city).addOnCompleteListener(new OnCompleteListener<Void>() {
//        @Override
//        public void onComplete(@NonNull Task<Void> task) {
//            if (task.isSuccessful()){
//                Toast.makeText(MainActivity.this, "Values added to FireStore Database", Toast.LENGTH_SHORT).show();
//            }
//        }
//    });

//        Merging The Firebase data


//        Map<String,Object> data = new HashMap<>();
//        data.put("Capital","Delhi");

//        db.collection("Cities").document("JSR").set(data , SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(MainActivity.this, "Merge Successful!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        Map<String,Object> data = new HashMap<>();
//        data.put("Name","Tokyo");
//        data.put("Capital","Japan");
//
//        db.collection("Cities").add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentReference> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(MainActivity.this, "values added with unique Id", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        To Update the Values in firestore Database
//        DocumentReference reference1 =FirebaseFirestore.getInstance().collection("Cities").document("JSR");
//        reference1.update("Country","USA");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK){
            imageUri = data.getData();
            uploadImage();
        }
    }

    private void OpenImage() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver =getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null){
           final StorageReference fileref = FirebaseStorage.getInstance().getReference().child("Uploads").child(System.currentTimeMillis() + "."+getFileExtension(imageUri));

            fileref.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                 fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                       String url = uri.toString();
                            Log.d("DownloadUrl",url);
                            pd.dismiss();
                            Toast.makeText(MainActivity.this, "Image Upload Successfull", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
}