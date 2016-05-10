package xyzs.hy.com.xyzs;


import android.app.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import xyzs.hy.com.xyzs.fragment.FoundFragment;
import xyzs.hy.com.xyzs.fragment.LostFragment;
import xyzs.hy.com.xyzs.fragment.*;


public class LostActivity extends Activity implements View.OnClickListener {
    private LostFragment lostFragment;
    private MyLostFragment myLostFragment;

    private Button toFoundFragment;
    private Button toLostFragment;
    private Button addDates;
    private boolean isLost = true;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lost);

        setDefoultFragment();
        initLayout();
    }

    //初始化控件
    private void initLayout() {
        toLostFragment = (Button) findViewById(R.id.main_lost);
        toFoundFragment = (Button) findViewById(R.id.my_lost);
        addDates = (Button) findViewById(R.id.toolbar_button_add);

        addDates.setOnClickListener(this);
        toLostFragment.setOnClickListener(this);
        toFoundFragment.setOnClickListener(this);
    }

    //按钮监听
    @Override
    public void onClick(View v) {
        fragmentManager = getFragmentManager();
        FragmentTransaction Transaction = fragmentManager.beginTransaction();

        switch (v.getId()) {
            case R.id.my_lost:
                toFoundFragment.setBackgroundResource(R.color.white);
                toLostFragment.setBackgroundResource(R.drawable.all_left_bg);
                toFoundFragment.setTextColor(Color.rgb(102, 204, 255));
                toLostFragment.setTextColor(Color.WHITE);
                hideFragment(Transaction);
                isLost = false;
                if (myLostFragment == null) {
                    myLostFragment = new MyLostFragment();
                    Transaction.add(R.id.content, myLostFragment);
                } else {
                    Transaction.show(myLostFragment);
                }
                break;
            case R.id.main_lost:
                toLostFragment.setBackgroundResource(R.color.white);
                toFoundFragment.setBackgroundResource(R.drawable.all_right_bg);
                toLostFragment.setTextColor(Color.rgb(102, 204, 255));
                toFoundFragment.setTextColor(Color.WHITE);
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
        if (myLostFragment != null) {
            Transaction.hide(myLostFragment);
        }
    }

    private void setDefoultFragment() {
        fragmentManager = getFragmentManager();
        FragmentTransaction Transaction = fragmentManager.beginTransaction();
        isLost = true;
        if (lostFragment == null) {
            lostFragment = new LostFragment();
            Transaction.add(R.id.content, lostFragment);
        } else {
            Transaction.show(lostFragment);
        }
        Transaction.commit();
    }
}
