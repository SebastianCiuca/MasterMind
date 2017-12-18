package com.ubb.zeb.mastermind;

/**
 * Created by zeb on 11/11/17.
 */

public class Memo {
    String memoTitle;
    String memoText;

    public Memo() {
    }

    public Memo(String memoTitle, String memoText) {
        this.memoTitle = memoTitle;
        this.memoText = memoText;
    }

    public String getMemoTitle() {
        return memoTitle;
    }

    public String getMemoText() {
        return memoText;
    }

    public void setMemoTitle(String memoTitle) {
        this.memoTitle = memoTitle;
    }

    public void setMemoText(String memoText) {
        this.memoText = memoText;
    }

    @Override
    public String toString() {
        return "Memo{" +
                "memoTitle='" + memoTitle + '\'' +
                ", memoText='" + memoText + '\'' +
                '}';
    }
}
