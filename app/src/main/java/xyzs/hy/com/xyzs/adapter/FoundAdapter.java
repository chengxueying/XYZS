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


public class FoundAdapter extends RecyclerView.Adapter<FoundAdapter.MyViewHolder>implements View.OnClickListener {

    private LayoutInflater mInflater;
    private ArrayList<Found> lostDatas;
	private OnRecyclerViewItemClickListener mOnItemClickListemer = null;

    public FoundAdapter(Context context, ArrayList<Found> lostDatas) {
        this.lostDatas = lostDatas;
        mInflater = LayoutInflater.from(context);
    }
	
	//静态接口
	public static interface OnRecyclerViewItemClickListener{
		void onItemClick(View view,Found lostdata);
	}

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recyclerview_cardview_item, viewGroup, false);
		view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos) {
        holder.tv_describe.setText(lostDatas.get(pos).getDescribe());
        holder.tv_phone.setText(lostDatas.get(pos).getPhone());
        holder.tv_title.setText(lostDatas.get(pos).getTitle());
        holder.tv_time.setText(lostDatas.get(pos).getUpdatedAt());
		holder.tv_name.setText(lostDatas.get(pos).getPublisher().getUsername());
        if (lostDatas.get(pos).getImageURL() != null) {
            Uri uri = Uri.parse(lostDatas.get(pos).getImageURL());
            holder.draweeViewImage.setImageURI(uri);
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

	@Override
	public void onClick(View v) {
		if(mOnItemClickListemer != null) {
			//getTag方法获取数据
			mOnItemClickListemer.onItemClick(v,(Found)v.getTag());
		}
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
        private TextView tv_name;
        private SimpleDraweeView draweeViewCaptcha;
        SimpleDraweeView draweeViewImage;

        public MyViewHolder(View arg0) {
            super(arg0);
            draweeViewImage = (SimpleDraweeView) arg0.findViewById(R.id.iv_lost);
            tv_phone = (TextView) arg0.findViewById(R.id.tv_phone);
            tv_title = (TextView) arg0.findViewById(R.id.tv_title);
            tv_time = (TextView) arg0.findViewById(R.id.tv_time);
			tv_name = (TextView) arg0.findViewById(R.id.tv_name);
			draweeViewCaptcha = (SimpleDraweeView) arg0.findViewById(R.id.iv_headSculpture);
            tv_describe = (TextView) arg0.findViewById(R.id.tv_describe);
        }
    }
}
