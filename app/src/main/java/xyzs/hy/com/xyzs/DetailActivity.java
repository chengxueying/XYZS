package xyzs.hy.com.xyzs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 详情页有图
 */
public class DetailActivity extends Activity implements View.OnClickListener {
    private TextView tv_nameDetail;
    private TextView tv_timeDetail;
    private TextView tv_titleDetail;
    private TextView tv_describeDetail;
    private TextView tv_phoneDetail;
    private ImageButton ibtn_phoneDetail;
    private SimpleDraweeView lostDetail;
    private SimpleDraweeView headDetail;
    private String phone;
    private String url;
	private String headURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
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
        url = getIntent().getStringExtra("url");
        phone = getIntent().getStringExtra("phone");

        tv_nameDetail.setText(name);
        tv_timeDetail.setText(time);
        tv_titleDetail.setText(title);
        tv_describeDetail.setText(describe);
        tv_phoneDetail.setText(phone);
        Uri uri = Uri.parse(url);
        lostDetail.setImageURI(uri);
		Uri headUri = Uri.parse(headURL);
		headDetail.setImageURI(headUri);
		
        ibtn_phoneDetail.setOnClickListener(this);
        lostDetail.setOnClickListener(this);


    }

    private void initView() {
        headDetail = (SimpleDraweeView) findViewById(R.id.iv_headDetail);
        lostDetail = (SimpleDraweeView) findViewById(R.id.iv_lostDetail);
        tv_nameDetail = (TextView) findViewById(R.id.tv_nameDetail);
        tv_timeDetail = (TextView) findViewById(R.id.tv_timeDetail);
        tv_titleDetail = (TextView) findViewById(R.id.tv_titleDetail);
        tv_describeDetail = (TextView) findViewById(R.id.tv_describeDetail);
        tv_phoneDetail = (TextView) findViewById(R.id.tv_phoneDetail);
        ibtn_phoneDetail = (ImageButton) findViewById(R.id.ibtn_phoneDetail);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibtn_phoneDetail:
                String newPhone = phone.trim();
                //调用系统的拨号服务实现电话拨打功能
                //封装一个拨打电话的intent，并且将电话号码包装成一个Uri对象传入
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + newPhone));
                DetailActivity.this.startActivity(intent);
                break;
            case R.id.iv_lostDetail:
                Intent intent1 = new Intent(DetailActivity.this, PhotoView.class);
                intent1.putExtra("url", url);
                startActivity(intent1);
                break;
        }
    }
}
