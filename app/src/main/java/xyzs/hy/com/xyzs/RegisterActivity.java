package xyzs.hy.com.xyzs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import xyzs.hy.com.xyzs.common.IsPhone;
import xyzs.hy.com.xyzs.entity.User;
import xyzs.hy.com.xyzs.ui.activity.BaseActivity;

/**
 * 注册页
 */
public class RegisterActivity extends BaseActivity implements TextView.OnEditorActionListener {
    @BindView(R.id.user_phone_edit)
    EditText editUserPhone;
    @BindView(R.id.verification_code_edit)
    EditText editVerification;
    @BindView(R.id.btn_verification)
    Button btnVerification;
    @BindView(R.id.pwd_edit)
    EditText editPwd;
    @BindView(R.id.btn_enroll)
    Button btnEnroll;
    private String userPhone;
    private String verificationCode;
    private String userPwd;
    private String imagePath = null;
    private Timer timeTimer;
    private final Object timerSync = new Object();
    private volatile int time = 60000;
    private double lastCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_register;
    }

    /**
     * 初始化视图
     */
    private void initLayout() {
        editUserPhone.setOnEditorActionListener(this);
        editVerification.setOnEditorActionListener(this);
        editPwd.setOnEditorActionListener(this);

    }

    //注册
    private void getRegister() {
        userPhone = editUserPhone.getText().toString();
        verificationCode = editVerification.getText().toString();
        userPwd = editPwd.getText().toString();
        if (TextUtils.isEmpty(userPhone)) {
            needToast(this, "手机号码不能为空");
            return;
        }
        if (TextUtils.isEmpty(verificationCode)) {
            needToast(this, "验证码不能为空");
            return;
        }
        if (TextUtils.isEmpty(userPwd)) {
            needToast(this, "密码不能为空");
            return;
        }
        if (!IsPhone.isPhone(userPhone)) {
            needToast(this, "手机号码不合法");
            return;
        }
        User user = new User();
        user.setMobilePhoneNumber(userPhone);
        user.setPassword(userPwd);
        user.setHeadSculpture("http://file.bmob.cn/M03/65/48/oYYBAFcyPeCAOKH5AAASPUfL_KA350.jpg");//默认头像
        user.signOrLogin(RegisterActivity.this, verificationCode, new SaveListener() {
            @Override
            public void onSuccess() {
                Intent intent = new Intent();
                intent.setClass(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(int p1, String p2) {
                needToast(RegisterActivity.this, p2);
            }
        });
    }

    //验证码
    private void getCaptcha() {
        userPhone = editUserPhone.getText().toString();
        if (TextUtils.isEmpty(userPhone)) {
            needToast(this, "手机号码不能为空");
            return;
        }
        if (!IsPhone.isPhone(userPhone)) {
            needToast(this, "手机号码不合法");
            return;
        }
        BmobSMS.requestSMSCode(this, userPhone, "XYZS", new RequestSMSCodeListener() {
            @Override
            public void done(Integer p1, BmobException p2) {
                if (p2 == null) {
                    destroyTimer();
                    initTimeText(1, 0);
                    createTimer();
                    needToast(RegisterActivity.this, "短信已发送，注意查收");
                } else {
                    destroyTimer();
                    needToast(RegisterActivity.this, p2.getMessage().toString());
                }
            }
        });
    }

    @OnClick({R.id.btn_verification, R.id.btn_enroll})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_verification:
                getCaptcha();
                break;
            case R.id.btn_enroll:
                getRegister();
                break;
        }
    }

    private void initTimeText(int minutes, int seconds) {
        time = 60000;
        lastCurrentTime = System.currentTimeMillis();
        btnVerification.setText(String.format("%1$d:%2$02d", minutes, seconds));
        btnVerification.setClickable(false);
    }

    private void destroyTimer() {
        try {
            synchronized (timerSync) {
                if (timeTimer != null) {
                    timeTimer.cancel();
                    timeTimer = null;
                }
            }
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }

    private void createTimer() {
        if (timeTimer != null) {

            return;
        }
        timeTimer = new Timer();
        timeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                double currentTime = System.currentTimeMillis();
                double diff = currentTime - lastCurrentTime;
                time -= diff;
                lastCurrentTime = currentTime;
                new Handler(getApplicationContext().getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (time > 0) {
                            int minutes = time / 1000 / 60;
                            int seconds = time / 1000 - minutes * 60;
                            btnVerification.setText(String.format("%1$d:%2$02d", minutes, seconds));
                        } else {
                            btnVerification.setText("获取验证码");
                            btnVerification.setClickable(true);
                            destroyTimer();
                        }
                    }
                });
            }
        }, 0, 1000);
    }

    @Override
    public boolean onEditorAction(TextView v, int i, KeyEvent event) {
        switch (v.getId()) {
            case R.id.user_phone_edit:
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    editVerification.requestFocus();
                    return true;
                }
                break;
            case R.id.verification_code_edit:
                if (i == EditorInfo.IME_ACTION_NEXT) {
                    editPwd.requestFocus();
                    return true;
                }
                break;
            case R.id.pwd_edit:
                if (i == EditorInfo.IME_ACTION_DONE) {
                    getRegister();
                    return true;
                }
                break;
        }
        return false;
    }
}
