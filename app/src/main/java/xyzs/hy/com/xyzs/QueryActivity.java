package xyzs.hy.com.xyzs;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class QueryActivity extends Activity {
	private WebView mWebView;
	private String urlString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_query);
		mWebView = (WebView) findViewById(R.id.webView);
		urlString = getIntent().getStringExtra("http");
		initView();

	}

	private void initView() {
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setUseWideViewPort(true);
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webSettings.setBuiltInZoomControls(true);// 会出现放大缩小的按钮
		webSettings.setSupportZoom(true);
		webSettings.setSupportMultipleWindows(true);
//		webSettings.setTextZoom(100);// 必须设置为100，否则有可能不是100
		mWebView.setWebChromeClient(new WebChromeClient());
		mWebView.loadUrl(urlString);

		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				mWebView.loadUrl(url);
				return true;
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

		});

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mWebView.canGoBack()) {
				mWebView.goBack();
				return true;
			} else {
				System.exit(0);
			}

		}
		return super.onKeyDown(keyCode, event);
	}

}
