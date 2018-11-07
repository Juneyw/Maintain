package com.yantang.juney.maintain.view;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yantang.juney.maintain.R;

public class MDialog  {
    public static ImageButton ibAdd;


    public static AlertDialog.Builder createChoiceDialog(String [] items, Context context, String title,
                                                         DialogInterface.OnClickListener onOkClickListener, DialogInterface.OnClickListener onChoiseClickListener){

        View view  = LayoutInflater.from(context).inflate(R.layout.item_dialog_title, null);
        TextView tv_title = view.findViewById(R.id.tv_title_dialog);
        ibAdd = view.findViewById(R.id.ib_add_dialog);
        tv_title.setText(title);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setCustomTitle(view);
        builder.setPositiveButton("确定", onOkClickListener);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setSingleChoiceItems(items, -1, onChoiseClickListener);


        return builder;
    }

    public static AlertDialog.Builder createEditDialog(EditText edit, String items, Context context, String title, DialogInterface.OnClickListener onOkClickListener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(edit);
        builder.setPositiveButton("确认", onOkClickListener);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder;
    }

}
