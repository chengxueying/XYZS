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

import xyzs.hy.com.xyzs.common.HttpUtils;


public class MainActivity extends Activity implements BaseSliderView.OnSliderClickListener, OnClickListener {
    public static final int REQUEST = 3;
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
                switchActivity(LiftActivity.class);
                break;
            case R.id.main_cardview_life:
                Intent intent1 = new Intent(MainActivity.this, UserCenterActivity.class);
                startActivityForResult(intent1, REQUEST);
                break;
            case R.id.main_cardview_mark:
                switchActivity(ScoreActivity.class);
                break;
            case R.id.main_cardview_market:
                switchActivity(ProductActivity.class);
                break;
        }
    }

    /**
     * 跳转基类
     */
    private void switchActivity(Class<?> activity) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, activity);
        startActivity(intent);
    }

    //结果处理
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            MainActivity.this.finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //代码家的轮播框架
    public void initSlider() {
        //new一个TextSliderView对象，传入两个参数，一个是图片的URL，一个是显示的文字。
        TextSliderView textSliderView = new TextSliderView(this);
        textSliderView.image(HttpUtils.VILLAGE_PHOTO)
                .description("支援农村教育")
                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView p1) {
                        sendIntent(HttpUtils.VILLAGE);
                    }
                });
        TextSliderView textSliderView1 = new TextSliderView(this);
        textSliderView1.image(HttpUtils.SCHOOL_PHOTO)
                .description("青岛农业大学")
                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView p1) {
                        sendIntent(HttpUtils.SCHOOL);
                    }
                });
        TextSliderView textSliderView2 = new TextSliderView(this);
        textSliderView2.image(HttpUtils.CULTURE_PHOTO)
                .description("第十一届大学生科技文化艺术节")
                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView p1) {
                        sendIntent(HttpUtils.CULTURE);
                    }
                });
        TextSliderView textSliderView3 = new TextSliderView(this);
        textSliderView3.image(HttpUtils.DISCUSS_PHOTO)
                .description("全国植物逆境生物学学术研讨会")
                .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView p1) {
                        sendIntent(HttpUtils.DISCUSS);
                    }
                });
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        //设置轮播延时
        sliderLayout.setDuration(3000);
        //增加到sliderLayout中
        sliderLayout.addSlider(textSliderView1);
        sliderLayout.addSlider(textSliderView);
        sliderLayout.addSlider(textSliderView2);
        sliderLayout.addSlider(textSliderView3);
    }

    private void sendIntent(String name) {
        Intent intent = new Intent(MainActivity.this, QueryActivity.class);
        intent.putExtra("http", name);
        startActivity(intent);
    }

    //轮播点击监听
    @Override
    public void onSliderClick(BaseSliderView p1) {

    }
}
