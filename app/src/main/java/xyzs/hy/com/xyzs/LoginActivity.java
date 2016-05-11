package xyzs.hy.com.xyzs;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.*;
import android.view.View.*;
import android.view.*;

import cn.bmob.v3.*;
import cn.bmob.v3.listener.*;
import cn.bmob.v3.exception.*;
import xyzs.hy.com.xyzs.entity.User;

import android.content.*;

public class LoginActivity extends Activity implements OnClickListener {
    private Button login;
    private Button register;
    private EditText editUsername;
    private EditText editPassword;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        initLayout();
    }

    private void initLayout() {
        login = (Button) findViewById(R.id.btn_login);
        register = (Button) findViewById(R.id.btn_register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        editUsername = (EditText) findViewById(R.id.edit_username);
        editPassword = (EditText) findViewById(R.id.edit_password);
    }

    private void login() {
        BmobUser.loginByAccount(this, username, password, new LogInListener<User>() {
            @Override
            public void done(User p1, BmobException p2) {
                if (p1 != null) {
                    Toast.makeText(getApplicationContext(), "登录成功！", Toast.LENGTH_SHORT)
                            .show();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "用户名或者密码错误！", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    public void onClick(View p1) {
        switch (p1.getId()) {
            case R.id.btn_login:
                username = editUsername.getText().toString();
                password = editPassword.getText().toString();
                if (username == null && password == null) {
                    Toast.makeText(getApplicationContext(), "用户名和密码不能为空！", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    login();
                }
                break;
            case R.id.btn_register:
                Intent intent = new Intent();
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

}
