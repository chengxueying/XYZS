package xyzs.hy.com.xyzs.common;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/11.
 */
public class ActivityCollector extends Application{
    public static List<Activity> activities = new ArrayList<>();

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
            System.exit(0);
        }
    }
}
