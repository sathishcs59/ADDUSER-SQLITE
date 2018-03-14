package mine.manik.com.dbsample.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mine.manik.com.dbsample.Pojo.Emp;
import mine.manik.com.dbsample.R;

/**
 * Created by manikkam on 1/2/18.
 */

public class EmpAdapter extends RecyclerView.Adapter<EmpAdapter.MyViewHolder> {

    Context mContext;
    ArrayList<Emp> mUserList;
    OnClickListener onClickListener;

    public interface OnClickListener {
        void onLayoutClick(int position);
    }

    public EmpAdapter(Context context, ArrayList<Emp> userList) {
        mContext = context;
        mUserList = userList;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setUserList(ArrayList<Emp> userList) {
        mUserList = userList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.list_emp, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Emp users=mUserList.get(position);
        holder.tvName.setText(users.getName());
        holder.tvRole.setText(users.getDep());
        holder.tvMobile.setText(users.getMobile());


    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout mLayout;
        TextView tvName,tvRole,tvMobile;

        public MyViewHolder(View view) {
            super(view);

            mLayout= (LinearLayout) view.findViewById(R.id.list_user_layout);
            tvName= (TextView) view.findViewById(R.id.list_user_name);
            tvRole= (TextView) view.findViewById(R.id.list_user_role);
            tvMobile= (TextView) view.findViewById(R.id.list_user_mobile);

            mLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(onClickListener!=null)
                        onClickListener.onLayoutClick(getAdapterPosition());
                    return false;
                }
            });

        }
    }
}
