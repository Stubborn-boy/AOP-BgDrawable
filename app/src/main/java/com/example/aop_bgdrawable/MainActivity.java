package com.example.aop_bgdrawable;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aop.bgdrawable.aspect.BgDrawable;

public class MainActivity extends AppCompatActivity {

    @BgDrawable(id = R.id.textView,
            shape = GradientDrawable.OVAL,
            color = R.color.colorPrimary,
            strokeWidth = 3,
            strokeColor = R.color.colorAccent
    )
    private TextView textView;

    @BgDrawable(id = R.id.button,
            color = R.color.colorPrimary,
            cornerRadius = 20,
            strokeWidth = 3,
            strokeColor = R.color.colorAccent,
            dashWidth = 15,
            dashGap = 10,
            pressedColor = R.color.colorAccent,
            pressedStrokeColor = R.color.colorPrimary
    )
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, new MainFragment()).commitAllowingStateLoss();
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ButterknifeActivity.class));
            }
        });
    }
}
