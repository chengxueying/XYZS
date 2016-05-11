package xyzs.hy.com.xyzs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import xyzs.hy.com.xyzs.common.HttpUtils;

/**
 * 生活查询页
 */
public class LiftActivity extends Activity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_left);

        Button btnMeiTuan = (Button) findViewById(R.id.btn_mei);
        Button btnELM = (Button) findViewById(R.id.btn_e);
        Button btnRuJia = (Button) findViewById(R.id.btn_ru);
        Button btnQiTian = (Button) findViewById(R.id.btn_qi);
        Button btnHuoChe = (Button) findViewById(R.id.btn_huo);
        Button btnXieCheng = (Button) findViewById(R.id.btn_xie);
        Button btnKuaiDi = (Button) findViewById(R.id.btn_kuai);
        Button btnAiCha = (Button) findViewById(R.id.btn_ai);

        btnMeiTuan.setOnClickListener(this);
        btnELM.setOnClickListener(this);
        btnRuJia.setOnClickListener(this);
        btnQiTian.setOnClickListener(this);
        btnHuoChe.setOnClickListener(this);
        btnXieCheng.setOnClickListener(this);
        btnKuaiDi.setOnClickListener(this);
        btnAiCha.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mei:
                sendIntent(HttpUtils.MEI_TUAN);
                break;
            case R.id.btn_e:
                sendIntent(HttpUtils.E_MA);
                break;
            case R.id.btn_ru:
                sendIntent(HttpUtils.RU_JIA);
                break;
            case R.id.btn_qi:
                sendIntent(HttpUtils.QI_TIAN);
                break;
            case R.id.btn_huo:
                sendIntent(HttpUtils.HUO_CHE_PIAO);
                break;
            case R.id.btn_xie:
                sendIntent(HttpUtils.XIE_CHENG);
                break;
            case R.id.btn_kuai:
                sendIntent(HttpUtils.KUAI_DI);
                break;
            case R.id.btn_ai:
                sendIntent(HttpUtils.AI_CHA);
                break;
        }


    }

    private void sendIntent(String name) {
        Intent intent = new Intent(LiftActivity.this, QueryActivity.class);
        intent.putExtra("http", name);
        startActivity(intent);
    }
}
