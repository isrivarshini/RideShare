package edu.uga.cs.rideshare;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    //FirebaseAuth auth;
    TextView userDetails;
    Button logout;
    FirebaseUser user;
    //DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //reference = FirebaseDatabase.getInstance().getReference("users");
        user = FirebaseAuth.getInstance().getCurrentUser();
        logout=findViewById(R.id.logout);
        userDetails=findViewById(R.id.user_details);

        if(user!=null){
            String name= getIntent().getStringExtra("name");
            Log.d(TAG, "name: "+name);
            userDetails.setText(name);

        }
        else{
            Intent intent=new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            finish();
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}