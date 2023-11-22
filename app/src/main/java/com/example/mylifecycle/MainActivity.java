package com.example.mylifecycle;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private int duration = Toast.LENGTH_SHORT;

    private Button btnExit;
    private EditText txtColorSelected;
    private TextView txt;
    private LinearLayout myScreen;
    private String PREFNAME = "file1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtColorSelected = findViewById(R.id.editText1);
        btnExit = findViewById(R.id.button1);
        txt = findViewById(R.id.textView1);
        myScreen = findViewById(R.id.myScreen1);

        btnExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        txtColorSelected.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String chosenColor = s.toString().toLowerCase(Locale.US);
                txt.setText(chosenColor);
                setBackgroundColor(chosenColor, myScreen);
            }
        });

        context = getApplicationContext();
        Toast.makeText(context, "onCreate", duration).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(context, "onDestroy", duration).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        String chosenColor = txt.getText().toString();
        saveStateData(chosenColor);
        Toast.makeText(context, "onPause", duration).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(context, "onRestart", duration).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(context, "onResume", duration).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateMeUsingSavedStateData();
        Toast.makeText(context, "onStart", duration).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(context, "onStop", duration).show();
    }

    @Override
    public ComponentName getCallingActivity() {
        super.getCallingActivity();
        Toast.makeText(context, "Someone calling", duration).show();
        return null;
    }

    private void setBackgroundColor(String chosenColor, LinearLayout myScreen) {
        if (chosenColor.contains("red"))
            myScreen.setBackgroundColor(0xffff0000);
        if (chosenColor.contains("green"))
            myScreen.setBackgroundColor(0xff00ff00);
        if (chosenColor.contains("blue"))
            myScreen.setBackgroundColor(0xff0000ff);
        if (chosenColor.contains("white"))
            myScreen.setBackgroundColor(0xffffffff);
    }

    private void saveStateData(String chosenColor) {
        SharedPreferences myPrefContainer =
                getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);
        SharedPreferences.Editor myPrefEditor = myPrefContainer.edit();
        String key = "chosenBackgroundColor";
        String value = txt.getText().toString();
        myPrefEditor.putString(key, value);
        myPrefEditor.apply();
    }

    private void updateMeUsingSavedStateData() {
        SharedPreferences myPrefContainer =
                getSharedPreferences(PREFNAME, Activity.MODE_PRIVATE);

        String key = "chosenBackgroundColor";
        String defaultValue = "white";

        if ((myPrefContainer != null) && myPrefContainer.contains(key)) {
            String color = myPrefContainer.getString(key, defaultValue);
            setBackgroundColor(color, myScreen);
        }
    }
}
