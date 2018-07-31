package com.roshine.lstypechoblog.mvp.presenter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.roshine.lstypechoblog.http.RxSubUtil;
import com.roshine.lstypechoblog.mvp.bean.MediaListBean;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.mvp.model.MediaListModel;

import java.util.List;

/**
 * @author Roshine
 * @date 2017/8/10 13:59
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MediaListPresenter extends IBasePresenter<ContractUtil.IMediaListView> implements ContractUtil.IMediaListPresenter {
//    private ContractUtil.IMediaListView mIMediaListView;
    private MediaListModel mediaListModel;
    public MediaListPresenter(){
//        this.mIMediaListView = iMediaListView;
        mediaListModel = new MediaListModel(this);
    }

    @Override
    public void getMediaList(Context context, int pageSize, int pageNum) {
//        mediaListModel.getMediaList(context,pageSize,pageNum);
        compositeDisposable.add(mediaListModel.getMediaList(pageSize,pageNum)
        .subscribeWith(new RxSubUtil<String>(compositeDisposable,context) {
            @Override
            protected void onSuccess(String result) {
                Gson gson = new Gson();
                List<MediaListBean> mediaLists = gson.fromJson(result, new TypeToken<List<MediaListBean>>() {}.getType());
                getView().loadSuccess(mediaLists);
            }

            @Override
            protected void onFail(String errorMsg) {
                getView().loadFail(errorMsg);
            }
        }));
    }

    @Nullable
    @Override
    public void loadSuccess(List<MediaListBean> listDatas) {
        getView().loadSuccess(listDatas);
    }


    @Override
    public void loadFail(String message) {
        getView().loadFail(message);
    }
}
