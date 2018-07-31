package com.roshine.lstypechoblog.mvp.presenter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.http.RxSubUtil;
import com.roshine.lstypechoblog.mvp.bean.ArticleDetailBean;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.mvp.model.EditBlogModel;
import com.roshine.lstypechoblog.utils.LogUtil;

/**
 * @author Roshine
 * @date 2017/9/8 22:51
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class EditBlogPresenter extends IBasePresenter<ContractUtil.IEditBlogView> implements ContractUtil.IEditBlogPresenter {
    private EditBlogModel preBlogModel;

    public EditBlogPresenter() {
        preBlogModel = new EditBlogModel(this);
    }

    @Override
    public void getArticleDetail(Context context, String postId) {
        compositeDisposable.add(preBlogModel.getArticleDetail(postId)
                .subscribeWith(new RxSubUtil<String>(compositeDisposable, context) {
                    @Override
                    protected void onSuccess(String s) {
                        try {
                            LogUtil.show("博客详情："+s);
                            Gson gson = new Gson();
                            ArticleDetailBean articleDetailBean = gson.fromJson(s, ArticleDetailBean.class);
                            loadSuccess(articleDetailBean);
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
    public void loadSuccess(ArticleDetailBean datas) {
        getView().loadSuccess(datas);
    }

    @Override
    public void loadFail(String message) {
        getView().loadFail(message);
    }
}
