package xyzs.hy.com.xyzs;

import android.app.*;
import android.os.*;
import com.facebook.drawee.view.*;
import android.widget.*;
import android.net.*;
import cn.bmob.v3.*;
import xyzs.hy.com.xyzs.entity.*;
import android.view.View.*;
import android.view.*;
import android.content.*;

public class UserCenterActivity extends Activity
{
	private SimpleDraweeView iv_head;
	private TextView tv_name;
	private Button btn_change;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usercenter);
		
		initLayout();
		
		User user = BmobUser.getCurrentUser(this, User.class);
		Uri uri = Uri.parse(user.getHeadSculpture());
		iv_head.setImageURI(uri);
		tv_name.setText(user.getUsername());
		btn_change.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1) {
					Intent intent = new Intent();
					intent.setClass(UserCenterActivity.this, SetNewUserDatasActivity.class);
					startActivity(intent);
				}
		});
	}

	private void initLayout() {
		iv_head = (SimpleDraweeView) findViewById(R.id.iv_headSculpture);
		tv_name = (TextView) findViewById(R.id.tv_name);
		btn_change = (Button) findViewById(R.id.btn_change);
	}

}
