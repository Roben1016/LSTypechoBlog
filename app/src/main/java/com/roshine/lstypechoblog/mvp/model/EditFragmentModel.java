package com.roshine.lstypechoblog.mvp.model;

import com.roshine.lstypechoblog.LsXmlRpcApplication;
import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.constants.Constants;
import com.roshine.lstypechoblog.http.LsRetrofitManage;
import com.roshine.lstypechoblog.http.RxUrlException;
import com.roshine.lstypechoblog.http.RxUtil;
import com.roshine.lstypechoblog.mvp.bean.ArticleDetailBean;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.mvp.presenter.EditFragmentPresenter;
import com.roshine.lstypechoblog.utils.SPUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.timroes.axmlrpc.XMLRPCException;
import io.reactivex.Flowable;

/**
 * @author Roshine
 * @date 2018/7/23 22:37
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class EditFragmentModel implements ContractUtil.IEditFragmentModel  {

    private EditFragmentPresenter editFragmentPresenter;
    public EditFragmentModel(EditFragmentPresenter editFragmentPresenter) {
        this.editFragmentPresenter = editFragmentPresenter;
    }

    @Override
    public Flowable<String> postEditBlog(String title,String content,ArticleDetailBean articleDetailBean) {
        if (SPUtil.checkMethodAndUrl(articleDetailBean == null?Constants.MethodNames.METHOD_CREATE_BLOG:Constants.MethodNames.METHOD_EDIT_BLOG)) {
            List<Object> parameters = new ArrayList<>();
            Map<String,Object> map = new HashMap<>();//ok
            String methodName = Constants.MethodNames.METHOD_CREATE_BLOG;
            map.put("title",title);
            map.put("description",content);
            map.put("post_type","post");
            if (articleDetailBean != null) {
                map.put("mt_text_more",articleDetailBean.getMt_text_more());
                map.put("wp_password",articleDetailBean.getWp_password());
                map.put("mt_keywords",articleDetailBean.getMt_keywords());
                map.put("dateCreated",new Date(articleDetailBean.getDateCreated()));
                map.put("categories",articleDetailBean.getCategories());
                map.put("mt_allow_comments",articleDetailBean.getMt_allow_comments());
                map.put("mt_allow_pings",articleDetailBean.getMt_allow_pings());
                map.put("_status",articleDetailBean.getPost_status());
                map.put("slug",articleDetailBean.getWp_slug());
                map.put("postId",articleDetailBean.getPostid());
                parameters.add(articleDetailBean.getPostid());
                methodName = Constants.MethodNames.METHOD_EDIT_BLOG;
            }else{
                parameters.add(1);
                methodName = Constants.MethodNames.METHOD_CREATE_BLOG;
            }
            parameters.add(SPUtil.getParam(Constants.SharedPreferancesKeys.USER_NAME, ""));
            parameters.add(SPUtil.getParam(Constants.SharedPreferancesKeys.USER_PASSWORD, ""));
            parameters.add(map);
            parameters.add(true);
            return LsRetrofitManage.getInstance()
                    .baseUrl(SPUtil.getParam(Constants.SharedPreferancesKeys.BLOG_URL, "").toString())
                    .method(methodName)
                    .parameters(parameters)
                    .errorCallBack(e -> {
                        if(e instanceof RxUrlException || e instanceof IllegalArgumentException){
                            editFragmentPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed_url));
                        } else if(e instanceof XMLRPCException){
                            editFragmentPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed_parameters));
                        } else{
                            editFragmentPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed));
                        }
                    })
                    .getService()
                    .compose(RxUtil.handleResult());
        } else {
            editFragmentPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed));
            return null;
        }
    }
}
