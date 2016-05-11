package xyzs.hy.com.xyzs;

import android.app.*;
import android.os.*;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.content.*;

import cn.bmob.v3.*;
import xyzs.hy.com.xyzs.entity.*;
import cn.bmob.v3.listener.*;

import android.text.*;

public class ChangePasswordActivity extends Activity implements View.OnClickListener {

    private LinearLayout mChangePassword;
    private EditText mNewPassWord;
    private EditText mOldPassWord;
    private EditText mNewPassWordAgain;
    private String newPassword;
    private String oldPassword;
    private String newPasswordAgain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_changepassword);
        mOldPassWord = (EditText) findViewById(R.id.old_password_ed);
        mNewPassWord = (EditText) findViewById(R.id.new_password_ed);
        mNewPassWordAgain = (EditText) findViewById(R.id.new_password_again_ed);
        mChangePassword = (LinearLayout) findViewById(R.id.changepassword_linearlayout);
        mChangePassword.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changepassword_linearlayout:
                //关闭软键盘
                if (getCurrentFocus() != null) {
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                }
                oldPassword = mOldPassWord.getText().toString();
                newPassword = mNewPassWord.getText().toString();
                newPasswordAgain = mNewPassWordAgain.getText().toString();
                if (oldPassword.equals("") || newPassword.equals("") || newPasswordAgain.equals("")) {
                    Toast.makeText(getApplication(), "请完善信息...", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPassword.equals(newPasswordAgain)) {
                    Toast.makeText(getApplication(), "新密码两次输入不相同", Toast.LENGTH_SHORT).show();
                    return;
                }

                BmobUser.updateCurrentUserPassword(ChangePasswordActivity.this, oldPassword, newPassword, new UpdateListener() {

                    @Override
                    public void onSuccess() {
                        Toast.makeText(getApplication(), "修改成功，请重新登录", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(ChangePasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(int p1, String p2) {
                        Toast.makeText(getApplication(), "修改失败,当前密码不正确...", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
}
