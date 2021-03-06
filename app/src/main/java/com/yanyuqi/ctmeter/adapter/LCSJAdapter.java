package com.yanyuqi.ctmeter.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yanyuqi.ctmeter.R;
import com.yanyuqi.ctmeter.bean.LCQXBean;

import java.util.List;

/**
 * Created by yanyuqi on 2017/2/25.
 */

public class LCSJAdapter extends BaseAdapter {
    private List<LCQXBean> list;
    private LayoutInflater inflater;

    public LCSJAdapter(Context context, List<LCQXBean> list) {
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
        int color;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.data_item, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        FF8F4A04
        if (position % 2 == 0) {
            color = Color.parseColor("#00000000");
        } else {
            color = Color.parseColor("#FF8F4A04");
        }
        viewHolder.textId.setText(list.get(position).getE_No() + "");
        viewHolder.textId.setBackgroundColor(color);
        viewHolder.textOne.setText(list.get(position).getE_fu() + "");
        viewHolder.textOne.setBackgroundColor(color);
        viewHolder.textTwo.setText(list.get(position).getE_fi() + "");
        viewHolder.textTwo.setBackgroundColor(color);
        return convertView;
    }

    static class ViewHolder {
        TextView textId;
        TextView textOne;
        TextView textTwo;

        public ViewHolder(View view) {
            textId = (TextView) view.findViewById(R.id.text_one);
            textOne = (TextView) view.findViewById(R.id.text_two);
            textTwo = (TextView) view.findViewById(R.id.text_three);
            view.setTag(this);
        }
    }
}
