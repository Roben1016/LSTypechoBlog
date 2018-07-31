package com.roshine.lstypechoblog.mvp.contract;

import android.content.Context;

import com.roshine.lstypechoblog.mvp.bean.ArticleDetailBean;
import com.roshine.lstypechoblog.mvp.bean.ArticleListBean;
import com.roshine.lstypechoblog.mvp.bean.MediaListBean;
import com.roshine.lstypechoblog.mvp.model.base.BaseModel;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author Roshine
 * @date 2017/7/20 22:27
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 契约工具接口
 */
public interface ContractUtil {

    /*=============登录契约===============*/
    interface ILoginView extends IBaseView{
//        void loginSuccess();
        String getUrl();
        String getUserName();
        String getUserPasswd();
    }
    interface ILoginPresenter{
        void getAllMethodAndAuthenty(Context context);
        void loginSuccess();
        void loginFail(String message);
    }
    interface ILoginModel<T> extends BaseModel{
        Flowable<T> getAllMethod(String url, String userName, String password);
        Flowable<T> getAuthenty(String userName,String password);
    }

    /*===============我的文章列表契约====================*/
    interface IArticleListView  extends IBaseView<List<ArticleListBean>> {
        void loadSuccess(List<ArticleListBean> articleListBeanList);
        void loadFail(String message);
    }
    interface IArticleListPresenter extends IBaseView<List<ArticleListBean>> {
        void getArticles(Context context,int count);
//        void loadSuccess(List<ArticleListBean> articleListBeanList);
//        void loadFail(String message);
    }
    interface IArticleListModel<T> extends BaseModel {
        Flowable<T> getArticleList(int count);
    }

    /*=======================我的媒体文件列表契约=========================*/
    interface IMediaListView extends IBaseView<List<MediaListBean>> {

    }
    interface IMediaListPresenter extends IBaseView<List<MediaListBean>> {
        void getMediaList(Context context,int pageSize,int pageNum);
    }
    interface IMediaListModel<T> extends BaseModel{
        Flowable<T> getMediaList(int pageSize, int pageNum);
    }

    /*=======================博客文章详情预览=========================*/
    interface IEditBlogView extends IBaseView<ArticleDetailBean>{}
    interface IEditBlogPresenter extends IBaseView<ArticleDetailBean>{
        void getArticleDetail(Context context,String postId);
    }
    interface IEditBlogModel<T> extends BaseModel{
        Flowable<T> getArticleDetail(String postId);
    }

    /*=======================博客保存=========================*/
    interface IEditFragmentView extends IBaseView<String>{}
    interface IEditFragmentPresenter extends IBaseView<String>{
        void postEditBlog(Context context,String title,String content,ArticleDetailBean articleDetailBean);
    }
    interface IEditFragmentModel<T> extends BaseModel{
        Flowable<T> postEditBlog(String title,String content,ArticleDetailBean articleDetailBean);
    }
}
