package xyzs.hy.com.xyzs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import xyzs.hy.com.xyzs.common.HttpUtils;

/**
 * 成绩查询，对话框
 */
public class ScoreActivity extends Activity implements OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_score);
        LinearLayout btnScorce = (LinearLayout) findViewById(R.id.score_linearlayout);
        LinearLayout btnFourScorce = (LinearLayout) findViewById(R.id.fource_linearlayout);
        btnScorce.setOnClickListener(this);
        btnFourScorce.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ScoreActivity.this, QueryActivity.class);
        switch (v.getId()) {

            case R.id.score_linearlayout:
                intent.putExtra("http", HttpUtils.SCORE);
                startActivity(intent);
                finish();
                break;

            case R.id.fource_linearlayout:
                intent.putExtra("http", HttpUtils.FOUR_SCORE);
                startActivity(intent);
                finish();
                break;

        }

    }

}
