package xyzs.hy.com.xyzs;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;


import android.content.*;

import xyzs.hy.com.xyzs.fragment.FoundFragment;
import xyzs.hy.com.xyzs.fragment.LostFragment;

public class FoundActivity extends Activity implements OnClickListener {
    private LostFragment lostFragment;
    private FoundFragment foundFragment;

    private Button toFoundFragment;
    private Button toLostFragment;
    private Button addDates;
    private boolean isLost = true;

    private FragmentManager FragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_found);

        setDefoultFragment();
        initLayout();
    }

    //初始化控件
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
                hideFragment(Transaction);
                isLost = true;
                if (lostFragment == null) {
                    lostFragment = new LostFragment();
                    Transaction.add(R.id.content, lostFragment);
                } else {
                    Transaction.show(lostFragment);
                }
                break;
            case R.id.toolbar_button_add:
                Intent intent = new Intent();
                intent.setClass(this, AddLostDataActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        Transaction.commit();
    }

    //隐藏Fragment
    private void hideFragment(FragmentTransaction Transaction) {
        if (lostFragment != null) {
            Transaction.hide(lostFragment);
        }
        if (foundFragment != null) {
            Transaction.hide(foundFragment);
        }
    }

    private void setDefoultFragment() {
        FragmentManager = getFragmentManager();
        FragmentTransaction Transaction = FragmentManager.beginTransaction();
        isLost = true;
        if (lostFragment == null) {
            foundFragment = new FoundFragment();
            Transaction.add(R.id.content, foundFragment);
        } else {
            Transaction.show(foundFragment);
        }
        Transaction.commit();
    }
}
