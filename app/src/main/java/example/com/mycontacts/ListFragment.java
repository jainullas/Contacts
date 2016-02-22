package example.com.mycontacts;

/**
 * Created by ullasjain on 2/15/16.
 */

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ListFragment extends android.support.v4.app.Fragment {
    DatabaseHandler db = new DatabaseHandler(getActivity());
    String LOG = "from CONTACTS";
    int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    MyAdapter mAdapter;
    List<Contact> contacts;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment

        ThreadPoolExecutor executor;
        executor = new ThreadPoolExecutor(NUMBER_OF_CORES * 2, NUMBER_OF_CORES * 2, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                newFetch();
            }
        });


        mRecyclerView = (RecyclerView) getView().findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Log.d("Insert: ", "Inserting ..");
        Log.d("Reading: ", "Reading all contacts..");
        contacts = db.getAllContacts();

        for (Contact cn : contacts) {
            String log = "Id: " + cn.getID() + " ,Name: " + cn.getName() + " ,Phone: " + cn.getPhoneNumber() + " ,Email: " + cn.get_email();
            // Writing Contacts to log
            Log.d("Name: ", log);

        }
        mAdapter = new MyAdapter(contacts);
        mRecyclerView.setAdapter(mAdapter);
        return inflater.inflate(R.layout.fragment_list, container, false);
    }


    public void newFetch() {
        Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String email = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.d(LOG, name + " " + phoneNumber);
            db.addContact(new Contact(name, phoneNumber, email));
        }
        phones.close();
    }


}