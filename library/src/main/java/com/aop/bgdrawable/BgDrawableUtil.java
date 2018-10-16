package com.aop.bgdrawable;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.Log;

import com.aop.bgdrawable.aspect.BgDrawable;

public class BgDrawableUtil {
    public static Drawable getDrawable(Context context, BgDrawable bgAnno){

        StateListDrawable stateListDrawable = null;
        if(bgAnno.pressedColor()!=0) {
            if(stateListDrawable==null) stateListDrawable = new StateListDrawable();
            GradientDrawable pressedDrawable = getGradientDrawable(context, bgAnno);
            int pressedColor = context.getResources().getColor(bgAnno.pressedColor());
            pressedDrawable.setColor(pressedColor);
            if(bgAnno.pressedStrokeColor()!=0) {
                int strokeWidth = dip2px(context, bgAnno.strokeWidth());
                int dashWidth = dip2px(context, bgAnno.dashWidth());
                int dashGap = dip2px(context, bgAnno.dashGap());
                int pressedStrokeColor = context.getResources().getColor(bgAnno.pressedStrokeColor());
                pressedDrawable.setStroke(strokeWidth, pressedStrokeColor, dashWidth, dashGap);
            }
            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressedDrawable);
        }
        if(bgAnno.selectedColor()!=0) {
            if(stateListDrawable==null) stateListDrawable = new StateListDrawable();
            GradientDrawable selectedDrawable = getGradientDrawable(context, bgAnno);
            int selectedColor = context.getResources().getColor(bgAnno.selectedColor());
            selectedDrawable.setColor(selectedColor);
            if(bgAnno.selecteStrokedColor()!=0) {
                int strokeWidth = dip2px(context, bgAnno.strokeWidth());
                int dashWidth = dip2px(context, bgAnno.dashWidth());
                int dashGap = dip2px(context, bgAnno.dashGap());
                int selecteStrokedColor = context.getResources().getColor(bgAnno.selecteStrokedColor());
                selectedDrawable.setStroke(strokeWidth, selecteStrokedColor, dashWidth, dashGap);
            }
            stateListDrawable.addState(new int[]{android.R.attr.state_selected}, selectedDrawable);
        }
        if(bgAnno.checkedColor()!=0) {
            if(stateListDrawable==null) stateListDrawable = new StateListDrawable();
            GradientDrawable checkedDrawable = getGradientDrawable(context, bgAnno);
            int checkedColor = context.getResources().getColor(bgAnno.checkedColor());
            checkedDrawable.setColor(checkedColor);
            if(bgAnno.checkedStrokeColor()!=0) {
                int strokeWidth = dip2px(context, bgAnno.strokeWidth());
                int dashWidth = dip2px(context, bgAnno.dashWidth());
                int dashGap = dip2px(context, bgAnno.dashGap());
                int checkedStrokeColor = context.getResources().getColor(bgAnno.checkedStrokeColor());
                checkedDrawable.setStroke(strokeWidth, checkedStrokeColor, dashWidth, dashGap);
            }
            stateListDrawable.addState(new int[]{android.R.attr.state_checked}, checkedDrawable);
        }
        if(bgAnno.focusedColor()!=0) {
            if(stateListDrawable==null) stateListDrawable = new StateListDrawable();
            GradientDrawable focusedDrawable = getGradientDrawable(context, bgAnno);
            int focusedColor = context.getResources().getColor(bgAnno.focusedColor());
            focusedDrawable.setColor(focusedColor);
            if(bgAnno.focusedStrokeColor()!=0) {
                int strokeWidth = dip2px(context, bgAnno.strokeWidth());
                int dashWidth = dip2px(context, bgAnno.dashWidth());
                int dashGap = dip2px(context, bgAnno.dashGap());
                int focusedStrokeColor = context.getResources().getColor(bgAnno.focusedStrokeColor());
                focusedDrawable.setStroke(strokeWidth, focusedStrokeColor, dashWidth, dashGap);
            }
            stateListDrawable.addState(new int[]{android.R.attr.state_focused}, focusedDrawable);
        }
        if(bgAnno.unabledColor()!=0) {
            if(stateListDrawable==null) stateListDrawable = new StateListDrawable();
            GradientDrawable unabledDrawable = getGradientDrawable(context, bgAnno);
            int unabledColor = context.getResources().getColor(bgAnno.unabledColor());
            unabledDrawable.setColor(unabledColor);
            if(bgAnno.unabledStrokeColor()!=0) {
                int strokeWidth = dip2px(context, bgAnno.strokeWidth());
                int dashWidth = dip2px(context, bgAnno.dashWidth());
                int dashGap = dip2px(context, bgAnno.dashGap());
                int unabledStrokeColor = context.getResources().getColor(bgAnno.unabledStrokeColor());
                unabledDrawable.setStroke(strokeWidth, unabledStrokeColor, dashWidth, dashGap);
            }
            stateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, unabledDrawable);
        }
        if(stateListDrawable!=null){
            stateListDrawable.addState(new int[]{}, getGradientDrawable(context, bgAnno));
            return stateListDrawable;
        }

        return getGradientDrawable(context, bgAnno);
    }

    public static GradientDrawable getGradientDrawable(Context context, BgDrawable bgAnno){
        GradientDrawable drawable = new GradientDrawable();

        drawable.setShape(bgAnno.shape());

        if(bgAnno.color()!=0) {
            int color = context.getResources().getColor(bgAnno.color());
            drawable.setColor(color);
        }

        int alpha = bgAnno.alpha();
        if(alpha>=0 && alpha<=255) {
            drawable.setAlpha(alpha);
        }

        if(bgAnno.cornerRadius()>0) {
            int cornerRadius = dip2px(context, bgAnno.cornerRadius());
            drawable.setCornerRadius(cornerRadius);
        }
        if(bgAnno.topleftRadius() > 0 || bgAnno.toprightRadius() > 0 ||
                bgAnno.bottomrightRadius() > 0 || bgAnno.bottomleftRadius() > 0) {
            float[] radii = new float[8];
            radii[0] = radii[1] = radii[2] = radii[3] = radii[4] = radii[5] = radii[6] = radii[7] = bgAnno.cornerRadius();
            if (bgAnno.topleftRadius() > 0)
                radii[0] = radii[1] = dip2px(context, bgAnno.topleftRadius());
            if (bgAnno.toprightRadius() > 0)
                radii[2] = radii[3] = dip2px(context, bgAnno.toprightRadius());
            if (bgAnno.bottomrightRadius() > 0)
                radii[4] = radii[5] = dip2px(context, bgAnno.bottomrightRadius());
            if (bgAnno.bottomleftRadius() > 0)
                radii[6] = radii[7] = dip2px(context, bgAnno.bottomleftRadius());
            drawable.setCornerRadii(radii);
        }

        if(bgAnno.width()>0 && bgAnno.height()>0) {
            int width = dip2px(context, bgAnno.width());
            int height = dip2px(context, bgAnno.height());
            drawable.setSize(width, height);
        }

        if(bgAnno.strokeWidth()>0) {
            int strokeWidth = dip2px(context, bgAnno.strokeWidth());
            int dashWidth = dip2px(context, bgAnno.dashWidth());
            int dashGap = dip2px(context, bgAnno.dashGap());
            int strokeColor = context.getResources().getColor(bgAnno.strokeColor());
            if(dashWidth>0 && dashGap>0){
                drawable.setStroke(strokeWidth, strokeColor, dashWidth, dashGap);
            }else{
                drawable.setStroke(strokeWidth, strokeColor);
            }
        }

        drawable.setOrientation(bgAnno.orientation());
        if(bgAnno.startColor()!=0 && bgAnno.endColor()!=0) {
            int startColor = context.getResources().getColor(bgAnno.startColor());
            int endColor = context.getResources().getColor(bgAnno.endColor());
            int[] colors;
            if(bgAnno.centerColor()!=0){
                int centerColor = context.getResources().getColor(bgAnno.centerColor());
                colors = new int[]{startColor, centerColor, endColor};
            }else{
                colors = new int[]{startColor, endColor};
            }
            drawable.setColors(colors);
        }

        drawable.setGradientType(bgAnno.gradientType());
        //这两个属性只有在type不为linear情况下起作用。
        if(bgAnno.gradientType()!=GradientDrawable.LINEAR_GRADIENT) {
            int gradientCenterX = dip2px(context, bgAnno.gradientCenterX());
            int gradientCenterY = dip2px(context, bgAnno.gradientCenterY());
            drawable.setGradientCenter(gradientCenterX, gradientCenterY);
        }
        //该属性只有在type="radial"有效
        if(bgAnno.gradientType()==GradientDrawable.RADIAL_GRADIENT) {
            int gradientRadius = dip2px(context, bgAnno.gradientRadius());
            drawable.setGradientRadius(gradientRadius);
        }
        return drawable;
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
