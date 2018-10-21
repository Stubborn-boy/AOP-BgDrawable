# AOP-BgDrawable
## 告别.xml文件，用AOP方式实现shape，selector

* 效果图
<img src="https://github.com/Stubborn-boy/AOP-BgDrawable/blob/master/BgDrawable.gif" />

## 使用方法
在主Module的build.grade文件中添加
```
apply plugin: 'android-aspectjx'

dependencies {
  ...
  implementation 'com.stubborn:bgdrawable:1.0.0'
}
```
在项目根目录的build.grade文件中添加
```
dependencies {
  ...
  classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.2'
}
```
在Activity或Fragment中，在控件上添加注解@BgDrawable，示例如下：
```
@BgDrawable(id = R.id.button,
        color = R.color.colorPrimary,
        cornerRadius = 20,
        strokeWidth = 3,
        strokeColor = R.color.colorAccent,
        pressedColor = R.color.colorAccent
)
private Button button;
```
注意这里的id就是控件的id，要确保和正确，否则会没有效果。strokeWidth等属性的单位都是dp，color相关的属性必须是R.color.xxx形式。
支持的属性有以下这些：
```
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
```
只要在@BgDrawable里添加这些属性就可以实现shape和selector效果，是不是很简单呢。
## 实现方式

不了解AOP的可以参考这两篇文章
<a href="https://www.jianshu.com/p/f90e04bcb326">AOP 之 AspectJ 全面剖析 in Android</a>
<a href="https://blog.csdn.net/fei20121106/article/details/70269765">Android之面向切面编程：AOP 与 Aspect简介</a>

以Activity为例进行说明，代码如下：
```
public class MainActivity extends AppCompatActivity {

    @BgDrawable(id = R.id.button,
            color = R.color.colorPrimary,
            cornerRadius = 20,
            strokeWidth = 3,
            strokeColor = R.color.colorAccent,
            pressedColor = R.color.colorAccent,
    )
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
    }
}
```
一般在Activity中，要获取一个控件我们需要调用Activity的findViewById方法，所以我们可以用AspectJ来对findViewById方法进行代码注入。
代码如下：
```
@Aspect
public class BgDrawableAspect {
    @Around("call(* android.app.Activity.findViewById(..))")
    public Object executionfindViewById(ProceedingJoinPoint joinPoint) throws Throwable {
        Object returnValue = joinPoint.proceed();   //findViewById的返回值
        Field[] fields = joinPoint.getThis().getClass().getDeclaredFields();    //MainActivity的成员变量
        for(Field field : fields){
            field.setAccessible(true);
            BgDrawable anno = field.getAnnotation(BgDrawable.class);    //获取@BgDrawable注解
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
}
```
在上面的代码中，通过joinPoint.proceed()获取findViewById的返回值，也即是我们的目标控件，接下来我们需要获取MainActivity的成员变量，
从中找出带有@BgDrawable注解成员变量，如果注解中的id与目标控件的id相同，那么就从@BgDrawable注解中获取设置的属性，来创建一个Drawable
对象，最后把Drawable设置为目标控件的背景。
以上就是Activity中实现的过程，在Fragment中实现过程大致相同，只是把要注入的方法换成view的findViewById方法即android.view.View.findViewById(..)。
可能有人会问，我没有使用findViewById方法，使用的是Butterknife怎么办，还能支持吗？请接着往下看。
## 对Butterknife的支持
下面是使用Butterknife的Activity示例：
```
public class ButterknifeActivity extends AppCompatActivity {

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
    }
}
```
上面使用Butterknife的ButterknifeActivity类，在编译时生成一个叫ButterknifeActivity_ViewBinding的.class文件，
```
public class ButterknifeActivity_ViewBinding implements Unbinder {
    private ButterknifeActivity target;

    @UiThread
    public ButterknifeActivity_ViewBinding(ButterknifeActivity target) {
        this(target, target.getWindow().getDecorView());
    }

    @UiThread
    public ButterknifeActivity_ViewBinding(ButterknifeActivity target, View source) {
        this.target = target;
        target.button = (Button)Utils.findRequiredViewAsType(source, 2131165218, "field 'button'", Button.class);
    }

    @CallSuper
    public void unbind() {
        ButterknifeActivity target = this.target;
        if (target == null) {
            throw new IllegalStateException("Bindings already cleared.");
        } else {
            this.target = null;
            target.button = null;
        }
    }
}
```
我们来看下ButterknifeActivity_ViewBinding的构造方法，参数target就是ButterknifeActivity的实例对象，Utils.findRequiredViewAsType方法
可以获取到我们的目标控件，它的内部也是通过view的findViewById方法实现的，最终赋值给了target.button，也就是activity的成员变量button。
有了Activity的实例对象和目标控件，如果我们对这个构造方法进行代码注入，不就可以实现我们想要的功能了吗。
```
@Aspect
public class BgDrawableAspect {
    @After("initialization(* ..*_ViewBinding.new(..))")
    public void executionbButterknife(JoinPoint joinPoint) throws Throwable {
        Object target = joinPoint.getArgs()[0]; //获取第一个参数，这里指Activity的实例对象
        Field[] fields = target.getClass().getDeclaredFields(); //获取Activity的成员变量
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
```
上面的代码中，initialization表示对构造方法进行注入，@After表示在原方法之后注入，所以执行上面代码的时候activity的成员变量target.button已经被赋值了，就是我们的
目标控件，我们再获取这个成员变量上的@BgDrawable注解，根据注解的属性来创建一个Drawable对象，最后把Drawable设置为target.button的背景就可以了。

* <a href="https://www.jianshu.com/p/b4885c1abb2c">原文链接</a>