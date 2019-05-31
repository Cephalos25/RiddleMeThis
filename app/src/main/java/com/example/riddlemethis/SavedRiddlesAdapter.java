package com.example.riddlemethis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SavedRiddlesAdapter extends ArrayAdapter<Riddle> {

    private List<Riddle> riddles;
    private Context ctx;

    private static class ViewHolder {
        TextView textView;
    }

    public SavedRiddlesAdapter(@NonNull Context context, @NonNull List<Riddle> objects) {
        super(context, R.layout.listitem_savedriddles_riddle, objects);
        riddles = objects;
        ctx = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Riddle riddle = getItem(position);

        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.listitem_savedriddles_riddle, parent,
                    false);
            viewHolder.textView=convertView.findViewById(R.id.textView_riddleitem_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(riddle.getName());

        return convertView;
    }
}
