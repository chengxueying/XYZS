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
    private ArrayList<Found> FoundDatas;
    private RecyclerView mRecycleView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FoundAdapter adapter;
    private View Found;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Found = inflater.inflate(R.layout.fragment_found, container, false);

        FoundDatas = new ArrayList<Found>();
        mSwipeRefreshLayout = (SwipeRefreshLayout) Found.findViewById(R.id.SwipeRefreshLayout_found);
        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE);
        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
												  .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
																  .getDisplayMetrics()));
        refreshDatas();

        if (FoundDatas == null || FoundDatas.size() == 0) {
            getDatas();
        }

        return Found;
    }

    private void setDatas() {
        if (FoundDatas.size() != 0) {
            mRecycleView = (RecyclerView) Found.findViewById(R.id.recyclerview_found);
            adapter = new FoundAdapter(getActivity(), FoundDatas);
            mRecycleView.setAdapter(adapter);
            LinearLayoutManager lin = new LinearLayoutManager(getActivity());
            mRecycleView.setLayoutManager(lin);
			adapter.setmOnItemClickListener(new FoundAdapter.OnItemClickListener() {
				@Override
				public void OnItemClick(int position) {
					Intent intent;
					if (FoundDatas.get(position).getStatus() == 0) {
						intent = new Intent(getActivity(), DetailNoActivity.class);
						intent.putExtra("name", FoundDatas.get(position).getPublisher().getUsername());
						intent.putExtra("time", FoundDatas.get(position).getUpdatedAt());
						intent.putExtra("title", FoundDatas.get(position).getTitle());
						intent.putExtra("describe", FoundDatas.get(position).getDescribe());
						intent.putExtra("phone", FoundDatas.get(position).getPhone());
						intent.putExtra("headURL",FoundDatas.get(position).getPublisher().getHeadSculpture());
						startActivity(intent);
					} else {
						intent = new Intent(getActivity(), DetailActivity.class);
//                        intent.putExtra("head",lostDatas.get(position).g)
						intent.putExtra("name", FoundDatas.get(position).getPublisher().getUsername());
						intent.putExtra("time", FoundDatas.get(position).getUpdatedAt());
						intent.putExtra("title", FoundDatas.get(position).getTitle());
						intent.putExtra("describe", FoundDatas.get(position).getDescribe());
						intent.putExtra("phone", FoundDatas.get(position).getPhone());
						intent.putExtra("url", FoundDatas.get(position).getImageURL());
						intent.putExtra("headURL",FoundDatas.get(position).getPublisher().getHeadSculpture());
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
							Lost lost = new Lost();
							lost.setObjectId(FoundDatas.get(pos).getObjectId());
							lost.delete(getActivity(), new DeleteListener() {

									@Override
									public void onSuccess()
									{
										Toast.makeText(getActivity(), "删除成功", Toast.LENGTH_SHORT).show();
										refreshDatas();
									}

									@Override
									public void onFailure(int p1, String p2)
									{
										Toast.makeText(getActivity(), "删除失败", Toast.LENGTH_SHORT).show();
									}
								});
							//Toast.makeText(getActivity(), "长安了++++", Toast.LENGTH_SHORT).show();
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
    }

    private void getDatas() {
		User user = BmobUser.getCurrentUser(getActivity(),User.class);
        BmobQuery<Found> query = new BmobQuery<Found>();
		query.addWhereEqualTo("publisher", user);
		query.order("-updatedAt");
		query.include("publisher");
        query.findObjects(getActivity(), new FindListener<Found>() {
				@Override
				public void onSuccess(List<Found> object) {
					FoundDatas.addAll(object);
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
								User user = BmobUser.getCurrentUser(getActivity(),User.class);
								BmobQuery<Found> query = new BmobQuery<Found>();
								query.addWhereEqualTo("publisher", user);
								query.order("-updatedAt");
								query.include("publisher");
								query.findObjects(getActivity(), new FindListener<Found>() {
										@Override
										public void onSuccess(List<Found> object) {
											FoundDatas.addAll(object);
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
