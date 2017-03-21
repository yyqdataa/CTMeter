package com.yanyuqi.ctmeter.adapter;

import android.content.Context;
import android.system.Os;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yanyuqi.ctmeter.R;

import java.io.File;
import java.util.List;

/**
 * Created by yanyuqi on 2017/3/21.
 */

public class ReportListAdapter extends BaseAdapter {
    private final boolean[] checks;
    private List<File> list;
    private LayoutInflater inflater;

    public ReportListAdapter(Context context,List<File> list) {
        this.list = list;
        inflater = LayoutInflater.from(context);
        checks = new boolean[list.size()];
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int position) {
        return list!=null?list.get(position):null;
    }

    @Override
    public long getItemId(int position) {
        return list!=null?position:0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.report_list_item,parent,false);
            viewHolder = new ViewHolder(convertView);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    for (int i = 0; i < checks.length; i++) {
                        checks[i] = false;
                    }
                    checks[position] = true;
                }
                notifyDataSetChanged();
            }
        });

        viewHolder.checkBox.setChecked(checks[position]);
        viewHolder.title.setText(list.get(position).getName());
        return convertView;
    }

    static class ViewHolder{
        CheckBox checkBox;
        TextView title;

        public ViewHolder(View view) {
            checkBox = (CheckBox) view.findViewById(R.id.report_lv_check);
            title = (TextView) view.findViewById(R.id.report_lv_text);
            view.setTag(this);
        }
    }
}
