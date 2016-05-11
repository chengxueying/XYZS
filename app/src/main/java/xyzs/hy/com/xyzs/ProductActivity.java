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


public class ProductActivity extends Activity implements View.OnClickListener {
    private ProductFragment productFragment;
    private MyProductFragment myProductFragment;

    private Button toFoundFragment;
    private Button toLostFragment;
    private Button addDates;
    private boolean isLost = true;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_product);

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
                if (myProductFragment == null) {
                    myProductFragment = new MyProductFragment();
                    Transaction.add(R.id.content, myProductFragment);
                } else {
                    Transaction.show(myProductFragment);
                }
                break;
            case R.id.main_lost:
                toLostFragment.setBackgroundResource(R.color.white);
                toFoundFragment.setBackgroundResource(R.drawable.all_right_bg);
                toLostFragment.setTextColor(Color.rgb(102, 204, 255));
                toFoundFragment.setTextColor(Color.WHITE);
                hideFragment(Transaction);
                isLost = true;
                if (productFragment == null) {
                    productFragment = new ProductFragment();
                    Transaction.add(R.id.content, productFragment);
                } else {
                    Transaction.show(productFragment);
                }
                break;
            case R.id.toolbar_button_add:
                Intent intent = new Intent();
                intent.setClass(this, AddProductDataActivity.class);
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
        if (productFragment != null) {
            Transaction.hide(productFragment);
        }
        if (myProductFragment != null) {
            Transaction.hide(myProductFragment);
        }
    }

    private void setDefoultFragment() {
        fragmentManager = getFragmentManager();
        FragmentTransaction Transaction = fragmentManager.beginTransaction();
        isLost = true;
        if (productFragment == null) {
            productFragment = new ProductFragment();
            Transaction.add(R.id.content, productFragment);
        } else {
            Transaction.show(productFragment);
        }
        Transaction.commit();
    }
}
