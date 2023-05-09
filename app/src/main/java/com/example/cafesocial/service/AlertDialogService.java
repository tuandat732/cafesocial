package com.example.cafesocial.service;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import com.example.cafesocial.utils.Command;

public class AlertDialogService {
    public static void openAlertDialog(Context context, String title, String body, Command commandNo , Command commandYes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(body);
        if(commandNo != null) {
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    commandNo.execute();
                }
            });
        }
        if(commandYes != null) {
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    commandYes.execute();
                }
            });
        }
        builder.create().show();
    }
}
