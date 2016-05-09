package xyzs.hy.com.xyzs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class ScoreDialog extends Activity implements OnClickListener {

	// 四六级查询网址
	private static String FOUR_SCORE = "http://cet.99sushe.com/";
	
	//成绩查询
	private static String SCORE="http://jw.qdu.edu.cn/academic/common/security/electiveLogin.jsp";
	
	//美食
	private static String FOOD="http://qd.meituan.com/?utm_campaign=baidu&utm_medium=organic&utm_source=baidu&utm_content=homepage&utm_term=";

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
		Intent intent = new Intent(ScoreDialog.this, QueryActivity.class);
		switch (v.getId()) {

		case R.id.score_linearlayout:
			intent.putExtra("four_score", FOOD);
			startActivity(intent);
			finish();
			break;

		case R.id.fource_linearlayout:
			intent.putExtra("four_score", FOUR_SCORE);
			startActivity(intent);
			finish();
			break;

		}

	}

}
