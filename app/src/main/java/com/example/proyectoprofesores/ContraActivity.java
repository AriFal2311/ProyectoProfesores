package com.example.proyectoprofesores;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class ContraActivity extends AppCompatActivity{
    Button button;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasena);

        button = findViewById(R.id.button_contra);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContraActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        String olvidaste=getString(R.string.recuperar_contra);
        SpannableString ss= new SpannableString(olvidaste);
        ss.setSpan(new ForegroundColorSpan(Color.YELLOW), 0, olvidaste.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE );
        TextView textView=findViewById(R.id.text_contra);
        textView.setText(ss);
    }
}
