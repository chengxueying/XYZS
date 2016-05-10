package xyzs.hy.com.xyzs.adapter;

import android.content.*;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.TextView;
import android.net.*;

import com.facebook.drawee.view.*;

import java.util.ArrayList;

import xyzs.hy.com.xyzs.R;
import xyzs.hy.com.xyzs.entity.Lost;


public class LostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_VIEW = 0;
    private static final int FOOT_VIEW = 1;//无图片
    private LayoutInflater mInflater;
    private ArrayList<Lost> lostDatas;
    private OnRecyclerViewItemClickListener mOnItemClickListemer = null;

    public LostAdapter(Context context, ArrayList<Lost> lostDatas) {
        this.lostDatas = lostDatas;
        mInflater = LayoutInflater.from(context);
    }

    public static interface OnRecyclerViewItemClickListener {
        Void OnItemClickListener(View view, String data);
    }


//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_lost_item, viewGroup, false);
//        //view.setOnClickListener(this);
//        return new MyViewHolder(view);
//    }

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
        if (lostDatas.get(position).getStatus() == 0) {
            return FOOT_VIEW;
        } else if (lostDatas.get(position).getStatus() == 1) {
            return ITEM_VIEW;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).tv_describe.setText(lostDatas.get(position).getDescribe());
            ((ItemViewHolder) holder).tv_phone.setText(lostDatas.get(position).getPhone());
			((ItemViewHolder)holder).tv_name.setText(lostDatas.get(position).getPublisher().getUsername());
            ((ItemViewHolder) holder).tv_title.setText(lostDatas.get(position).getTitle());
            ((ItemViewHolder) holder).tv_time.setText(lostDatas.get(position).getUpdatedAt());
        } else if (holder instanceof MyViewHolder) {
            ((MyViewHolder) holder).tv_describe.setText(lostDatas.get(position).getDescribe());
            ((MyViewHolder) holder).tv_phone.setText(lostDatas.get(position).getPhone());
            ((MyViewHolder) holder).tv_title.setText(lostDatas.get(position).getTitle());
			((MyViewHolder)holder).tv_name.setText(lostDatas.get(position).getPublisher().getUsername());
            ((MyViewHolder) holder).tv_time.setText(lostDatas.get(position).getUpdatedAt());
            Uri uri = Uri.parse(lostDatas.get(position).getimageURL());
            ((MyViewHolder) holder).draweeView.setImageURI(uri);

        }
    }

//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int pos) {
//
//        holder.tv_describe.setText(lostDatas.get(pos).getDescribe());
//        holder.tv_phone.setText(lostDatas.get(pos).getPhone());
//        holder.tv_title.setText(lostDatas.get(pos).getTitle());
//        holder.tv_time.setText(lostDatas.get(pos).getUpdatedAt());
//        if (lostDatas.get(pos).getimageURL() != null) {
//            Uri uri = Uri.parse(lostDatas.get(pos).getimageURL());
//            holder.draweeView.setImageURI(uri);
//        } else return;
//        holder.itemView.setTag(lostDatas.get(pos));
//    }

    @Override
    public int getItemCount() {
        return lostDatas == null ? 0 : lostDatas.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListemer = listener;
    }

    public void refreshDatas() {
        lostDatas.clear();
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
