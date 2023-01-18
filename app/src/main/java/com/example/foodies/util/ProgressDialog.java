package com.example.foodies.util;

import android.content.Context;

import com.example.foodies.R;

public class ProgressDialog {

    private static android.app.ProgressDialog mProgressDialog;

    public static void showProgressDialog(Context context, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new android.app.ProgressDialog(context);
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
