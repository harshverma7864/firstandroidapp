package com.example.cocktailhouse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PhoneAuth extends AppCompatActivity {

    EditText verfication_text,phonenumber;
    Button sendVerficationCode,Verify;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    EditText name_user;
    Button skip;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callback;
    String verificationId;
    PhoneAuthProvider.ForceResendingToken token;
    private static String URL_REGIST="https://winbattleuc.in/androidapi/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        Intialize();
        skiptomain();
        auth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        sendVerficationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PhoneNumber=phonenumber.getText().toString();
                if(TextUtils.isEmpty(PhoneNumber))
                {
                    phonenumber.setError("please enter phone number");

                }else if(TextUtils.isEmpty(name_user.getText().toString())){
                    phonenumber.setError("please enter Your name");
                }
                else
                {
                    progressDialog.setTitle("Phone Verification");
                    progressDialog.setMessage("please wait , while we are authentication your phone ...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    PhoneAuthOptions options= PhoneAuthOptions.newBuilder(auth)
                            .setPhoneNumber("+91"+ PhoneNumber)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(PhoneAuth.this)
                            .setCallbacks(callback)
                            .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                    auth.setLanguageCode("en");
                }
            }
        });
        callback=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                progressDialog.cancel();
                verfication_text.setText(phoneAuthCredential.getSmsCode());
                signInWithPhoneCredentials(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressDialog.cancel();
                Toast.makeText(getApplicationContext(), "Invalid Phone Number , please enter correct phone number", Toast.LENGTH_SHORT).show();
                Verify.setVisibility(View.INVISIBLE);
                verfication_text.setVisibility(View.INVISIBLE);
                sendVerficationCode.setVisibility(View.VISIBLE);
                phonenumber.setVisibility(View.VISIBLE);
                //exceptional handling
                if(e instanceof FirebaseAuthInvalidCredentialsException)
                { Toast.makeText(getApplicationContext(), "Invalid Request : " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();


                }
                if(e instanceof FirebaseTooManyRequestsException)
                {
                    Toast.makeText(getApplicationContext(), " Your sms limit has been expired ", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken Token) {
                progressDialog.cancel();
                verificationId=s;
                token=Token;
                Toast.makeText(getApplicationContext(), "Code Send", Toast.LENGTH_SHORT).show();
                Verify.setVisibility(View.VISIBLE);
                verfication_text.setVisibility(View.VISIBLE);
                sendVerficationCode.setVisibility(View.INVISIBLE);
                phonenumber.setVisibility(View.INVISIBLE);
            }

        };
        Verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phonenumber.setVisibility(View.INVISIBLE);
                sendVerficationCode.setVisibility(View.INVISIBLE);
                String code=verfication_text.getText().toString();
                if(TextUtils.isEmpty(code))
                {
                    verfication_text.setError("please enter verification code");
                }
                else
                {
                    progressDialog.setTitle("Verification Code");
                    progressDialog.setMessage("Please wait , while we are verifying you code ....");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,code);
                    signInWithPhoneCredentials(credential);
                }
            }
        });

    }

    public void signInWithPhoneCredentials(PhoneAuthCredential phoneAuthCredential)
    {
        auth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    register();
                    Toast.makeText(getApplicationContext(), "you are successfully logged In", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),auth.getUid(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),name_user.getText().toString(),Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(),phonenumber.getText().toString(),Toast.LENGTH_SHORT).show();

                    SendUserToMainActivity();
                }
                else
                {
                    String message =task.getException().getMessage();
                    Toast.makeText(getApplicationContext(), "Error : " +  message, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
                progressDialog.cancel();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser() !=null){
            SendUserToMainActivity();
        }
    }

    private void Intialize() {

        verfication_text=findViewById(R.id.phone_number_verify_code);
        sendVerficationCode=findViewById(R.id.send_verify_code_btn);
        Verify=findViewById(R.id.verify_btn);
        phonenumber=findViewById(R.id.phone_number_edit);
        name_user=findViewById(R.id.name_user);
        skip = findViewById(R.id.Skip);
        Verify.setVisibility(View.INVISIBLE);
        verfication_text.setVisibility(View.INVISIBLE);
        sendVerficationCode.setVisibility(View.VISIBLE);
        phonenumber.setVisibility(View.VISIBLE);
    }


    private void register(){
        String nameUser = name_user.getText().toString();
        String userUid = auth.getUid();
        String userPhone = phonenumber.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if(success.equals("1")){
                                Toast.makeText(PhoneAuth.this, "Registration SuccessFul", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PhoneAuth.this, "Registration Failed"+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", nameUser);
                params.put("uid", userUid);
                params.put("phone", userPhone);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void skiptomain(){
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(PhoneAuth.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void SendUserToMainActivity() {
        Intent intent =new Intent(PhoneAuth.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}