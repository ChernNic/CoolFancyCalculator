package com.example.coolfancycalculatorapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import org.mariuszgromada.math.mxparser.*;
import com.google.android.material.button.MaterialButton;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String solution, formattedResult;

    TextView result_TextView, solution_TextView;
    MaterialButton btnAllClear, btnClear, btnBracketOpen, btnBracketClose, closeBtn,
        btnDivide, btnMultiply, btnPlus, btnMinus, btnResult, btnDot,
        btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9,
        btnRoot, btnCos, btnSin, btnTg, btnCtg;

    RelativeLayout ADLayout;
    LinearLayout btnLayout;
    VideoView ADVideo;

    boolean showAd;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result_TextView = findViewById(R.id.result_TextView);
        solution_TextView = findViewById(R.id.solution_TextView);

        SetClickListener(btnAllClear, R.id.button_all_clear);
        SetClickListener(btnClear, R.id.button_clear);
        SetClickListener(btnBracketOpen, R.id.button_open_bracket);
        SetClickListener(btnBracketClose, R.id.button_close_bracket);

        SetClickListener(btnDivide, R.id.button_divide);
        SetClickListener(btnMultiply, R.id.button_multiply);
        SetClickListener(btnPlus, R.id.button_plus);
        SetClickListener(btnMinus, R.id.button_minus);
        SetClickListener(btnResult, R.id.button_result);
        SetClickListener(btnDot, R.id.button_dot);

        SetClickListener(btn0, R.id.button_0);
        SetClickListener(btn1, R.id.button_1);
        SetClickListener(btn2, R.id.button_2);
        SetClickListener(btn3, R.id.button_3);
        SetClickListener(btn4, R.id.button_4);
        SetClickListener(btn5, R.id.button_5);
        SetClickListener(btn6, R.id.button_6);
        SetClickListener(btn7, R.id.button_7);
        SetClickListener(btn8, R.id.button_8);
        SetClickListener(btn9, R.id.button_9);

        SetClickListener(btnRoot, R.id.button_root);
        SetClickListener(btnSin, R.id.button_sin);
        SetClickListener(btnCos, R.id.button_cos);
        SetClickListener(btnTg, R.id.button_tg);
        SetClickListener(btnCtg, R.id.button_ctg);

        Button button_settings = findViewById(R.id.button_settings);

        button_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        showAd = getIntent().getBooleanExtra("showAd", true);
        btnLayout = findViewById(R.id.buttons_layout);
        ADLayout = findViewById(R.id.AdLayout);
        ADVideo = findViewById(R.id.videoView);
        closeBtn = findViewById(R.id.CloseButton);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ADLayout.setVisibility(View.GONE);
                btnLayout.setVisibility(View.VISIBLE);
                ADVideo.stopPlayback();
            }
        });
    }


    void SetClickListener(MaterialButton btn, int id){
        btn = findViewById(id);
        if (btn != null){
            btn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        MaterialButton btn = (MaterialButton) view;
        solution = solution_TextView.getText().toString();

        Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(100);

        if(btn.getText().toString().equals("AC")){
            solution_TextView.setText("0");
            result_TextView.setText("0");
            return;
        }
        else if (btn.getText().toString().equals("C")) {
            if(!solution.equals("0")){
                solution = solution.substring(0, solution.length() - 1);
            }
            if(solution.equals("")){
                solution = "0";
            }
        }
        else if (btn.getText().toString().equals("=")) {

            if(showAd){
                ADLayout.setVisibility(View.VISIBLE);
                btnLayout.setVisibility(View.GONE);
                Uri videoUri= Uri.parse( "android.resource://" + getPackageName() + "/" + R.raw.advideo);
                ADVideo.setVideoURI(videoUri);
                ADVideo.start();
            }


            Expression e = new Expression(solution);
            double result = e.calculate();
            solution_TextView.setText(result_TextView.getText().toString());
            formattedResult = (result == (int) result) ? String.format("%.0f", result) : String.format("%.2f", result);
            result_TextView.setText(formattedResult);
        }
        else {
            if(solution.equals("0")){
                solution = "";
            }
            solution += btn.getText().toString();
        }

        solution_TextView.setText(solution);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("solution",solution_TextView.getText());
        outState.putCharSequence("formattedResult",result_TextView.getText());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        solution = (String) savedInstanceState.getCharSequence("solution");
        formattedResult = (String) savedInstanceState.getCharSequence("formattedResult");
        result_TextView.setText(savedInstanceState.getCharSequence("formattedResult"));
        solution_TextView.setText(savedInstanceState.getCharSequence("solution"));
    }
}