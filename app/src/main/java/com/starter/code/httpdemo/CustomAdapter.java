package com.starter.code.httpdemo;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Album> implements View.OnClickListener{

    private List<Album> albumArrayList;
    private Context mContext;
    private int lastPosition = -1;

    // View lookup cache
    private static class ViewHolder {
        TextView title;
        ImageView info;
    }

    public CustomAdapter(List<Album> data, Context context) {
        super(context, R.layout.row_item, data);
        this.albumArrayList = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {
        // Intentionally left blank
        // Onclick not implemented
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Getting the record based on position
        Album album = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.imageView);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        lastPosition = position;
        // Setting the title of the photo
        viewHolder.title.setText(album.getTitle());

        // Rendering the photo on the screen
        Picasso.get().load(album.getUrl()).into(viewHolder.info);

        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}

