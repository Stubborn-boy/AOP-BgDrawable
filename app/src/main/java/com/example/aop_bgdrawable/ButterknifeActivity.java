package com.example.aop_bgdrawable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.aop.bgdrawable.aspect.BgDrawable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ButterknifeActivity extends AppCompatActivity {

    @BgDrawable(id = R.id.textView,
            color = R.color.colorPrimary,
            cornerRadius = 10,
            topleftRadius = 60,
            toprightRadius = 60,
            bottomrightRadius = 10,
            bottomleftRadius = 10,
            strokeWidth = 4,
            strokeColor = R.color.colorAccent
    )
    @BindView(R.id.textView)
    TextView textView;

    @BgDrawable(id = R.id.button,
            color = R.color.colorAccent,
            cornerRadius = 10,
            pressedColor = R.color.colorPrimary
    )
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butterknife);
        ButterKnife.bind(this);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content, new ButterknifeFragment()).commitAllowingStateLoss();
    }
}
