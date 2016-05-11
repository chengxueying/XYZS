package xyzs.hy.com.xyzs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 详情页没有图片
 */
public class DetailNoActivity extends Activity implements View.OnClickListener {
    private TextView tv_nameDetail;
    private TextView tv_timeDetail;
    private TextView tv_titleDetail;
    private TextView tv_describeDetail;
    private TextView tv_phoneDetail;
    private ImageButton ibtn_phoneDetail;
    private LinearLayout lg_detail_back;
    private SimpleDraweeView headDetail;
    private String headURL;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_no_photo);
        initView();
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        headURL = getIntent().getStringExtra("headURL");
        String name = getIntent().getStringExtra("name");
        String time = getIntent().getStringExtra("time");
        String title = getIntent().getStringExtra("title");
        String describe = getIntent().getStringExtra("describe");
        phone = getIntent().getStringExtra("phone");
        tv_nameDetail.setText(name);
        tv_timeDetail.setText(time);
        tv_titleDetail.setText(title);
        tv_describeDetail.setText(describe);
        Uri headUri = Uri.parse(headURL);
        headDetail.setImageURI(headUri);
        tv_phoneDetail.setText(phone);
        ibtn_phoneDetail.setOnClickListener(this);
        lg_detail_back.setOnClickListener(this);
    }

    /**
     * 初始化视图
     **/
    private void initView() {
        headDetail = (SimpleDraweeView) findViewById(R.id.iv_headNoDetail);
        tv_nameDetail = (TextView) findViewById(R.id.tv_nameNoDetail);
        tv_timeDetail = (TextView) findViewById(R.id.tv_timeNoDetail);
        tv_titleDetail = (TextView) findViewById(R.id.tv_titleNoDetail);
        tv_describeDetail = (TextView) findViewById(R.id.tv_describeNoDetail);
        tv_phoneDetail = (TextView) findViewById(R.id.tv_phoneDNoDetail);
        ibtn_phoneDetail = (ImageButton) findViewById(R.id.ibtn_phoneNoDetail);
        lg_detail_back = (LinearLayout) findViewById(R.id.lg_no_detail_back);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_phoneNoDetail:
                String newPhone = phone.trim();
                //调用系统的拨号服务实现电话拨打功能
                //封装一个拨打电话的intent，并且将电话号码包装成一个Uri对象传入
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + newPhone));
                DetailNoActivity.this.startActivity(intent);
                break;
            case R.id.lg_no_detail_back:
                finish();
                break;
        }
    }
}
