package com.example.magicalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;
    private DrawerLayout drawerLayout;
    private String currentInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);
        drawerLayout = findViewById(R.id.drawerLayout);
        ImageButton btnMenu = findViewById(R.id.btnMenu);

        // Slider open karne ke liye menu button
        btnMenu.setOnClickListener(v -> drawerLayout.open());

        // Navigation actions inside slider
        findViewById(R.id.navUnitConverter).setOnClickListener(v -> {
            drawerLayout.closeDrawers();
            startActivity(new Intent(MainActivity.this, UnitConverterActivity.class));
        });

        // Number & Operator Button Clicks setup
        setButtonListeners();
    }

    private void setButtonListeners() {
        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnDoubleZero, R.id.btnPlus, R.id.btnMinus,
                R.id.btnMultiply, R.id.btnDivide, R.id.btnPercent, R.id.btnDot
        };

        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            currentInput += b.getText().toString();
            tvDisplay.setText(currentInput);

            // SPECIAL MAGIC FEATURE: Agar user "2+2" ya "2 + 2" likhe
            checkMagicCondition();
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(listener);
        }

        // Clear Button (C)
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            currentInput = "";
            tvDisplay.setText("0");
        });

        // Backspace / Back Button (⌫) inside calculator
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
                tvDisplay.setText(currentInput.isEmpty() ? "0" : currentInput);
                checkMagicCondition();
            }
        });

        // Equal Button (=)
        findViewById(R.id.btnEqual).setOnClickListener(v -> {
            try {
                // Aap yaha standard evaluation ya custom calculation daal sakte hain
                if (currentInput.contains("2+2") || currentInput.contains("2 + 2")) {
                    tvDisplay.setText("5"); // Magic Answer!
                } else {
                    // Simple evaluation logic yaha aayega
                    tvDisplay.setText(currentInput);
                }
            } catch (Exception e) {
                tvDisplay.setText("Error");
            }
        });
    }

    // Ye function bina '=' dabaye 2+2 ka answer dikha dega
    private void checkMagicCondition() {
        String cleanInput = currentInput.replaceAll("\\s+", "");
        if (cleanInput.equals("2+2")) {
            tvDisplay.setText("5 (Magic Ans!)");
        }
    }

    // Phone ka physical back button press karne par slider close ho ya app exit ho
    @Override
    public void onBackPressed() {
        if (drawerLayout.isOpen()) {
            drawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
