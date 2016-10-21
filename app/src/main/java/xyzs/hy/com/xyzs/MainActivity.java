package xyzs.hy.com.xyzs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.HashMap;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyzs.hy.com.xyzs.common.HttpUtils;
import xyzs.hy.com.xyzs.ui.activity.BaseActivity;


public class MainActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener {
    @BindView(R.id.sliderLayout)
    SliderLayout sliderLayout;
    @BindView(R.id.main_cardview_market)
    CardView mainCardviewMarket;
    @BindView(R.id.main_cardview_lost)
    CardView mainCardviewLost;
    @BindView(R.id.main_cardview_found)
    CardView mainCardviewFound;
    @BindView(R.id.main_cardview_express)
    CardView mainCardviewExpress;
    @BindView(R.id.main_cardview_life)
    CardView mainCardviewLife;
    @BindView(R.id.main_cardview_mark)
    CardView mainCardviewMark;
    private Intent intent = new Intent();
    private static String SHUFFING_FIRST = "支援农村教育";
    private static String SHUFFING_SECOND = "青岛农业大学";
    private static String SHUFFING_THIRD = "第十一届大学生科技文化艺术节";
    private static String SHUFFING_FOURTH = "全国植物逆境生物学学术研讨会";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSlider();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    /**
     * 跳转基类
     */
    private void openActivity(Class<?> activity) {
        intent.setClass(MainActivity.this, activity);
        startActivity(intent);
    }

    //代码家的轮播框架
    public void initSlider() {
        LinkedHashMap<String, String> url_map = new LinkedHashMap<>();
        url_map.put(SHUFFING_FIRST, HttpUtils.VILLAGE_PHOTO);
        url_map.put(SHUFFING_SECOND, HttpUtils.SCHOOL_PHOTO);
        url_map.put(SHUFFING_THIRD, HttpUtils.CULTURE_PHOTO);
        url_map.put(SHUFFING_FOURTH, HttpUtils.DISCUSS_PHOTO);
        //new一个TextSliderView对象，传入两个参数，一个是图片的URL，一个是显示的文字。
        for (String name : url_map.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView.description(name)
                    .image(url_map.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this)
                    .bundle(new Bundle())
                    .getBundle()
                    .putString("extra", name);
            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        //设置轮播延时
        sliderLayout.setDuration(3000);
    }

    private void sendIntent(String name) {
        intent.setClass(MainActivity.this, QueryActivity.class);
        intent.putExtra("http", name);
        startActivity(intent);
    }

    @OnClick({R.id.main_cardview_market, R.id.main_cardview_lost, R.id.main_cardview_found, R.id.main_cardview_express, R.id.main_cardview_life, R.id.main_cardview_mark})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_cardview_market:
                openActivity(ProductActivity.class);
                break;
            case R.id.main_cardview_lost:
                openActivity(LostActivity.class);
                break;
            case R.id.main_cardview_found:
                openActivity(FoundActivity.class);
                break;
            case R.id.main_cardview_express:
                openActivity(LiftActivity.class);
                break;
            case R.id.main_cardview_life:
                openActivity(UserCenterActivity.class);
                break;
            case R.id.main_cardview_mark:
                openActivity(ScoreActivity.class);
                break;
        }
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        String name = slider.getBundle().getString("extra");
        if (TextUtils.equals(SHUFFING_FIRST, name)) {
            sendIntent(HttpUtils.VILLAGE);
        } else if (TextUtils.equals(SHUFFING_SECOND, name)) {
            sendIntent(HttpUtils.SCHOOL);
        } else if (TextUtils.equals(SHUFFING_THIRD, name)) {
            sendIntent(HttpUtils.CULTURE);
        } else if (TextUtils.equals(SHUFFING_FOURTH, name)) {
            sendIntent(HttpUtils.DISCUSS);
        }

    }

}
