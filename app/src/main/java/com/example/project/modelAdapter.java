package com.example.project;

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

public class modelAdapter extends ArrayAdapter<Model> {

    private Context context;
    private int resource;
    List<Model> models;

    modelAdapter(Context context, int resource, List<Model> models) {
        super(context,resource,models);
        this.context = context;
        this.resource = resource;
        this.models = models;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(resource,parent,false);

        TextView title = row.findViewById(R.id.Title);
        TextView description = row.findViewById(R.id.Description);
        ImageView imageView = row.findViewById(R.id.imageView);

        Model model = models.get(position);
        title.setText(model.getTitle());
        description.setText(model.getDescription());
        imageView.setVisibility(row.INVISIBLE);

        if(model.getFinished() > 0) {
            imageView.setVisibility(View.VISIBLE);
        }
        return row;
    }
}
