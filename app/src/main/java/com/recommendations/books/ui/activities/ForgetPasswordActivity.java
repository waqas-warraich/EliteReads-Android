package com.recommendations.books.ui.activities;


import static com.recommendations.books.ui.activities.SignUpWithEmailActivity.hideKeyboardFrom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.recommendations.books.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPasswordActivity extends AppCompatActivity {

    ImageButton ibBack;
    TextView tvSignIn, tvSubmitBtn, toolBarTitle;
    LinearLayout llEmailContainer;


    FirebaseAuth mAuth;
    LinearLayoutCompat llProgressBarContainer;

    String email;
    View varView= null;
    Context context;
    TextInputLayout tilEmail;
    TextInputEditText etEmail;
    MaterialTextView mtvErrorLabelEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
       // overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);

        mAuth= FirebaseAuth.getInstance();

        varView = findViewById(android.R.id.content).getRootView();
        context = ForgetPasswordActivity.this;
        llProgressBarContainer = findViewById(R.id.progressBar_reset_password);
        ibBack = findViewById(R.id.ib_cancel_forgetPasswordActivity);
        etEmail = findViewById(R.id.et_email_ForgetPasswordActivity);
        mtvErrorLabelEmail = findViewById(R.id.err_text_email_ForgetPasswordActivity);
        tilEmail = findViewById(R.id.til_email_LoginActivity);
        tvSubmitBtn = findViewById(R.id.submit_btn_ForgetPasswordActivity);
        tvSignIn = findViewById(R.id.tv_signIn_btn_ForgetPasswordActivity);
        llEmailContainer = findViewById(R.id.ll_mail_container_forget_activity);


        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        tvSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                hideKeyboardFrom(context, varView);

                String email = etEmail.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()||TextUtils.isEmpty(email)){

                    tilEmail.setErrorEnabled(true);
                    tilEmail.setError("Email is not valid");
                    etEmail.setError(null);
                    mtvErrorLabelEmail.setVisibility(View.VISIBLE);
                    Toast.makeText(ForgetPasswordActivity.this, "Email address is not correct", Toast.LENGTH_SHORT).show();
                    return;

                }else
                {
                    tilEmail.setErrorEnabled(false);
                    tilEmail.setError(null);
                    etEmail.setError(null);
                    mtvErrorLabelEmail.setVisibility(View.INVISIBLE);

                }
                resetPassword(email);

               /* FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser!=null)*/

            }
        });


        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });



    }


    private void resetPassword(String email){

        llProgressBarContainer.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            llProgressBarContainer.setVisibility(View.GONE);
                            Toast.makeText(ForgetPasswordActivity.this, "We have sent you email to reset your password!", Toast.LENGTH_SHORT).show();
                            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    onBackPressed();
                                }


                            }, 1000);

                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "No account exist on this email", Toast.LENGTH_SHORT).show();
                          //  Toasty.error(getApplication(), "Failed to send reset email!", Toast.LENGTH_SHORT, true).show();
                        }


                    }
                });
    }


}