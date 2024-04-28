package edu.uga.cs.rideshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
     TextInputEditText emailTxt, passwordTxt,nameTxt;
     Button buttonReg;
     TextView registerTxt;
     FirebaseAuth mAuth;
     FirebaseDatabase db;
     DatabaseReference ref;
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth= FirebaseAuth.getInstance();
        nameTxt=findViewById(R.id.name);
        emailTxt=findViewById(R.id.emailId);
        passwordTxt=findViewById(R.id.password);
        buttonReg=findViewById(R.id.register);
        registerTxt=findViewById(R.id.textView);

        registerTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent=new Intent(getApplicationContext(),Login.class);
                 startActivity(intent);
                 finish();
            }
        });

        buttonReg.setOnClickListener(new  View.OnClickListener(){

            @Override
            public void onClick(View v) {
                db=FirebaseDatabase.getInstance();
                ref=db.getReference("users");
                String email,password,name;
                name=String.valueOf(nameTxt.getText());
                email=String.valueOf(emailTxt.getText());
                password=String.valueOf(passwordTxt.getText());

                HelperClass helperClass=new HelperClass(name, email, password);
                ref.child(name).setValue(helperClass);


                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"Enter email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"Enter password",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                   // Log.d(TAG, "createUserWithEmail:success");
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(Register.this, "Acoount Created",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(Register.this, task.getException().getLocalizedMessage(),
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });
    }
}