package xyzs.hy.com.xyzs.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import xyzs.hy.com.xyzs.DetailActivity;
import xyzs.hy.com.xyzs.DetailNoActivity;
import xyzs.hy.com.xyzs.R;
import xyzs.hy.com.xyzs.adapter.ProductAdapter;
import xyzs.hy.com.xyzs.entity.Found;
import xyzs.hy.com.xyzs.entity.*;
import xyzs.hy.com.xyzs.adapter.*;


public class ProductFragment extends Fragment {
    private ArrayList<Product> mProductDatas;
    private RecyclerView mRecycleView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProductAdapter mProductAdapter;
    private View mFoundView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFoundView = inflater.inflate(R.layout.fragment_found, container, false);
        //设置SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) mFoundView.findViewById(R.id.SwipeRefreshLayout_found);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
												  .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
																  .getDisplayMetrics()));
        initData();
        initView();
        return mFoundView;
    }

    /**
     * 初始化视图
     */

    private void initView() {
        mRecycleView = (RecyclerView) mFoundView.findViewById(R.id.recyclerview_found);
        mProductAdapter = new ProductAdapter(getActivity(), mProductDatas);
        mRecycleView.setAdapter(mProductAdapter);
        LinearLayoutManager lin = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(lin);
        refreshDatas();
        mProductAdapter.setmOnItemClickListener(new ProductAdapter.OnItemClickListener() {
				@Override
				public void OnItemClick(int position) {
					Intent intent;
					if (mProductDatas.get(position).getStatus() == 0) {
						intent = new Intent(getActivity(), DetailNoActivity.class);
						intent.putExtra("name", mProductDatas.get(position).getPublisher().getUsername());
						intent.putExtra("time", mProductDatas.get(position).getUpdatedAt());
						intent.putExtra("title", mProductDatas.get(position).getTitle());
						intent.putExtra("describe", mProductDatas.get(position).getDescribe());
						intent.putExtra("phone", mProductDatas.get(position).getPhone());
						intent.putExtra("headURL", mProductDatas.get(position).getPublisher().getHeadSculpture());
						startActivity(intent);
					} else {
						intent = new Intent(getActivity(), DetailActivity.class);
						intent.putExtra("name", mProductDatas.get(position).getPublisher().getUsername());
						intent.putExtra("time", mProductDatas.get(position).getUpdatedAt());
						intent.putExtra("title", mProductDatas.get(position).getTitle());
						intent.putExtra("describe", mProductDatas.get(position).getDescribe());
						intent.putExtra("phone", mProductDatas.get(position).getPhone());
						intent.putExtra("headURL", mProductDatas.get(position).getPublisher().getHeadSculpture());
						intent.putExtra("url", mProductDatas.get(position).getImageURL());
						startActivity(intent);

					}
				}

				@Override
				public boolean OnItemLongClick(int position) {
					return false;
				}
			});

    }

    //初始化数据
    private void initData() {
        mProductDatas = new ArrayList<Product>();
        BmobQuery<Product> query = new BmobQuery<Product>();
        query.include("publisher");
        query.order("-updatedAt");
        query.findObjects(getActivity(), new FindListener<Product>() {
				@Override
				public void onSuccess(List<Product> object) {
					mProductDatas.addAll(object);
					initView();
					refreshDatas();
				}

				@Override
				public void onError(int code, String msg) {
					Toast.makeText(getActivity(), code + msg, Toast.LENGTH_LONG).show();
				}
			});
    }

    //下拉刷新
    private void refreshDatas() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
					new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								BmobQuery<Product> query = new BmobQuery<Product>();
								query.include("publisher");
								query.order("-updatedAt");
								query.findObjects(getActivity(), new FindListener<Product>() {
										@Override
										public void onSuccess(List<Product> object) {
											mProductAdapter.addItem(object);

										}

										@Override
										public void onError(int code, String msg) {
											Toast.makeText(getActivity(), code + msg, Toast.LENGTH_LONG).show();
										}
									});
								Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_LONG).show();
								mSwipeRefreshLayout.setRefreshing(false);
							}
						}, 1000);
				}
			});
    }
}
