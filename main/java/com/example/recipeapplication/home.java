package com.example.recipeapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class home extends AppCompatActivity {

    Button r_btn;
    Button mp_btn;
    Button g_btn;
    Button l_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        r_btn = (Button) findViewById(R.id.recipe_page_button);

        r_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, recipe.class);
                startActivity(intent);
            }
        });

        mp_btn = (Button) findViewById(R.id.meal_plan_button);

        mp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, meal_plan.class);
                startActivity(intent);
            }
        });

        g_btn = (Button) findViewById(R.id.grocery_button);

        g_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, grocery_list.class);
                startActivity(intent);
            }
        });

        l_btn = (Button) findViewById(R.id.login_page_button);

        l_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, login.class);
                startActivity(intent);
            }
        });

    }

}
