package com.roshine.lstypechoblog.mvp.presenter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.http.RxSubUtil;
import com.roshine.lstypechoblog.mvp.bean.ArticleDetailBean;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.mvp.model.EditFragmentModel;
import com.roshine.lstypechoblog.utils.LogUtil;

/**
 * @author Roshine
 * @date 2018/7/23 22:34
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class EditFragmentPresenter extends IBasePresenter<ContractUtil.IEditFragmentView> implements ContractUtil.IEditFragmentPresenter {

    private EditFragmentModel model;

    public EditFragmentPresenter(){
        model = new EditFragmentModel(this);
    }

    @Override
    public void postEditBlog(Context context,String title,String content,ArticleDetailBean articleDetailBean) {
        compositeDisposable.add(model.postEditBlog(title,content,articleDetailBean)
                .subscribeWith(new RxSubUtil<String>(compositeDisposable, context) {
                    @Override
                    protected void onSuccess(String s) {
                        try {
                            LogUtil.show("更新成功："+s);
//                            Gson gson = new Gson();
//                            ArticleDetailBean articleDetailBean = gson.fromJson(s, ArticleDetailBean.class);
                            loadSuccess(s);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                            getView().loadFail(context.getResources().getString(R.string.data_failed));
                        }
                    }

                    @Override
                    protected void onFail(String errorMsg) {
                        getView().loadFail(errorMsg);
                    }
                }));
    }

    @Nullable
    @Override
    public void loadSuccess(String datas) {
        getView().loadSuccess(datas);
    }

    @Override
    public void loadFail(String message) {
        getView().loadFail(message);
    }
}
