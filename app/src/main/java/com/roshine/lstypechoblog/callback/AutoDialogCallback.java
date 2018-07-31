package com.roshine.lstypechoblog.callback;

import android.content.DialogInterface;

public interface AutoDialogCallback {

    void onSureClick(DialogInterface dialogInterface, int i, int dialogCode);
    void onCancelClick(DialogInterface dialogInterface, int i, int dialogCode);
}
