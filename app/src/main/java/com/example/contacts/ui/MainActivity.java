package com.example.contacts.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.contacts.R;
import com.example.contacts.api.ApiRequestData;
import com.example.contacts.api.RetrofitInstance;
import com.example.contacts.custom_views.CustomProgressDialog;
import com.example.contacts.databasehelper.DatabaseHandler;
import com.example.contacts.db.NoteDatabase;
import com.example.contacts.model.ContactListModel;
import com.example.contacts.model.ContactsModel;
import com.example.contacts.model.LoginResponse;
import com.example.contacts.util.NetworkStatus;
import com.example.contacts.viewmodel.LoginViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity = null;
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private CustomProgressDialog progress = null;
    private ArrayList<ContactsModel.Contact> contactList = null;

    private AppCompatTextView textviewGetData, textViewShowData;
    private DatabaseHandler dhelper;
    private ArrayList<ContactListModel> contactLists=null;
    ContactListModel contactListmodel=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dhelper = new DatabaseHandler(this);
        activity = MainActivity.this;
        textviewGetData = findViewById(R.id.textviewTaskone);
        textViewShowData = findViewById(R.id.textviewTaskTwo);
        textviewGetData.setOnClickListener(this);
        textViewShowData.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textviewTaskone:
                getData();
                break;
            case R.id.textviewTaskTwo:
                startActivity(new Intent(activity, HomeActivity.class));
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;
        }
    }


    private void getData() {
        if (NetworkStatus.getConnectivityStatusString(activity)) {

            getContactList();

        } else {

            Toast.makeText(activity, "OffLine", Toast.LENGTH_LONG).show();
        }
    }

    public void getContactList() {
        ApiRequestData service = null;
        service = RetrofitInstance.getRetrofitInstance().create(ApiRequestData.class);
        Call<ContactsModel> call = service.getContacts();
        progress = new CustomProgressDialog(activity).
                setStyle(CustomProgressDialog.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
                .show();
        call.enqueue(new Callback<ContactsModel>() {
            @Override
            public void onResponse(Call<ContactsModel> call, Response<ContactsModel> response) {

                contactList = (ArrayList<ContactsModel.Contact>) response.body().getContacts();

                for(int i=0;i<contactList.size();i++){
                    contactListmodel=new ContactListModel(contactList.get(i).getName(), contactList.get(i).getEmail(),contactList.get(i).getAddress(),contactList.get(i).getGender(),contactList.get(i).getPhone().getMobile());
                    dhelper.addContacts(contactListmodel);
                }

                for (ContactsModel.Contact result : contactList) {
                    int rowCount = dhelper.existRecord(result);
                      // NoteDatabase.getInstance(getApplicationContext()).contactDao().insert(contactListmodel);
                       // dhelper.addContacts(result);

                }

                progress.dismiss();
                Toast.makeText(activity, "Sync Data with Server", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ContactsModel> call, Throwable t) {
                progress.dismiss();

                Toast.makeText(MainActivity.this, "Sync Fail!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}