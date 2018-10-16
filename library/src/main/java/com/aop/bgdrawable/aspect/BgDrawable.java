package com.aop.bgdrawable.aspect;

import android.graphics.drawable.GradientDrawable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BgDrawable {
    int id() default 0;
    int shape() default GradientDrawable.RECTANGLE;
    int color() default 0;
    int alpha() default 255;

    float cornerRadius() default 0;
    float topleftRadius() default 0;
    float toprightRadius() default 0;
    float bottomleftRadius() default 0;
    float bottomrightRadius() default 0;

    float width() default 0;
    float height() default 0;

    float strokeWidth() default 0;
    int strokeColor() default 0;
    float dashWidth() default 0;
    float dashGap() default 0;

    GradientDrawable.Orientation orientation() default GradientDrawable.Orientation.TOP_BOTTOM;
    int startColor() default 0;
    int centerColor() default 0;
    int endColor() default 0;
    int gradientType() default GradientDrawable.LINEAR_GRADIENT;
    float gradientCenterX() default 0;
    float gradientCenterY() default 0;
    float gradientRadius() default 0;

    int pressedColor() default 0;
    int selectedColor() default 0;
    int checkedColor() default 0;
    int focusedColor() default 0;
    int unabledColor() default 0;

    int pressedStrokeColor() default 0;
    int selecteStrokedColor() default 0;
    int checkedStrokeColor() default 0;
    int focusedStrokeColor() default 0;
    int unabledStrokeColor() default 0;
}
