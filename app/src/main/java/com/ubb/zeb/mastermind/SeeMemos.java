package com.ubb.zeb.mastermind;

import android.app.Dialog;
import android.database.Cursor;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class SeeMemos extends AppCompatActivity {

    //custom debugging Sebi tag
    private static final String TAG = "Sebi - See Memos";

    //List, adapter
    ArrayList<Memo> memoList = new ArrayList<>();
    RecyclerView memoRecyclerView;
    MemoAdapter memoAdapter;

    //Database
    DatabaseHelper dbHelper;

    //AddMemo button
    Button btnAddMemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_memos);
        Log.i(TAG, "onCreate");

        //instanciate Database Helper
        dbHelper = new DatabaseHelper(this);

        //instanciate Memo adapter
        memoAdapter = new MemoAdapter(this,memoList,dbHelper);

        //refer RecyclerView
        memoRecyclerView  = (RecyclerView) findViewById(R.id.recyclerViewSeeMemos);
        memoRecyclerView.setHasFixedSize(true);
        memoRecyclerView.setLayoutManager(new LinearLayoutManager(this)); //vertical layout

        //set Memo Adapter
        memoRecyclerView.setAdapter(memoAdapter);


        //populate List with Memos
        populateRecyclerView();
        memoAdapter.notifyDataSetChanged();

        //AddMemo button
        btnAddMemo = (Button) findViewById(R.id.AddMemo);

        btnAddMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMemoForm();
            }
        });
    }

    public void addMemo(Memo newMemo){
        boolean insertData = dbHelper.addData(newMemo);

        if (insertData)
            toastMessage("Memo successfully added!");
        else
            toastMessage("Something went wrong!");
    }

    public void addMemoForm(){

        final Dialog form = new Dialog(this);
        form.setTitle("Add Memo");
        form.setContentView(R.layout.add_memo_view);

        //Text insertion
        final EditText addMemoTitle = (EditText) form.findViewById(R.id.add_edittext_title); //final?
        final EditText addMemoText= (EditText) form.findViewById(R.id.add_edittext_memotext);

        //Add & Discard buttons
        Button saveButton = (Button) form.findViewById(R.id.addButtonSave);
        Button discardButton = (Button) form.findViewById(R.id.addButtonDiscard);

        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Memo newMemo = new Memo(addMemoTitle.getText().toString(),
                                addMemoText.getText().toString());

                        if (addMemoText.getText().toString().isEmpty() &&
                                addMemoTitle.getText().toString().isEmpty())
                            toastMessage("Cannot leave both fields empty! \nPlease insert something...");
                        else {
                            //add new Memo in list
                            memoList.add(newMemo);

                            //add also in DB
                            addMemo(newMemo);

                            //alert adapter that a new item was added
                            memoAdapter.notifyDataSetChanged();
                        }

                        form.dismiss();
                    }
                }
        );

        discardButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toastMessage("Aborted adding new Memo...");
                        form.dismiss();
                    }
                }
        );

        form.show();
    }

    public void populateRecyclerView(){
        Log.d(TAG," populateListView: fetching data from DB & displaying it");

        //fetch data from DB
        Cursor data = dbHelper.getData();
        Log.d(TAG," populateListView: got cursor data");
        while (data.moveToNext()){
            memoList.add(new Memo(data.getString(1),data.getString(2)));
        }

        /*//instanciate Memo Adapter
        memoAdapter = new MemoAdapter(this,listData);*/

        data.close();
    }


    /*
            UTILS
     */

    /**
     * Customizable toast.
     * @param message
     */
    public void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private ArrayList<Memo> hardcodededMemos(){
        Memo m1 = new Memo("ToDo1", "Wash car");
        Memo m2 = new Memo("Song", "I've never felt so right");
        Memo m3 = new Memo("HomeWork Mobile", "Use List Adapter");
        Memo m4 = new Memo("HipHop Choreography", "Better watch yourself / BoyTroy - mashup");
        Memo m5 = new Memo("Alarm", "Set at 1 o' clock");
        Memo m6 = new Memo("Jump", "Ride the pony");

        return new ArrayList<>(Arrays.asList(m1,m2,m3,m4,m5,m6));
    }
}






















