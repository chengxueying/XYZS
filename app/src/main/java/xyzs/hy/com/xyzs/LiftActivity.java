package xyzs.hy.com.xyzs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyzs.hy.com.xyzs.common.HttpUtils;
import xyzs.hy.com.xyzs.ui.activity.BaseActivity;

/**
 * 生活查询页
 */
public class LiftActivity extends BaseActivity {
    @BindView(R.id.btn_mei)
    Button btnMei;
    @BindView(R.id.btn_e)
    Button btnE;
    @BindView(R.id.btn_ru)
    Button btnRu;
    @BindView(R.id.btn_qi)
    Button btnQi;
    @BindView(R.id.btn_huo)
    Button btnHuo;
    @BindView(R.id.btn_xie)
    Button btnXie;
    @BindView(R.id.btn_kuai)
    Button btnKuai;
    @BindView(R.id.btn_ai)
    Button btnAi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_left;
    }

    private void sendIntent(String name) {
        Intent intent = new Intent(LiftActivity.this, QueryActivity.class);
        intent.putExtra("http", name);
        startActivity(intent);
    }

    @OnClick({R.id.btn_mei, R.id.btn_e, R.id.btn_ru, R.id.btn_qi, R.id.btn_huo, R.id.btn_xie, R.id.btn_kuai, R.id.btn_ai})
    public void onClick(View view) {
        switch (view.getId()) {
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
}
