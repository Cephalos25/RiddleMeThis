package com.example.riddlemethis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class DiscoveredRiddlesAdapter extends ArrayAdapter<Riddle> {

    private List<Riddle> riddles;
    private List<Riddle> savedRiddles;
    private Context context;

    private static class ViewHolder {
        TextView nameField;
        ImageView imageView;
    }

    public DiscoveredRiddlesAdapter(@NonNull Context context, int resource, @NonNull List<Riddle> objects,
                                    @NonNull List<Riddle> objectsCompare) {
        super(context, resource, objects);
        this.context = context;
        riddles = objects;
        savedRiddles = objectsCompare;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Riddle riddle = riddles.get(position);
        boolean isSaved = savedRiddles.contains(riddle);

        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_discoverriddles_riddle,
                    parent, false);
            viewHolder.nameField = convertView.findViewById(R.id.textView_discoveredriddleitem_name);
            viewHolder.imageView = convertView.findViewById(R.id.imageView_discoverriddleitem_savedstar);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nameField.setText(riddle.getName());
        if(isSaved) {
            viewHolder.imageView.setImageResource(R.drawable.star_filled);
        } else {
            viewHolder.imageView.setImageResource(R.drawable.star_empty);
        }
        convertView.setTag(viewHolder);

        return convertView;
    }
}
