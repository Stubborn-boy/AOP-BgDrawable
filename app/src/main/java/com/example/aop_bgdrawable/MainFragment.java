package com.example.aop_bgdrawable;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aop.bgdrawable.aspect.BgDrawable;

public class MainFragment extends Fragment {

    @BgDrawable(id = R.id.textView,
            shape = GradientDrawable.OVAL,
            startColor = R.color.colorPrimary,
            centerColor = R.color.colorAccent,
            endColor = R.color.colorPrimaryDark
    )
    private TextView textView;

    @BgDrawable(id = R.id.button,
            color = R.color.colorPrimary,
            cornerRadius = 10,
            topleftRadius = 40,
            bottomrightRadius = 40,
            unabledColor = R.color.colorAccent
    )
    private Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.textView);
        button = view.findViewById(R.id.button);
        button.setEnabled(false);
    }
}
