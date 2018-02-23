package com.example.patrycja.companyapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class FirePopup {

    private Button confirm;
    private Button cancel;
    private Runnable ans_true;

    public void display(final Activity activity, Runnable true_poc) {

        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_layout);

        ans_true = true_poc;

        confirm = dialog.findViewById(R.id.confirm);
        cancel = dialog.findViewById(R.id.cancel_firing);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ans_true.run();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
