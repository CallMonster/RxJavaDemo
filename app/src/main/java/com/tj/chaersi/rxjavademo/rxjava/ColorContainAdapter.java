package com.tj.chaersi.rxjavademo.rxjava;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tj.chaersi.rxjavademo.R;

import java.util.List;

/**
 * Created by Chaersi on 17/5/10.
 */

public class ColorContainAdapter extends BaseAdapter {

    private Context context;
    private List<String> colorArr;
    public ColorContainAdapter(Context context,List<String> colorArr){
        this.context=context;
        this.colorArr=colorArr;
    }

    @Override
    public int getCount() {
        return colorArr.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_colorcontain, null,false);
            holder=new ViewHolder();
            holder.itemTitle= (TextView) convertView.findViewById(R.id.title);
            holder.itemColorAction=(ImageView) convertView.findViewById(R.id.actionColor);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder) convertView.getTag();
        }

        holder.itemTitle.setText(colorArr.get(position));
        holder.itemColorAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(position,colorArr.get(position));
            }
        });
        return convertView;
    }

    private class ViewHolder{
        TextView itemTitle;
        ImageView itemColorAction;
    }


    public interface OnAdapterListenerImpl{void onItemClickListener(int position,String content);}
    private OnAdapterListenerImpl listener;
    public void addOnItemClickListener(OnAdapterListenerImpl listener){
        this.listener=listener;
    }

}
