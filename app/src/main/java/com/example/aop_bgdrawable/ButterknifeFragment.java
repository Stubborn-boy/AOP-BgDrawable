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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ButterknifeFragment extends Fragment {

    @BgDrawable(id = R.id.textView,
            shape = GradientDrawable.OVAL,
            startColor = R.color.colorPrimary,
            centerColor = R.color.colorAccent,
            endColor = R.color.colorPrimaryDark
    )
    @BindView(R.id.textView)
    TextView textView;

    @BgDrawable(id = R.id.button,
            color = R.color.colorPrimary,
            cornerRadius = 10,
            topleftRadius = 40,
            bottomrightRadius = 40,
            unabledColor = R.color.colorAccent
    )
    @BindView(R.id.button)
    Button button;

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butterknife, null, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null){
            unbinder.unbind();
        }
    }
}
