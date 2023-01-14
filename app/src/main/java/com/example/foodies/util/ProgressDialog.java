package com.example.foodies.util;

import com.example.foodies.R;

public class ProgressDialog {

    private static android.app.ProgressDialog mProgressDialog;

    public static void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new android.app.ProgressDialog(mProgressDialog.getContext());
            mProgressDialog.setMessage(getString(R.string.loading));
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
