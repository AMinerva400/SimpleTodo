package minerva.anthony.simpletodo;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AddItemFragment.AddItemListener, EditItemFragment.EditItemListener {
    //Handles Adding Item - From DialogFragment (Add)
    @Override
    public void addItem(Item item) {
        dataAdapter.add(item);
        insertItem(item);
        Toast.makeText(this, "Item Successfully Added", Toast.LENGTH_SHORT).show();
    }
    //Handles Editing an Item - From DialogFragment (Edit)
    @Override
    public void editItem(Item item, int position) {
        dataSet.set(position, item);
        dataAdapter.notifyDataSetChanged();
        updateItem(item);
        Toast.makeText(this, "Item Successfully Edited", Toast.LENGTH_SHORT).show();
    }
    //Custom Adapter to Display Items
    class ItemAdapter extends ArrayAdapter<Item> {

        public ItemAdapter(Context context, ArrayList<Item> items){
            super(context, 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Item i = getItem(position);
            if(convertView == null){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
            }
            TextView tvItemDesc = convertView.findViewById(R.id.tvItemDesc);
            TextView tvDueDate = convertView.findViewById(R.id.tvDueDate);
            tvItemDesc.setText(i.itemDescription);
            String due = "Due Date " + i.dueDate;
            tvDueDate.setText(due);
            return convertView;
        }
    }

    ArrayList<Item> dataSet;
    ItemAdapter dataAdapter;
    ListView lvItems;
    private static final String DATABASE_NAME = "items_db";
    private MyDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildDatabase();
        readItems();
        dataAdapter = new ItemAdapter(this, dataSet);
        lvItems = findViewById(R.id.lvItems);
        lvItems.setAdapter(dataAdapter);
        setupListViewListener();
    }
    //Load Add Item Fragment
    public void showAddItemDialog(View v){
        FragmentManager fm = getSupportFragmentManager();
        AddItemFragment addItem = AddItemFragment.newInstance();
        addItem.show(fm, "fragment_add_item");
    }
    //Load Edit Item Fragment
    private void showEditItemDialog(Item i, int position){
        FragmentManager fm = getSupportFragmentManager();
        EditItemFragment editItem = EditItemFragment.newInstance(i, position);
        editItem.show(fm, "fragment_edit_item");
    }
    //Listener for ListView to Remove Items on Long Click or Edit on Click
    private void setupListViewListener() {
        Log.i("MainActivity", "Setting up ListView Listener");
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("MainActivity", "Item Removed From ListView at: " + position);
                deleteItem(dataSet.get(position));
                dataSet.remove(position);
                dataAdapter.notifyDataSetChanged();
                return true;
            }
        });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showEditItemDialog(dataSet.get(position), position);
            }
        });
    }
    //TODO: Fix Database Functionality
    //Builds Database
    private void buildDatabase(){
        mDatabase = Room.databaseBuilder(getApplicationContext(),
                MyDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

    }
    //Updates Item in Database
    private void updateItem(final Item i){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.itemDao().updateItem(i);
            }
        });
        try{
            t.start();
            t.join();
        }catch(Exception e){
            Log.e("MainActivity", "Error Joining: " + e);
        }
    }
    //Inserts Item in Database
    private void insertItem(final Item i){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.itemDao().insertItem(i);
            }
        });
        try{
            t.start();
            t.join();
        }catch(Exception e){
            Log.e("MainActivity", "Error Joining: " + e);
        }
    }
    //Deletes Item From Database
    private void deleteItem(final Item i){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.itemDao().deleteItem(i);
            }
        });
        try{
            t.start();
            t.join();
        }catch(Exception e){
            Log.e("MainActivity", "Error Joining: " + e);
        }
    }
    //Reads Items from Database
    private void readItems(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dataSet = (ArrayList<Item>) mDatabase.itemDao().getAllItems();
                } catch (Exception e){
                    Log.e("MainActivity", "Error Reading Data: " + e);
                    dataSet = new ArrayList<Item>();
                }
            }
        });
        try{
            t.start();
            t.join();
        }catch(Exception e){
            Log.e("MainActivity", "Error Joining: " + e);
        }

    }
}
