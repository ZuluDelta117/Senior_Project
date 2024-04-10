package com.example.recipeapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import androidx.annotation.Nullable;

public class recipe extends Activity {

    private ListView recipe_listView;
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_page);

        btn = (Button) findViewById(R.id.home_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(recipe.this, home.class);
                startActivity(intent);
            }
        });

        recipe_listView = findViewById(R.id.recipe_list);

        new GetDataTask().execute();
    }

    private class GetDataTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                // Execute Python Script
                Process process = Runtime.getRuntime().exec("Users/zackd/AndroidStudioProjects/RecipeApplication/app/src/main/python/query_aws.py recipe_list");

                // Read Python Script output
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                // Wait for the process to complete
                process.waitFor();

                // Return the output of the Python script
                return stringBuilder.toString();
            }
            catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonData) {
            super.onPostExecute(jsonData);

            if (jsonData != null) {
                try {
                    // Parse JSON data
                    JSONArray jsonArray = new JSONArray(jsonData);

                    // Populate ListView with data
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(recipe.this, android.R.layout.simple_list_item_1);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        adapter.add(jsonArray.getJSONObject(i).toString());
                    }
                    recipe_listView.setAdapter(adapter);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}