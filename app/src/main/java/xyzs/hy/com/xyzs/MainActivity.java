package xyzs.hy.com.xyzs;

import android.app.*;
import android.os.*;
import android.support.v7.widget.CardView;
import android.view.View.*;
import android.view.*;
import android.content.*;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.facebook.drawee.backends.pipeline.Fresco;


public class MainActivity extends Activity implements BaseSliderView.OnSliderClickListener, OnClickListener {
    private SliderLayout sliderLayout;
    private CardView lost;
    private CardView found;
    private CardView markQuery;
    private CardView market;
    private CardView express;
    private CardView life;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        sliderLayout = (SliderLayout) findViewById(R.id.sliderLayout);
        initLayout();
        initSlider();
    }

    //初始化控件
    private void initLayout() {
        lost = (CardView) findViewById(R.id.main_cardview_lost);
        found = (CardView) findViewById(R.id.main_cardview_found);
        market = (CardView) findViewById(R.id.main_cardview_market);
        express = (CardView) findViewById(R.id.main_cardview_express);
        life = (CardView) findViewById(R.id.main_cardview_life);
        markQuery = (CardView) findViewById(R.id.main_cardview_mark);

        lost.setOnClickListener(this);
        found.setOnClickListener(this);
        market.setOnClickListener(this);
        express.setOnClickListener(this);
        life.setOnClickListener(this);
        markQuery.setOnClickListener(this);
    }

    //点击监听
    @Override
    public void onClick(View p1) {
        switch (p1.getId()) {
            case R.id.main_cardview_lost:
                switchActivity(LostActivity.class);
                break;
            case R.id.main_cardview_found:
                switchActivity(FoundActivity.class);
                break;
            case R.id.main_cardview_express:
                break;
            case R.id.main_cardview_life:
                break;
            case R.id.main_cardview_mark:
                break;
            case R.id.main_cardview_market:
                break;
        }
    }

    private void switchActivity(Class<?> activity) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, activity);
        startActivity(intent);
    }

    //代码家的轮播框架
    public void initSlider() {
        //new一个TextSliderView对象，传入两个参数，一个是图片的URL，一个是显示的文字。
        TextSliderView textSliderView = new TextSliderView(this);
        textSliderView.image("http://a3.att.hudong.com/87/43/300533991095135023430192962.jpg")
                .description("手机");
        TextSliderView textSliderView1 = new TextSliderView(this);
        textSliderView.image("http://pic1.nipic.com/2008-12-29/2008122912152258_2.jpg")
                .description("手机");

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        //设置轮播延时
        sliderLayout.setDuration(3000);
        //增加到sliderLayout中，搞定！
        sliderLayout.addSlider(textSliderView);
        sliderLayout.addSlider(textSliderView1);
    }

    //轮播点击监听
    @Override
    public void onSliderClick(BaseSliderView p1) {

    }
}
