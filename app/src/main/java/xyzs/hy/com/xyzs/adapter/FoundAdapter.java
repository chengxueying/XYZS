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

/**
 * 寻物适配器
 */
public class FoundAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_VIEW = 0;//有图片
    private static final int FOOT_VIEW = 1;//无图片
    private LayoutInflater mInflater;
    private ArrayList<Found> mFoundDatas;//数据源
    private OnItemClickListener mOnItemClickListener;//点击回调

    public FoundAdapter(Context context, ArrayList<Found> mFoundDatas) {
        this.mFoundDatas = mFoundDatas;
        mInflater = LayoutInflater.from(context);
    }

    //回调
    public interface OnItemClickListener {
        //点击事件
        void OnItemClick(int position);

        //长按事件
        boolean OnItemLongClick(int position);
    }

    //设置回调
    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    //用的多布局，根据类型选择布局
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

    //多布局
    @Override
    public int getItemViewType(int position) {
        if (mFoundDatas.get(position).getStatus() == 0) {
            return FOOT_VIEW;
        } else if (mFoundDatas.get(position).getStatus() == 1) {
            return ITEM_VIEW;
        }
        return super.getItemViewType(position);
    }

    //结果处理
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        //如果holder是ItemViewHolder则进行以下操作
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).tv_describe.setText(mFoundDatas.get(position).getDescribe());
            ((ItemViewHolder) holder).tv_phone.setText(mFoundDatas.get(position).getPhone());
            ((ItemViewHolder) holder).tv_name.setText(mFoundDatas.get(position).getPublisher().getUsername());
            ((ItemViewHolder) holder).tv_name.setText(mFoundDatas.get(position).getPublisher().getUsername());
            ((ItemViewHolder) holder).tv_title.setText(mFoundDatas.get(position).getTitle());
            ((ItemViewHolder) holder).tv_time.setText(mFoundDatas.get(position).getUpdatedAt());
            Uri uri = Uri.parse(mFoundDatas.get(position).getPublisher().getHeadSculpture());
            ((ItemViewHolder) holder).head.setImageURI(uri);
            //给item设置监听
            ((ItemViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        //Returns the position of the ViewHolder in terms of the
                        //latest layout pass.
                        int pos = ((ItemViewHolder) holder).getLayoutPosition();
                        mOnItemClickListener.OnItemClick(pos);
                    }
                }
            });
            ((ItemViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemClickListener != null) {
                        int pos = ((ItemViewHolder) holder).getLayoutPosition();
                        return mOnItemClickListener.OnItemLongClick(pos);
                    }
                    return false;
                }
            });
        } else if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).tv_describe.setText(mFoundDatas.get(position).getDescribe());
            ((MyViewHolder) holder).tv_phone.setText(mFoundDatas.get(position).getPhone());
            ((MyViewHolder) holder).tv_title.setText(mFoundDatas.get(position).getTitle());
            ((MyViewHolder) holder).tv_name.setText(mFoundDatas.get(position).getPublisher().getUsername());
            ((MyViewHolder) holder).tv_time.setText(mFoundDatas.get(position).getUpdatedAt());
            Uri imageUri = Uri.parse(mFoundDatas.get(position).getPublisher().getHeadSculpture());
            ((MyViewHolder) holder).head.setImageURI(imageUri);
            Uri uri = Uri.parse(mFoundDatas.get(position).getImageURL());
            ((MyViewHolder) holder).draweeView.setImageURI(uri);
            //点击事件
            ((MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        //Returns the position of the ViewHolder in terms of the
                        //latest layout pass.
                        int pos = ((MyViewHolder) holder).getLayoutPosition();
                        mOnItemClickListener.OnItemClick(pos);
                    }
                }
            });
            //长按事件
            ((MyViewHolder) holder).itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemClickListener != null) {
                        int pos = ((MyViewHolder) holder).getLayoutPosition();
                        return mOnItemClickListener.OnItemLongClick(pos);
                    }
                    return false;
                }
            });


        }
    }


    @Override
    public int getItemCount() {
        return mFoundDatas == null ? 0 : mFoundDatas.size();
    }


    //刷新新数据
    public void addItem(List<Found> mNewData) {
        mFoundDatas.clear();
        mFoundDatas.addAll(mNewData);
        notifyDataSetChanged();

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time;
        TextView tv_title;
        TextView tv_describe;
        TextView tv_phone;
        TextView tv_name;
        SimpleDraweeView head;

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone_no);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title_no);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time_no);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name_no);
            tv_describe = (TextView) itemView.findViewById(R.id.tv_describe_no);
            head = (SimpleDraweeView) itemView.findViewById(R.id.iv_headSculpture_no);

        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time;
        TextView tv_title;
        TextView tv_describe;
        TextView tv_phone;
        SimpleDraweeView draweeView;
        TextView tv_name;
        SimpleDraweeView head;

        public MyViewHolder(View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.iv_lost);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_describe = (TextView) itemView.findViewById(R.id.tv_describe);
            head = (SimpleDraweeView) itemView.findViewById(R.id.iv_headSculpture);
        }
    }
}
