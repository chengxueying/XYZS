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
import java.io.*;
import android.provider.*;
import android.graphics.*;
import android.database.*;
import cn.bmob.v3.datatype.*;
import cn.bmob.v3.listener.*;

public class UserCenterActivity extends Activity implements OnClickListener
{
	public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
	private String imagePath = null;
    private Bitmap bitmap;
	private Uri imageUri;
	
	private SimpleDraweeView iv_head;
	private TextView tv_name;
	private TextView tv_phone;
	private Button btn_finish;
	private Button btn_changePassword;
	private TableRow changeName;
	private TableRow changeHead;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usercenter);
		
		initLayout();
		
		User user = BmobUser.getCurrentUser(this, User.class);
		Uri uri = Uri.parse(user.getHeadSculpture());
		iv_head.setImageURI(uri);
		
		tv_name.setText(user.getUsername());
		tv_phone.setText(user.getMobilePhoneNumber());
	}

	private void initLayout() {
		iv_head = (SimpleDraweeView) findViewById(R.id.head);
		tv_name = (TextView) findViewById(R.id.tab_name);
		tv_phone = (TextView) findViewById(R.id.tab_phone);
		
		changeHead = (TableRow) findViewById(R.id.change_head);
		changeName = (TableRow) findViewById(R.id.change_name);
		changeHead.setOnClickListener(this);
		changeName.setOnClickListener(this);
		
		btn_finish = (Button) findViewById(R.id.finish);
		btn_changePassword = (Button) findViewById(R.id.change_password);
		btn_finish.setOnClickListener(this);
		btn_changePassword.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case R.id.change_head:
				// 创建File对象，用于存储选择的照片
                File outputImage = new File(Environment.
											getExternalStorageDirectory(), "tempImage.jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageUri = Uri.fromFile(outputImage);
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setType("image/*");
                intent.putExtra("crop", true);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
				break;
			case R.id.change_name:
				Intent intent1 = new Intent();
				intent1.setClass(UserCenterActivity.this,ChangeNameActivity.class);
				startActivity(intent1);
				break;
			case R.id.finish:
				BmobUser.logOut(this);
				Intent intent4 = new Intent();
				intent4.setClass(UserCenterActivity.this,LoginActivity.class);
				startActivity(intent4);
				finish();
				break;
			case R.id.change_password:
				Intent intent2 = new Intent();
				intent2.setClass(UserCenterActivity.this,ChangePasswordActivity.class);
				startActivity(intent2);
				break;
		}
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        imageUri = data.getData();
                    }
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageUri, "image/*");
                    intent.putExtra("aspectX", 1);
                    intent.putExtra("aspectY", 1);
                    intent.putExtra("scale", true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, CROP_PHOTO); // 启动裁剪程序
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        bitmap = BitmapFactory.decodeStream
						(getContentResolver()
						 .openInputStream(imageUri));
                        Uri uri;
                        if (data.getData() == null) {
                            uri = imageUri;
                        } else {
                            uri = data.getData();
                        }
                        try {
                            String[] pojo = {MediaStore.Images.Media.DATA};
                            Cursor cursor = getContentResolver().query(uri, pojo, null, null, null);
                            ContentResolver cv = this.getContentResolver();
                            int colunm_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            imagePath = cursor.getString(colunm_index);
                        } catch (Exception e) {

                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
		upLodeImage();
    }

	private void upLodeImage()
	{
		if (imagePath == null) {
			
        } else {
            final BmobFile bmobFile = new BmobFile(new File(imagePath));
            bmobFile.uploadblock(UserCenterActivity.this, new UploadFileListener() {
					@Override
					public void onSuccess() {
						final String head = bmobFile.getFileUrl(UserCenterActivity.this);
						User user = new User();
						user.setHeadSculpture(head);
						BmobUser bmobUser = BmobUser.getCurrentUser(UserCenterActivity.this);
						user.update(UserCenterActivity.this, bmobUser.getObjectId(), new UpdateListener(){

								@Override
								public void onSuccess()
								{
									Toast.makeText(getApplication(), "修改成功", Toast.LENGTH_SHORT).show();
									Uri uri = Uri.parse(head);
									iv_head.setImageURI(uri);
								}

								@Override
								public void onFailure(int p1, String p2)
								{
									Toast.makeText(getApplication(), "修改失败", Toast.LENGTH_SHORT).show();
								}
							});
					}

					@Override
					public void onFailure(int p1, String p2) {
					}
				});
        }
	}

}
