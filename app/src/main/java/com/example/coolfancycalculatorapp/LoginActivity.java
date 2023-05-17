package com.example.coolfancycalculatorapp;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    Vibrator vibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText login = findViewById(R.id.edit_login);
        EditText password = findViewById(R.id.password);
        Button buttonLogin = findViewById(R.id.authorization);

        Button buttonExit = findViewById(R.id.button_exit);

        Animation anim_rumble = AnimationUtils.loadAnimation(this, R.anim.rumble);
        Animation anim_appear = AnimationUtils.loadAnimation(this, R.anim.appear_from_center2);
        Animation anim_appear_reverse = AnimationUtils.loadAnimation(this, R.anim.appear_from_center2_reverse);
        buttonLogin.startAnimation(anim_appear);
        password.startAnimation(anim_appear);
        login.startAnimation(anim_appear);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                anim_appear_reverse.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });

                buttonLogin.startAnimation(anim_appear_reverse);
                password.startAnimation(anim_appear_reverse);
                login.startAnimation(anim_appear_reverse);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!validateLogin(login.getText().toString())) {
                    login.setError("Неправильный формат почты");
                    buttonExit.startAnimation(anim_rumble);
                    buttonLogin.startAnimation(anim_rumble);
                    login.startAnimation(anim_rumble);
                    password.startAnimation(anim_rumble);
                    vibe.vibrate(100);
                    return;
                }
                if (!validatePassword(password.getText().toString())) {
                    password.setError("Неправильный формат пароля \n " +
                            "- Длина от 8 до 20 символов\n" +
                            "- Содержит как минимум одну строчную букву\n" +
                            "- Содержит как минимум одну заглавную букву\n" +
                            "- Содержит как минимум одну цифру\n");
                    buttonExit.startAnimation(anim_rumble);
                    buttonLogin.startAnimation(anim_rumble);
                    login.startAnimation(anim_rumble);
                    password.startAnimation(anim_rumble);
                    vibe.vibrate(100);
                    return;
                }


                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("showAd", false);
                startActivity(intent);
            }
        });
    }

    private boolean validateLogin(String login) {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        return !TextUtils.isEmpty(login) && emailPattern.matcher(login).matches();
    }

    private boolean validatePassword(String password) {
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)\\S{8,20}$";
        return !TextUtils.isEmpty(password) && password.matches(passwordRegex);
    }
}