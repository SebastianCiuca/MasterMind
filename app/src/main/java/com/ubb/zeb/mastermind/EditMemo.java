/*
package com.ubb.zeb.mastermind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditMemo extends AppCompatActivity {

    Memo memoToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memo);

        final EditText editMemoTitle = (EditText) this.findViewById(R.id.editMemoView).findViewById(R.id.editMemoEditTextTitle);
        final EditText editMemoMemo = (EditText) this.findViewById(R.id.editMemoView).findViewById(R.id.editMemoEditTextText);

        Bundle passedMemo = getIntent().getExtras();

        if (passedMemo == null)
            return; //try with finish();
        else
            memoToEdit = passedMemo.

        editMemoTitle.setText(currentMemo.getMemoTitle());
        editMemoMemo.setText(currentMemo.getMemoText());

        */
/*final Toast t = new Toast(this);
        t.setDuration(Toast.LENGTH_LONG);*//*


        Button save = (Button) this.findViewById(R.id.editMemoView).findViewById(R.id.buttonSave);
        Button discard = (Button) this.findViewById(R.id.editMemoView).findViewById(R.id.buttonDiscard);

        save.setOnClickListener(
                new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        String newTitle = String.valueOf(editMemoTitle.getText());
                        String newText = String.valueOf(editMemoMemo.getText());

                        editMemoTitle.setText(newTitle);
                        editMemoMemo.setText(newText);

                        Toast t = new Toast(getParent()); // crapa?!

                        t.setText("Successfully saved!");
                        t.setDuration(Toast.LENGTH_LONG);
                        t.show();

                        finish();
                    }
                }
        );

        discard.setOnClickListener(
                new Button.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        editMemoTitle.setText(currentMemo.getMemoTitle());
                        editMemoMemo.setText(currentMemo.getMemoText());

                        Toast t = new Toast(getParent()); // crapa?!

                        t.setText("Discarded changes!");
                        t.setDuration(Toast.LENGTH_LONG);
                        t.show();

                        finish();
                    }
                }
        );
    }
}
*/