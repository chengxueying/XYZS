package xyzs.hy.com.xyzs;

import android.app.*;
import android.graphics.Color;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;


import android.content.*;

import xyzs.hy.com.xyzs.fragment.FoundFragment;
import xyzs.hy.com.xyzs.fragment.LostFragment;
import xyzs.hy.com.xyzs.fragment.*;

/**
 * 寻物主界面
 **/
public class FoundActivity extends Activity implements OnClickListener {
    private MyFoundFragment myFoundFragment;//我的fragment
    private FoundFragment foundFragment;//全部fragment

    private Button toFoundFragment;
    private Button toLostFragment;
    private Button addDates;//添加
    private boolean isLost = true;

    private FragmentManager FragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_found);
        setDefoultFragment();
        initLayout();
    }

    /**
     * 初始化界面
     */
    private void initLayout() {
        toLostFragment = (Button) findViewById(R.id.main_my);
        toFoundFragment = (Button) findViewById(R.id.main_found);
        addDates = (Button) findViewById(R.id.toolbar_button_add);

        addDates.setOnClickListener(this);
        toLostFragment.setOnClickListener(this);
        toFoundFragment.setOnClickListener(this);
    }

    //按钮监听
    @Override
    public void onClick(View v) {
        FragmentManager = getFragmentManager();
        FragmentTransaction Transaction = FragmentManager.beginTransaction();

        switch (v.getId()) {
            case R.id.main_found:
                //修改全部与我的按钮颜色及字体颜色
                toFoundFragment.setBackgroundResource(R.color.white);
                toLostFragment.setBackgroundResource(R.drawable.all_right_bg);
                toFoundFragment.setTextColor(Color.rgb(102, 204, 255));
                toLostFragment.setTextColor(Color.WHITE);
                hideFragment(Transaction);
                isLost = false;
                if (foundFragment == null) {
                    foundFragment = new FoundFragment();
                    Transaction.add(R.id.content, foundFragment);
                } else {
                    Transaction.show(foundFragment);
                }
                break;
            case R.id.main_my:
                toLostFragment.setBackgroundResource(R.color.white);
                toFoundFragment.setBackgroundResource(R.drawable.all_left_bg);
                toLostFragment.setTextColor(Color.rgb(102, 204, 255));
                toFoundFragment.setTextColor(Color.WHITE);
                hideFragment(Transaction);
                isLost = true;
                if (myFoundFragment == null) {
                    myFoundFragment = new MyFoundFragment();
                    Transaction.add(R.id.content, myFoundFragment);
                } else {
                    Transaction.show(myFoundFragment);
                }
                break;
            case R.id.toolbar_button_add:
                Intent intent = new Intent();
                intent.setClass(this, AddFoundDataActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
        Transaction.commit();
    }

    //隐藏Fragment
    private void hideFragment(FragmentTransaction Transaction) {
        if (myFoundFragment != null) {
            Transaction.hide(myFoundFragment);
        }
        if (foundFragment != null) {
            Transaction.hide(foundFragment);
        }
    }

    /**
     * 添加fragment
     */
    private void setDefoultFragment() {
        FragmentManager = getFragmentManager();
        FragmentTransaction Transaction = FragmentManager.beginTransaction();
        isLost = true;
        if (myFoundFragment == null) {
            foundFragment = new FoundFragment();
            Transaction.add(R.id.content, foundFragment);
        } else {
            Transaction.show(foundFragment);
        }
        Transaction.commit();
    }
}
