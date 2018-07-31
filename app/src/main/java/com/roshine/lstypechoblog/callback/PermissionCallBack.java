package com.roshine.lstypechoblog.callback;

import com.roshine.lspermission.interfaces.Rationale;

public interface PermissionCallBack {

    void onRationShow(int requestCode, Rationale rationale);
    void onRequestSuccess(int requestCode, String[] permissions);
    void onRequestFail(int requestCode, String[] permissions);
}
