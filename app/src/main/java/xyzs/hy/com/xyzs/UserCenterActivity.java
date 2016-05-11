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

public class UserCenterActivity extends Activity implements OnClickListener {
    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int REQUEST = 3;
    private String imagePath = null;
    private Bitmap bitmap;
    private Uri imageUri;

    private SimpleDraweeView iv_head;
    private EditText mPersonAccount;
    private EditText mPersonName;
    private Button mBtnExit;
    private Button mBtnChange;
    private ImageButton mBtnAccount;
    private ImageButton mBtnName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usercenter);
        initView();
        User user = BmobUser.getCurrentUser(this, User.class);
        Uri uri = Uri.parse(user.getHeadSculpture());
        iv_head.setImageURI(uri);
        mPersonName.setText(user.getUsername());
        mPersonAccount.setText(user.getMobilePhoneNumber());
    }

    private void initView() {
        iv_head = (SimpleDraweeView) findViewById(R.id.head);
        mPersonName = (EditText) findViewById(R.id.person_name);
        mPersonAccount = (EditText) findViewById(R.id.person_account);
        mBtnExit = (Button) findViewById(R.id.person_exit);
        mBtnChange = (Button) findViewById(R.id.change_password);
        mBtnAccount = (ImageButton) findViewById(R.id.ibn_person_account);
        mBtnName = (ImageButton) findViewById(R.id.ibn_person_name);
        mBtnExit.setOnClickListener(this);
        mBtnChange.setOnClickListener(this);
        iv_head.setOnClickListener(this);
        mBtnAccount.setOnClickListener(this);
        mBtnName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head:
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
            case R.id.ibn_person_name:
                Intent intent1 = new Intent();
                intent1.setClass(UserCenterActivity.this, ChangeNameActivity.class);
                startActivityForResult(intent1, REQUEST);
//                startActivity(intent1);
                break;
            case R.id.ibn_person_account:
                Toast.makeText(UserCenterActivity.this, "亲，暂时不提供账号修改...", Toast.LENGTH_SHORT).show();
                break;
            case R.id.person_exit:
                BmobUser.logOut(this);
                Intent intent4 = new Intent();
                intent4.setClass(UserCenterActivity.this, LoginActivity.class);
                startActivity(intent4);
                this.setResult(RESULT_OK, intent4);
                finish();
                break;
            case R.id.change_password:
                Intent intent2 = new Intent();
                intent2.setClass(UserCenterActivity.this, ChangePasswordActivity.class);
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
            case REQUEST:
                if (resultCode == RESULT_OK) {
                    String newName = data.getStringExtra("name");
                    mPersonName.setText(newName);
                }
//
                break;
        }
        upLodeImage();
    }

    private void upLodeImage() {
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
                    user.update(UserCenterActivity.this, bmobUser.getObjectId(), new UpdateListener() {

                        @Override
                        public void onSuccess() {
                            Toast.makeText(getApplication(), "修改成功", Toast.LENGTH_SHORT).show();
                            Uri uri = Uri.parse(head);
                            iv_head.setImageURI(uri);
                        }

                        @Override
                        public void onFailure(int p1, String p2) {
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
