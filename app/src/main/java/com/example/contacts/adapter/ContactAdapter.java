package com.example.contacts.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.R;
import com.example.contacts.model.ContactListModel;
import com.example.contacts.model.ContactsModel;
import com.example.contacts.model.Note;
import com.example.contacts.ui.HomeActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private Context context;
   // private ArrayList<ContactsModel.Contact> listfriOrigin;
   // ArrayList<ContactsModel.Contact> contactsList = null;

    private ArrayList<ContactListModel> listfriOrigin;
    ArrayList<ContactListModel> contactsList = null;
    private onItemClickListner listner;

    public ContactAdapter(WeakReference<Context> context, ArrayList<ContactListModel> contactsList) {
        this.context = context.get();
        this.contactsList = contactsList;
        this.listfriOrigin = new ArrayList<ContactListModel>();
        this.listfriOrigin.addAll(contactsList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    public void setNotes(ArrayList<ContactListModel> contacts) {
        this.contactsList = contacts;
        notifyDataSetChanged();
    }

    // Filter Class
    public void filters(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        contactsList.clear();
        if (charText.length() == 0) {
            contactsList.addAll(listfriOrigin);
        } else {
            for (ContactListModel  wp : listfriOrigin) {
                if (wp.getName().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getName().toLowerCase(Locale.getDefault()).contains(charText) ||
                        wp.getEmail().toLowerCase(Locale.getDefault()).contains(charText)) {
                    contactsList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView name,email,address,gender,mobile;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textviewName);
            email = itemView.findViewById(R.id.textviewEmail);
            mobile = itemView.findViewById(R.id.textviewMobile);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listner != null && position != RecyclerView.NO_POSITION) {
                        listner.onItemClick(contactsList.get(position));
                    }
                }
            });
        }

        public void bind(int position) {
            name.setText(contactsList.get(position).getName());
            email.setText(contactsList.get(position).getEmail());
            mobile.setText(contactsList.get(position).getMobile());

        }
    }

    public ContactListModel getNoteAt(int position) {
        return contactsList.get(position);
    }



    public interface onItemClickListner {
        void onItemClick(ContactListModel contacts);
    }

    public void setOnItemClickListner(onItemClickListner listner) {
        this.listner = listner;
    }
}
