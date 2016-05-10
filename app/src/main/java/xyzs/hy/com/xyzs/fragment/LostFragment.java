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
import xyzs.hy.com.xyzs.adapter.LostAdapter;
import xyzs.hy.com.xyzs.entity.Lost;


public class LostFragment extends Fragment {
    private ArrayList<Lost> lostDatas;
    private RecyclerView mRecycleView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private LostAdapter adapter;
    private View lost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lost = inflater.inflate(R.layout.fragment_lost, container, false);

        lostDatas = new ArrayList<Lost>();
        mSwipeRefreshLayout = (SwipeRefreshLayout) lost.findViewById(R.id.SwipeRefreshLayout_lost);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        refreshDatas();

        if (lostDatas == null || lostDatas.size() == 0) {
            getDatas();
        }

        return lost;
    }

    private void setDatas() {
        if (lostDatas.size() != 0) {
            mRecycleView = (RecyclerView) lost.findViewById(R.id.Recyclerview_lost);
            adapter = new LostAdapter(getActivity(), lostDatas);
            mRecycleView.setAdapter(adapter);
            LinearLayoutManager lin = new LinearLayoutManager(getActivity());
            mRecycleView.setLayoutManager(lin);
            adapter.setmOnItemClickListener(new LostAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(int position) {
                    Intent intent;
                    if (lostDatas.get(position).getStatus() == 0) {
                        intent = new Intent(getActivity(), DetailNoActivity.class);
                        intent.putExtra("name", lostDatas.get(position).getPublisher().getUsername());
                        intent.putExtra("time", lostDatas.get(position).getUpdatedAt());
                        intent.putExtra("title", lostDatas.get(position).getTitle());
                        intent.putExtra("describe", lostDatas.get(position).getDescribe());
                        intent.putExtra("phone", lostDatas.get(position).getPhone());
                        startActivity(intent);
                    } else {
                        intent = new Intent(getActivity(), DetailActivity.class);
//                        intent.putExtra("head",lostDatas.get(position).g)
                        intent.putExtra("name", lostDatas.get(position).getPublisher().getUsername());
                        intent.putExtra("time", lostDatas.get(position).getUpdatedAt());
                        intent.putExtra("title", lostDatas.get(position).getTitle());
                        intent.putExtra("describe", lostDatas.get(position).getDescribe());
                        intent.putExtra("phone", lostDatas.get(position).getPhone());
                        intent.putExtra("url", lostDatas.get(position).getimageURL());
                        startActivity(intent);

                    }
                }

                @Override
                public boolean OnItemLongClick(int position) {
                    return false;
                }
            });
        }
    }

    private void getDatas() {
        BmobQuery<Lost> query = new BmobQuery<Lost>();
        query.order("-updatedAt");
        query.include("publisher");
        query.findObjects(getActivity(), new FindListener<Lost>() {
            @Override
            public void onSuccess(List<Lost> object) {
                lostDatas.addAll(object);
                setDatas();
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
                        adapter.refreshDatas();
                        BmobQuery<Lost> query = new BmobQuery<Lost>();
                        query.order("-updatedAt");
                        query.include("publisher");
                        query.findObjects(getActivity(), new FindListener<Lost>() {
                            @Override
                            public void onSuccess(List<Lost> object) {
                                lostDatas.addAll(object);
                                setDatas();
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
