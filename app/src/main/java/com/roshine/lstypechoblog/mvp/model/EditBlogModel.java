package com.roshine.lstypechoblog.mvp.model;

import com.roshine.lstypechoblog.LsXmlRpcApplication;
import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.constants.Constants;
import com.roshine.lstypechoblog.http.LsRetrofitManage;
import com.roshine.lstypechoblog.http.RxUrlException;
import com.roshine.lstypechoblog.http.RxUtil;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.mvp.presenter.EditBlogPresenter;
import com.roshine.lstypechoblog.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import de.timroes.axmlrpc.XMLRPCException;
import io.reactivex.Flowable;

/**
 * @author Roshine
 * @date 2017/9/8 23:02
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class EditBlogModel implements ContractUtil.IEditBlogModel {

    private EditBlogPresenter preBlogPresenter;

    public EditBlogModel(EditBlogPresenter preBlogPresenter){
        this.preBlogPresenter = preBlogPresenter;
    }

    @Override
    public Flowable<String> getArticleDetail(String postId) {
        if (SPUtil.checkMethodAndUrl(Constants.MethodNames.METHOD_GET_ARTICLE_DETAIL)) {
            List<Object> parameters = new ArrayList<>();
            parameters.add(postId);
            parameters.add(SPUtil.getParam(Constants.SharedPreferancesKeys.USER_NAME, "").toString());
            parameters.add(SPUtil.getParam(Constants.SharedPreferancesKeys.USER_PASSWORD, "").toString());
            return LsRetrofitManage.getInstance()
                    .baseUrl(SPUtil.getParam(Constants.SharedPreferancesKeys.BLOG_URL, "").toString())
                    .method(Constants.MethodNames.METHOD_GET_ARTICLE_DETAIL)
                    .parameters(parameters)
                    .errorCallBack(e -> {
                        if(e instanceof RxUrlException || e instanceof IllegalArgumentException){
                            preBlogPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed_url));
                        } else if(e instanceof XMLRPCException){
                            preBlogPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed_parameters));
                        } else{
                            preBlogPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed));
                        }
                    })
                    .getService()
                    .compose(RxUtil.handleResult());
        } else {
            preBlogPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed));
            return null;
        }
    }
}
