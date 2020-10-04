package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ModelAdaptersupp extends ArrayAdapter<Modelsupplier> {

    private Context context;
    private int resource;
    List<Modelsupplier> modelsuppliers;

    ModelAdaptersupp(@NonNull Context context, int resource, List<Modelsupplier> modelsuppliers) {
        super(context, resource, modelsuppliers);
        this.context = context;
        this.resource = resource;
        this.modelsuppliers = modelsuppliers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(resource, parent, false);

        TextView suppliername = row.findViewById(R.id.suppliername);
        TextView supplierdescription = row.findViewById(R.id.supplierdescription);

        Modelsupplier modelsupplier = modelsuppliers.get(position);
        suppliername.setText(modelsupplier.getSuppliername());
        supplierdescription.setText(modelsupplier.getSupplierdescription());

        return  row;
    }


}
