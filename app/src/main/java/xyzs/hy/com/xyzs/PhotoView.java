package xyzs.hy.com.xyzs;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 大图显示
 */
public class PhotoView extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photo);
        SimpleDraweeView photoView = (SimpleDraweeView) findViewById(R.id.iv_photoView);
        String url = getIntent().getStringExtra("url");
        Uri uri = Uri.parse(url);
        photoView.setImageURI(uri);

    }
}
