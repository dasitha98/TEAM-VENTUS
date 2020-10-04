package com.example.project.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.data.DatabaseHandler;
import com.example.project.model.item;

import java.text.MessageFormat;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<item> itemList;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;


    public RecyclerViewAdapter(Context context,List<item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_row, viewGroup, false);


        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder viewholder, int position) {

        item item = itemList.get(position);
        viewholder.itemName.setText(MessageFormat.format("Book Name: {0}", item.getBookname()));
        viewholder.itemPrice.setText(MessageFormat.format("Book price: {0}", String.valueOf(item.getBookprice())));
        viewholder.itemAuthor.setText(MessageFormat.format("Book Author: {0}", item.getBookAuthor()));
        viewholder.dateAdded.setText(MessageFormat.format("Added On: {0}", item.getDateItemAdded()));


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView itemName;
        public TextView itemPrice;
        public TextView itemAuthor;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;


        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context = ctx;

            itemName = itemView.findViewById(R.id.book_item_name);
            itemPrice = itemView.findViewById(R.id.book_item_price);
            itemAuthor = itemView.findViewById(R.id.book_item_Author);
            dateAdded = itemView.findViewById(R.id.book_item_date);

            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
        }

        public void onClick(View v) {

            int position;
            position = getAdapterPosition();
            item item = itemList.get(position);
            switch (v.getId()) {
                case R.id.editButton:
                    //edit item

                    editItem(item);
                    break;
                case R.id.deleteButton:
                    //delete item
                    deleteItem(item.getId());
                    break;
            }

        }

        private void deleteItem(final int id){

            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.confirmation_pop,null);

            Button noButton = view.findViewById(R.id.conf_no_button);
            Button yesButton = view.findViewById(R.id.conf_yes_button);

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHandler db = new DatabaseHandler(context);
                    db.deleteItem(id);
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    dialog.dismiss();

                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });


        }

        private void editItem(final item newitem) {


            builder = new AlertDialog.Builder(context);
            inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.popup,null);

            Button saveButton;
            final EditText bookname;
            final EditText bookprice;
            final EditText bookauthor;
            TextView title;

            bookname = view.findViewById(R.id.Book_name);
            bookprice = view.findViewById(R.id.Book_price);
            bookauthor = view.findViewById(R.id.Book_author);
            saveButton = view.findViewById(R.id.saveButton);
            saveButton.setText(R.string.update_text);
            title = view.findViewById(R.id.title);

            title.setText(R.string.edit_item);
            bookname.setText(newitem.getBookname());
            bookprice.setText(String.valueOf(newitem.getBookprice()));
            bookauthor.setText(newitem.getBookAuthor());

            builder.setView(view);
            dialog = builder.create();
            dialog.show();
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //update
                    DatabaseHandler databaseHandler = new DatabaseHandler(context);
                    newitem.setBookAuthor(bookauthor.getText().toString());
                    newitem.setBookname(bookname.getText().toString());
                    newitem.setBookprice(Integer.parseInt(bookprice.getText().toString()));

                    databaseHandler.updateItem(newitem);
                    notifyItemChanged(getAdapterPosition(),newitem);
                    dialog.dismiss();
                }
            });
        }

    }



}



