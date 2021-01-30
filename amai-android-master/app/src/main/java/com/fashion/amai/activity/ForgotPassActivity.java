package com.fashion.amai.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fashion.amai.R;

public class ForgotPassActivity extends BaseActivity implements View.OnClickListener {

    Button btnForgot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        intiView();


        setListener();


    }

    private void setListener() {

        btnForgot.setOnClickListener(this);

    }

    private void intiView() {

        btnForgot = findViewById(R.id.btn_forgot);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_forgot:

                Toast.makeText(ForgotPassActivity.this, "Recovery Link Has Been Sent To Your Email", Toast.LENGTH_SHORT).show();
                break;

    }
}
}
