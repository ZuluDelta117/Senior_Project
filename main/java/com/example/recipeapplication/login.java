package com.example.recipeapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class login extends Activity {

    Button h_btn;
    Button up_btn;
    Button in_btn;
    TextView tv;
    EditText username_entry;
    EditText password_entry;
    EditText email_entry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        if(!Python.isStarted())
            Python.start(new AndroidPlatform(this));

        Python py = Python.getInstance();
        PyObject pyobj = py.getModule("query_aws");


        h_btn = (Button) findViewById(R.id.home_button);

        h_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, home.class);
                startActivity(intent);
            }
        });

        up_btn = (Button) findViewById(R.id.sign_up_button);
        username_entry = (EditText) findViewById(R.id.username);
        password_entry = (EditText) findViewById(R.id.password);
        email_entry = (EditText) findViewById(R.id.email);
        tv = (TextView) findViewById(R.id.result_text);

        up_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("sign_up", username_entry.getText(), password_entry.getText(), email_entry.getText());

                tv.setText(obj.toString());

            }
        });

        in_btn = (Button) findViewById(R.id.sign_in_button);

        in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PyObject obj = pyobj.callAttr("login", username_entry.getText(), password_entry.getText());

                tv.setText(obj.toString());
            }
        });



    }

}