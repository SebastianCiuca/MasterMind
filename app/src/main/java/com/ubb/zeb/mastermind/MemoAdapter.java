package com.ubb.zeb.mastermind;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.MemoViewHolder>{

    /*
        MemoViewHolder inner class.
     */
    class MemoViewHolder extends RecyclerView.ViewHolder{

        //reference to the 2 items we are going to change
        EditText memoTextTitle, memoTextMemo;

        public MemoViewHolder(View itemView) {
            super(itemView);
            memoTextTitle = itemView.findViewById(R.id.memoTextTitle);
            memoTextMemo = itemView.findViewById(R.id.memoTextMemo);
        }
    }

    /*
        Members.
     */
    private static final String TAG = "sebiMessage";
    private Context ctx;
    private List<Memo> memoList;
    private DatabaseHelper dbHelper;

    /*
        Constructor.
     */
    public MemoAdapter(Context ctx, List<Memo> memoList, DatabaseHelper dbHelper) {
        this.ctx = ctx;
        this.memoList = memoList;
        this.dbHelper = dbHelper;
    }

    @Override
    public MemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater memoLayoutInflater = LayoutInflater.from(ctx);
        //View view = memoLayoutInflater.inflate(R.layout.activity_see_memos,null); //id of layout we want to inflate; parent
        View view = memoLayoutInflater.inflate(R.layout.memo,null); //id of layout we want to inflate; parent
        return new MemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemoViewHolder holder, final int position) {
        //ar trebui sa se apeleze inca o data cand schimbi
        //ar trebui sa existe on refresh
        Memo memo = memoList.get(position);

        holder.memoTextTitle.setText(memo.getMemoTitle());
        holder.memoTextMemo.setText(memo.getMemoText());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                                editMemoForm(memoList.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public void editMemoForm(final Memo oldMemo, final int index){

        final Dialog form = new Dialog(ctx);
        form.setTitle("Edit Memo");
        form.setContentView(R.layout.edit_memo_view);

        final EditText memoTitle = (EditText) form.findViewById(R.id.editMemoEditTextTitle); //final?
        final EditText memoText= (EditText) form.findViewById(R.id.editMemoEditTextText);

        memoTitle.setText(oldMemo.getMemoTitle());
        memoText.setText(oldMemo.getMemoText());

        Button saveButton = (Button) form.findViewById(R.id.buttonSave);
        Button discardButton = (Button) form.findViewById(R.id.buttonDiscard);
        final Button deleteButton = (Button) form.findViewById(R.id.buttonDelete);

        saveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //create Memo with new data
                        Memo newMemo = new Memo(memoTitle.getText().toString(),
                                memoText.getText().toString());

                        memoList.set(index,newMemo);
                        MemoAdapter.super.notifyDataSetChanged();

                        int itemID = dbHelper.extractMemoID(oldMemo);

                        if (itemID > -1) {
                            Log.d(TAG, "Edit -> update button: The ID is: " + itemID);
                            dbHelper.updateMemo(itemID,newMemo,oldMemo);
                            MemoAdapter.super.notifyDataSetChanged();
                        }
                        else
                            itemID = itemID;//toastMessage -> "No ID associated with that Memo"

                        //toasterMessage(getApplication(),"Memo successfully saved!");
                        openMail();
                        form.dismiss();
                    }
                }
        );

        discardButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //toasterMessage(getApplication(),"Memo update discarded!");
                        form.dismiss();
                    }
                }
        );

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //New Yes/No dialog
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                memoList.remove(index);
                                MemoAdapter.super.notifyDataSetChanged();

                                int itemID = dbHelper.extractMemoID(oldMemo);

                                if (itemID > -1) {
                                    dbHelper.deleteMemo(itemID);
                                    MemoAdapter.super.notifyDataSetChanged();
                                }
                                else
                                    itemID = itemID;//toastMessage -> "No ID associated with that Memo"

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                //toastMessage
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

                form.dismiss();
            }
        });

        form.show();
    }

    public void openMail(){
        Intent openGmail = new Intent(Intent.ACTION_SEND);
        openGmail.setData(Uri.parse("mailto:"));
        //String[] sendTo = ("sebastian.s.ciuca@gmail.com");
        //openGmail.putExtra(Intent.EXTRA_EMAIL,sendTo);
        openGmail.putExtra(Intent.EXTRA_SUBJECT,"Memo Updated");
        openGmail.putExtra(Intent.EXTRA_TEXT,"Hello!");
        openGmail.setType("message/rfc822");//for putExtra ~ specification for email
        Intent chooser = openGmail.createChooser(openGmail,"Send Email");
        ctx.startActivity(chooser);
    }
}
