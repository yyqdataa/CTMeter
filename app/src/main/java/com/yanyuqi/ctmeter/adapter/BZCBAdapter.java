package com.yanyuqi.ctmeter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.bean.LCQXBean;

import java.util.List;

/**
 * Created by yanyuqi on 2017/2/26.
 */

public class BZCBAdapter extends BaseAdapter {

    private List<LCQXBean> list;
    private LayoutInflater inflater;

    public BZCBAdapter(Context context, List<LCQXBean> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list == null ? 0 : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.data_item2,parent,false);
            viewHolder = new ViewHolder(convertView);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textNo.setText(list.get(position).getE_No());
        viewHolder.textOne.setText(list.get(position).getE_fu()+"");
        viewHolder.textTwo.setText(list.get(position).getE_fi()+"");
        viewHolder.textThree.setText(list.get(position).getE_fUc()+"");
        viewHolder.textFour.setText(list.get(position).getE_fA()+"");
        viewHolder.textFive.setText(list.get(position).getE_fIpe()+"");

        return convertView;
    }

    static class ViewHolder{
        TextView textNo;
        TextView textOne;
        TextView textTwo;
        TextView textThree;
        TextView textFour;
        TextView textFive;

        public ViewHolder(View view){
            textNo = (TextView) view.findViewById(R.id.tv_no);
            textOne = (TextView) view.findViewById(R.id.tv_one);
            textTwo = (TextView) view.findViewById(R.id.tv_two);
            textThree = (TextView) view.findViewById(R.id.tv_three);
            textFour = (TextView) view.findViewById(R.id.tv_four);
            textFive = (TextView) view.findViewById(R.id.tv_five);
            view.setTag(this);
        }
    }
}
