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

public class ChangeNameActivity extends Activity
{
	private EditText context;
	private Button refresh;
	private String datas;
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changename);

		context = (EditText) findViewById(R.id.edi_context);
		refresh = (Button) findViewById(R.id.btn_finish);


		refresh.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View p1)
				{
					datas = context.getText().toString();
					if (TextUtils.isEmpty(context.getText()))
					{
						Toast.makeText(getApplication(), "请输入新的用户名", Toast.LENGTH_SHORT).show();
					}
					else
					{
						User user = new User();
						user.setUsername(datas);
						BmobUser bmobUser = BmobUser.getCurrentUser(ChangeNameActivity.this);
						user.update(ChangeNameActivity.this, bmobUser.getObjectId(), new UpdateListener(){

								@Override
								public void onSuccess()
								{
									Toast.makeText(getApplication(), "修改成功", Toast.LENGTH_SHORT).show();
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
