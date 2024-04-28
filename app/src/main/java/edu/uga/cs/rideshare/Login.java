package edu.uga.cs.rideshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {
    TextInputEditText emailTxt, passwordTxt;
    Button buttonLog;
    TextView registerTxt;
    //FirebaseAuth mAuth;
    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //mAuth = FirebaseAuth.getInstance();
        emailTxt = findViewById(R.id.emailId);
        passwordTxt = findViewById(R.id.password);
        buttonLog = findViewById(R.id.login);
        registerTxt = findViewById(R.id.textView1);

        registerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

        buttonLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatePassword() | !validateEmail()) {

                } else {
                    checkUser();
                }
            }
        });
    }

        public Boolean validateEmail(){
            String val= emailTxt.getText().toString();
            if(val.isEmpty()){
                emailTxt.setError("username cannot be empty");
                return false;
            }
            else{
                emailTxt.setError(null);
                return true;
            }
        }
        public Boolean validatePassword(){
            String val= passwordTxt.getText().toString();
            if(val.isEmpty()){
                passwordTxt.setError("password cannot be empty");
                return false;
            }
            else{
                passwordTxt.setError(null);
                return true;
            }
        }
        public void checkUser(){
            String email=emailTxt.getText().toString().trim();
            String pass=passwordTxt.getText().toString().trim();

            DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users");
            Query checkUserDatabase=ref.orderByChild("email").equalTo(email);

            checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        emailTxt.setError(null);
                        String password = snapshot.child(email).child("password").getValue(String.class);

                        if (password.equals(pass)) {
                            emailTxt.setError(null);
                            String nameFromDB = snapshot.child(email).child("name").getValue(String.class);
                            String emailFromDB = snapshot.child(email).child("email").getValue(String.class);
                            //String usernameFromDB = snapshot.child(email).child("email").getValue(String.class);
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("name", nameFromDB);
                            intent.putExtra("email", emailFromDB);
                            intent.putExtra("password", password);
                            startActivity(intent);
                        } else {
                            passwordTxt.setError("invalid credentials");
                            passwordTxt.requestFocus();
                        }
                    }
                        else {
                            emailTxt.setError("User does not exist");
                            emailTxt.requestFocus();
                        }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        }
        /*buttonLog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email,password;
                    email=String.valueOf(emailTxt.getText());
                    password=String.valueOf(passwordTxt.getText());

                    if(TextUtils.isEmpty(email)){
                        Toast.makeText(Login.this,"Enter email",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(TextUtils.isEmpty(password)){
                        Toast.makeText(Login.this,"Enter password",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(Login.this, "Login successful.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        //Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(Login.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        //updateUI(null);
                                    }
                                }
                            });


            }
        });
     }

}*/