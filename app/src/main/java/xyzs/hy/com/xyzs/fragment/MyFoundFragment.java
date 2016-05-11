package xyzs.hy.com.xyzs.fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
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
import xyzs.hy.com.xyzs.adapter.FoundAdapter;
import xyzs.hy.com.xyzs.entity.Found;
import cn.bmob.v3.*;
import xyzs.hy.com.xyzs.entity.*;
import cn.bmob.v3.listener.*;


public class MyFoundFragment extends Fragment {
    private ArrayList<Found> mFoundDatas;
    private RecyclerView mRecycleView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FoundAdapter mFoundAdapter;
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

    private void initView() {
        mRecycleView = (RecyclerView) mFoundView.findViewById(R.id.recyclerview_found);
        mFoundAdapter = new FoundAdapter(getActivity(), mFoundDatas);
        mRecycleView.setAdapter(mFoundAdapter);
        LinearLayoutManager lin = new LinearLayoutManager(getActivity());
        mRecycleView.setLayoutManager(lin);
        refreshDatas();
        mFoundAdapter.setmOnItemClickListener(new FoundAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                Intent intent;
                if (mFoundDatas.get(position).getStatus() == 0) {
                    intent = new Intent(getActivity(), DetailNoActivity.class);
                    intent.putExtra("name", mFoundDatas.get(position).getPublisher().getUsername());
                    intent.putExtra("time", mFoundDatas.get(position).getUpdatedAt());
                    intent.putExtra("title", mFoundDatas.get(position).getTitle());
                    intent.putExtra("describe", mFoundDatas.get(position).getDescribe());
                    intent.putExtra("phone", mFoundDatas.get(position).getPhone());
                    intent.putExtra("headURL", mFoundDatas.get(position).getPublisher().getHeadSculpture());
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("name", mFoundDatas.get(position).getPublisher().getUsername());
                    intent.putExtra("time", mFoundDatas.get(position).getUpdatedAt());
                    intent.putExtra("title", mFoundDatas.get(position).getTitle());
                    intent.putExtra("describe", mFoundDatas.get(position).getDescribe());
                    intent.putExtra("phone", mFoundDatas.get(position).getPhone());
                    intent.putExtra("url", mFoundDatas.get(position).getImageURL());
                    intent.putExtra("headURL", mFoundDatas.get(position).getPublisher().getHeadSculpture());
                    startActivity(intent);

                }
            }

            @Override
            public boolean OnItemLongClick(int position) {
                final int pos = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("确定要删除吗？").setTitle("提示").setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Found found = new Found();
                        found.setObjectId(mFoundDatas.get(pos).getObjectId());
                        found.delete(getActivity(), new DeleteListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        initData();
                                        mFoundAdapter.notifyDataSetChanged();

                                    }
                                });

                            }

                            @Override
                            public void onFailure(int p1, String p2) {
                                Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                        dialog.dismiss();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
                return true;
            }
        });


    }

    private void initData() {
        mFoundDatas = new ArrayList<Found>();
        User user = BmobUser.getCurrentUser(getActivity(), User.class);
        BmobQuery<Found> query = new BmobQuery<Found>();
        query.addWhereEqualTo("publisher", user);
        query.order("-updatedAt");
        query.include("publisher");
        query.findObjects(getActivity(), new FindListener<Found>() {
            @Override
            public void onSuccess(List<Found> object) {
                mFoundDatas.addAll(object);
                initView();
                refreshDatas();
            }

            @Override
            public void onError(int code, String msg) {
                Toast.makeText(getActivity(), code + msg, Toast.LENGTH_LONG).show();
            }
        });

    }


    private void refreshDatas() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        User user = BmobUser.getCurrentUser(getActivity(), User.class);
                        BmobQuery<Found> query = new BmobQuery<Found>();
                        query.addWhereEqualTo("publisher", user);
                        query.order("-updatedAt");
                        query.include("publisher");
                        query.findObjects(getActivity(), new FindListener<Found>() {
                            @Override
                            public void onSuccess(List<Found> object) {
                                mFoundAdapter.addItem(object);

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
