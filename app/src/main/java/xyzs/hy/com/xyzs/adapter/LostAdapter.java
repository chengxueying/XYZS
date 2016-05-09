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


public class LostAdapter extends RecyclerView.Adapter<LostAdapter.MyViewHolder> {

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

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_lost_item, viewGroup, false);
        //view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos) {

        holder.tv_describe.setText(lostDatas.get(pos).getDescribe());
        holder.tv_phone.setText(lostDatas.get(pos).getPhone());
        holder.tv_title.setText(lostDatas.get(pos).getTitle());
        holder.tv_time.setText(lostDatas.get(pos).getUpdatedAt());
        if (lostDatas.get(pos).getimageURL() != null) {
            Uri uri = Uri.parse(lostDatas.get(pos).getimageURL());
            holder.draweeView.setImageURI(uri);
        } else return;
        holder.itemView.setTag(lostDatas.get(pos));
    }

    @Override
    public int getItemCount() {
        return lostDatas.size();
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListemer = listener;
    }

    public void refreshDatas() {
        lostDatas.clear();
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time;
        TextView tv_title;
        TextView tv_describe;
        TextView tv_phone;
        SimpleDraweeView draweeView;

        public MyViewHolder(View arg0) {
            super(arg0);
            draweeView = (SimpleDraweeView) arg0.findViewById(R.id.iv_lost);
            tv_phone = (TextView) arg0.findViewById(R.id.tv_phone);
            tv_title = (TextView) arg0.findViewById(R.id.tv_title);
            tv_time = (TextView) arg0.findViewById(R.id.tv_time);
            tv_describe = (TextView) arg0.findViewById(R.id.tv_describe);
        }
    }
}
