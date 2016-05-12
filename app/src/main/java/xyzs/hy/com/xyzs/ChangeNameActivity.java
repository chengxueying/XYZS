package xyzs.hy.com.xyzs;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.view.View.*;
import android.view.*;

import cn.bmob.v3.*;
import xyzs.hy.com.xyzs.entity.*;
import cn.bmob.v3.listener.*;

/***
 * 修改用户名
 */

public class ChangeNameActivity extends Activity implements View.OnClickListener {
    private EditText mNewName;//用户名
    private LinearLayout mChangeName;//确认修改
    private String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//设置无title
        setContentView(R.layout.activity_changename);
        mNewName = (EditText) findViewById(R.id.new_person_name);
        mChangeName = (LinearLayout) findViewById(R.id.changename_linearlayout);
        mChangeName.setOnClickListener(this);
    }

    //结果返回
    private void sendIntent(String name) {
        Intent mIntent = new Intent();
        mIntent.putExtra("name", name);
        ChangeNameActivity.this.setResult(RESULT_OK, mIntent);
        finish();
    }

    @Override
    public void onClick(View v) {
        //关闭软键盘
        if (getCurrentFocus() != null) {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getCurrentFocus()
                                    .getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        }
        name = mNewName.getText().toString();
        if (name.equals("")) {
            Toast.makeText(getApplication(), "请输入新的用户名", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ChangeNameActivity.this);
        builder.setMessage("您确定修改用户名吗？").setTitle("温馨提示").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeName();
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();


    }

    /**
     * 修改名字
     */
    private void changeName() {
        User user = new User();
        user.setUsername(name);
        BmobUser bmobUser = BmobUser.getCurrentUser(ChangeNameActivity.this);
        user.update(ChangeNameActivity.this, bmobUser.getObjectId(), new UpdateListener() {

            @Override
            public void onSuccess() {
                Toast.makeText(getApplication(), "修改成功", Toast.LENGTH_SHORT).show();
                sendIntent(name);
                finish();

            }

            @Override
            public void onFailure(int p1, String p2) {
                Toast.makeText(getApplication(), "修改失败", Toast.LENGTH_SHORT).show();
            }


        });


    }

}
