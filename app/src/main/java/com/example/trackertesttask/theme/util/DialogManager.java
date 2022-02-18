package com.example.trackertesttask.theme.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.example.trackertesttask.R;
import com.example.trackertesttask.theme.util.ColorDialogCallback;

public class DialogManager {
    public static void showCustomAlertDialog(Context context, final ColorDialogCallback callback) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.select_color_window);
        final TextView pinkColor = dialog.findViewById(R.id.pinkColor);
        pinkColor.setOnClickListener(view -> {
            callback.onChosen(pinkColor.getText().toString());
            dialog.cancel();
        });
        final TextView yellowColor = dialog.findViewById(R.id.yellowColor);
        yellowColor.setOnClickListener(view -> {
            callback.onChosen(yellowColor.getText().toString());
            dialog.cancel();
        });
        final TextView greyColor = dialog.findViewById(R.id.greyColor);
        greyColor.setOnClickListener(view -> {
            callback.onChosen(greyColor.getText().toString());
            dialog.cancel();
        });
        dialog.show();
    }
}
