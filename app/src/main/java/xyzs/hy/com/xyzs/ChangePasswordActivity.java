package xyzs.hy.com.xyzs;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.content.*;
import cn.bmob.v3.*;
import xyzs.hy.com.xyzs.entity.*;
import cn.bmob.v3.listener.*;
import android.text.*;

public class ChangePasswordActivity extends Activity
{
	private EditText oldContext;
	private Button refresh;
	private EditText newContext;
	private String newPassword;
	private String oldPassword;
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changepassword);

		oldContext = (EditText) findViewById(R.id.edi_oldcontext);
		newContext = (EditText) findViewById(R.id.edi_newcontext);
		refresh = (Button) findViewById(R.id.btn_finish);

		refresh.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					oldPassword = oldContext.getText().toString();
					newPassword = newContext.getText().toString();
					
					if (TextUtils.isEmpty(oldContext.getText())&&TextUtils.isEmpty(newContext.getText()))
					{
						Toast.makeText(getApplication(), "请输入新旧两个密码", Toast.LENGTH_SHORT).show();
					}
					else
					{
						BmobUser.updateCurrentUserPassword(ChangePasswordActivity.this, oldPassword, newPassword, new UpdateListener(){

								@Override
								public void onSuccess()
								{
									Toast.makeText(getApplication(), "修改成功，请重新登录", Toast.LENGTH_SHORT).show();
									Intent intent = new Intent();
									intent.setClass(ChangePasswordActivity.this,LoginActivity.class);
									startActivity(intent);
									finish();
								}

								@Override
								public void onFailure(int p1, String p2)
								{
									Toast.makeText(getApplication(), "修改失败", Toast.LENGTH_SHORT).show();
								}

							
						});
					}
				}
			});
	}
}
