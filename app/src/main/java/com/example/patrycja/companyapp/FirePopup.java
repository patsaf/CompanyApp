package com.example.patrycja.companyapp;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class FirePopup {

    private Button confirm;
    private Button cancel;
    private TextView message;
    private Runnable ansTrue;

    public void display(final Activity activity, Runnable true_poc, String title) {

        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_layout);

        ansTrue = true_poc;

        confirm = dialog.findViewById(R.id.confirm);
        cancel = dialog.findViewById(R.id.cancel_firing);
        message = dialog.findViewById(R.id.message);

        message.setText("Are you sure you want to fire " + title + "?");

        confirm.setOnClickListener(view -> {
            ansTrue.run();
            dialog.dismiss();
        });

        cancel.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }
}
