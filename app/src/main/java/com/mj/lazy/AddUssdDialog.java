package com.mj.lazy;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Frank on 3/29/2016.
 *
 */
public class AddUssdDialog {


    private final AlertDialog dialog;

    public AddUssdDialog(final Activity activity, final Dismissable dismissable) {

        final View root = activity.getLayoutInflater().inflate(R.layout.layout_add_ussd_dialog, null);
        dialog = new AlertDialog.Builder(activity).setView(root).create();


        DopeTextView btnSave = (DopeTextView) root.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save new ussd
                String us_name = ((EditText)(root.findViewById(R.id.us_name))).getText().toString();
                String us_code = ((EditText)(root.findViewById(R.id.us_code))).getText().toString();

                if (!us_name.isEmpty() && !us_code.isEmpty()) {
                    dismissable.addCode(new UssdCode(us_name, us_code, 0));//new hence freq = 0
                }

                dialog.dismiss();
            }
        });

    }

    public void show() {
        dialog.show();
    }
}
