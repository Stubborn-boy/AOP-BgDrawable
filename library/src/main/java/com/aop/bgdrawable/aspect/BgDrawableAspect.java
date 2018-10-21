package com.aop.bgdrawable.aspect;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import com.aop.bgdrawable.BgDrawableUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import java.lang.reflect.Field;

@Aspect
public class BgDrawableAspect {

    @Around("call(* android.*.*.findViewById(..)) && !within(android.support..*) && !within(butterknife..*)")
    public Object executionfindViewById(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnValue = joinPoint.proceed();
        Field[] fields = joinPoint.getThis().getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            BgDrawable anno = field.getAnnotation(BgDrawable.class);
            if(anno!=null && returnValue!=null) {
                View view = (View) returnValue;
                if(view.getId()==anno.id()) {
                    Drawable drawable = BgDrawableUtil.getDrawable(view.getContext(), anno);
                    view.setBackground(drawable);
                    break;
                }
            }
        }
        return returnValue;
    }

    @After("initialization(* ..*_ViewBinding.new(..)) && target(butterknife.Unbinder)")
    public void executionbButterknife(JoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getArgs()[0];
        Field[] fields = target.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            Object fieldValue = field.get(target);
            BgDrawable anno = field.getAnnotation(BgDrawable.class);
            if (anno != null && fieldValue instanceof View) {
                View view = (View) fieldValue;
                if (view.getId() == anno.id()) {
                    Drawable drawable = BgDrawableUtil.getDrawable(view.getContext(), anno);
                    view.setBackground(drawable);
                }
            }
        }
    }

}
