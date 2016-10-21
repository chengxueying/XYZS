package xyzs.hy.com.xyzs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import xyzs.hy.com.xyzs.entity.User;
import xyzs.hy.com.xyzs.ui.activity.BaseActivity;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity implements TextView.OnEditorActionListener {
    @BindView(R.id.user_name_edit)
    EditText editUserName;
    @BindView(R.id.user_pwd_edit)
    EditText editPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private static String userName;
    private static String userPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_login);
        editUserName.setOnEditorActionListener(this);
        editPwd.setOnEditorActionListener(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    private void login() {
        userName = editUserName.getText().toString();
        userPwd = editPwd.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            needToast(this,"用户名不能为空");
//            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userPwd)) {
            needToast(this,"密码不能为空");
//            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT)
//                    .show();
            return;
        }
        BmobUser.loginByAccount(this, userName, userPwd, new LogInListener<User>() {
            @Override
            public void done(User p1, BmobException p2) {
                if (p1 != null) {
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    needToast(LoginActivity.this,"用户名或者密码错误");
//                    Toast.makeText(getApplicationContext(), "用户名或者密码错误！", Toast.LENGTH_SHORT)
//                            .show();
                }
            }
        });
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View p1) {
        switch (p1.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                Intent intent = new Intent();
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int i, KeyEvent event) {
        switch (v.getId()) {
            case R.id.user_name_edit:
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    editPwd.requestFocus();
                    return true;
                }
                break;
            case R.id.user_pwd_edit:
                if (i == EditorInfo.IME_ACTION_DONE) {
                    login();
                    return true;
                }
                break;
        }
        return false;
    }

}
