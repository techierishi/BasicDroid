package com.basicdroid.app.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.basicdroid.app.libs.debug.DBG;

/**
 * Created by Ajijul Mondal on 11/18/2015.
 */
public class PopUps {
    public static final void showDialog(Context _ctx, String title, String message, final DialogOkButtonClickListener listener) {

        if (null != _ctx) {
            final AlertDialog alertDialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(_ctx);
            alertDialog = builder.create();
            alertDialog.setTitle("" + title);
            alertDialog.setMessage("" + message);

            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Close", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    if (listener != null) {
                        listener.onOkClick();
                    }
                    alertDialog.dismiss();
                }

            });
            alertDialog.show();
        } else {
            DBG.d("CONTEXT", "null");
        }
    }

    public static void showToast(Context context, String s) {
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }

    public interface DialogOkButtonClickListener {
        void onOkClick();

    }

    public static final void showYesNoDialog(Context _ctx, String title, String message, final DialogYesNoButtonClickListener listener) {

        if (null != _ctx) {
            final AlertDialog alertDialog;
            AlertDialog.Builder builder = new AlertDialog.Builder(_ctx);
            alertDialog = builder.create();
            alertDialog.setTitle("" + title);
            alertDialog.setMessage("" + message);

            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    if (listener != null) {
                        listener.onNoClick();
                    }
                    alertDialog.dismiss();
                }

            });
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    if (listener != null) {
                        listener.onYesClick();
                    }
                    alertDialog.dismiss();
                }

            });
            alertDialog.show();
        } else {
            DBG.d("CONTEXT", "null");
        }


    }

    public static void optionDialog(Context _ctx,String[] option, final OnDialogOptionSelectedListener listener) {
        final AlertDialog dialog;
       /* String[] option = new String[]{"Add", "View", "Change",
                "Delete"};*/
        ArrayAdapter<String> adapter = new ArrayAdapter<>(_ctx,
                android.R.layout.select_dialog_item, option);
        AlertDialog.Builder builder = new AlertDialog.Builder(_ctx);

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (listener != null) {
                    listener.onOptionClick(which);
                }
                if (dialog != null)
                    dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


    }

    public interface DialogYesNoButtonClickListener {
        void onYesClick();

        void onNoClick();

    }


    public interface OnDialogOptionSelectedListener {

        void onOptionClick(int which);
    }

}
