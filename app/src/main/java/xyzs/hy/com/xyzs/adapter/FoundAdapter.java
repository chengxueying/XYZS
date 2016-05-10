package xyzs.hy.com.xyzs.adapter;

import android.content.*;
import android.support.v7.widget.RecyclerView;
import android.view.*;

import java.util.*;

import android.widget.TextView;
import android.net.*;

import com.facebook.drawee.view.*;

import xyzs.hy.com.xyzs.R;
import xyzs.hy.com.xyzs.entity.Found;


public class FoundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int ITEM_VIEW = 0;
    private static final int FOOT_VIEW = 1;//无图片
    private LayoutInflater mInflater;
    private ArrayList<Found> mFoundDatas;
    private OnRecyclerViewItemClickListener mOnItemClickListemer = null;

    public FoundAdapter(Context context, ArrayList<Found> mFoundDatas) {
        this.mFoundDatas = mFoundDatas;
        mInflater = LayoutInflater.from(context);
    }

    //静态接口
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Found lostdata);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOT_VIEW) {
            View view = mInflater.inflate(R.layout.recyclerview_no_photo_item, parent,
                    false);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        } else if (viewType == ITEM_VIEW) {
            View view = mInflater.inflate(R.layout.recyclerview_cardview_item, parent,
                    false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (mFoundDatas.get(position).getStatus() == 0) {
            return FOOT_VIEW;
        } else if (mFoundDatas.get(position).getStatus() == 1) {
            return ITEM_VIEW;
        }
        return super.getItemViewType(position);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).tv_describe.setText(mFoundDatas.get(position).getDescribe());
            ((ItemViewHolder) holder).tv_phone.setText(mFoundDatas.get(position).getPhone());
            ((ItemViewHolder) holder).tv_name.setText(mFoundDatas.get(position).getPublisher().getUsername());
            ((ItemViewHolder) holder).tv_name.setText(mFoundDatas.get(position).getPublisher().getUsername());
            ((ItemViewHolder) holder).tv_title.setText(mFoundDatas.get(position).getTitle());
            ((ItemViewHolder) holder).tv_time.setText(mFoundDatas.get(position).getUpdatedAt());
        } else if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).tv_describe.setText(mFoundDatas.get(position).getDescribe());
            ((MyViewHolder) holder).tv_phone.setText(mFoundDatas.get(position).getPhone());
            ((MyViewHolder) holder).tv_title.setText(mFoundDatas.get(position).getTitle());
            ((MyViewHolder) holder).tv_name.setText(mFoundDatas.get(position).getPublisher().getUsername());
            ((MyViewHolder) holder).tv_time.setText(mFoundDatas.get(position).getUpdatedAt());
            Uri uri = Uri.parse(mFoundDatas.get(position).getImageURL());
            ((MyViewHolder) holder).draweeView.setImageURI(uri);

        }
    }


    @Override
    public int getItemCount() {
        return mFoundDatas == null ? 0 : mFoundDatas.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListemer = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListemer != null) {
            //getTag方法获取数据
            mOnItemClickListemer.onItemClick(v, (Found) v.getTag());
        }
    }

    public void refreshDatas() {
        mFoundDatas.clear();
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time;
        TextView tv_title;
        TextView tv_describe;
        TextView tv_phone;
        TextView tv_name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone_no);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title_no);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time_no);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name_no);
            tv_describe = (TextView) itemView.findViewById(R.id.tv_describe_no);

        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time;
        TextView tv_title;
        TextView tv_describe;
        TextView tv_phone;
        SimpleDraweeView draweeView;
        TextView tv_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.iv_lost);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_describe = (TextView) itemView.findViewById(R.id.tv_describe);
        }
    }
}
