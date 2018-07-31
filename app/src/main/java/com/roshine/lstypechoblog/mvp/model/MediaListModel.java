package com.roshine.lstypechoblog.mvp.model;

import com.roshine.lstypechoblog.LsXmlRpcApplication;
import com.roshine.lstypechoblog.R;
import com.roshine.lstypechoblog.constants.Constants;
import com.roshine.lstypechoblog.http.LsRetrofitManage;
import com.roshine.lstypechoblog.http.RxUrlException;
import com.roshine.lstypechoblog.http.RxUtil;
import com.roshine.lstypechoblog.mvp.contract.ContractUtil;
import com.roshine.lstypechoblog.utils.SPUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.timroes.axmlrpc.XMLRPCException;
import io.reactivex.Flowable;

/**
 * @author Roshine
 * @date 2017/8/10 14:49
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MediaListModel implements ContractUtil.IMediaListModel{
    private ContractUtil.IMediaListPresenter mMediaListPresenter;
    public MediaListModel(ContractUtil.IMediaListPresenter mediaListPresenter){
        mMediaListPresenter = mediaListPresenter;
    }

    @Override
    public Flowable<String> getMediaList(int pageSize, int pageNum) {
        if (SPUtil.checkMethodAndUrl(Constants.MethodNames.METHOD_GET_MY_MEDIALS)) {
            HashMap<String,Integer> num = new HashMap<>();
            num.put("number",pageSize);
            num.put("offset",pageNum);
            List<Object> parameters = new ArrayList<>();
            parameters.add(1);
            parameters.add(SPUtil.getParam(Constants.SharedPreferancesKeys.USER_NAME, "").toString());
            parameters.add(SPUtil.getParam(Constants.SharedPreferancesKeys.USER_PASSWORD, "").toString());
            parameters.add(num);
            return LsRetrofitManage.getInstance()
                    .baseUrl(SPUtil.getParam(Constants.SharedPreferancesKeys.BLOG_URL, "").toString())
                    .parameters(parameters)
                    .method(Constants.MethodNames.METHOD_GET_MY_MEDIALS)
                    .errorCallBack(e -> {
                        if(e instanceof RxUrlException || e instanceof IllegalArgumentException){
                            mMediaListPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed_url));
                        } else if(e instanceof XMLRPCException){
                            mMediaListPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed_parameters));
                        } else{
                            mMediaListPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed));
                        }
                    })
                    .getService().compose(RxUtil.handleResult());

//            request = LsXmlRpcClient.request(context)
//                    .addParameters(parameters)
//                    .url(SPUtil.getParam(Constants.SharedPreferancesKeys.BLOG_URL, "").toString())
//                    .method(Constants.MethodNames.METHOD_GET_MY_MEDIALS)
//                    .callback(new RequestCallBack() {
//                        @Override
//                        public void onResponse(String result) {
//                            if (result != null) {
//                                LogUtil.showI("Roshine","加载成功，数据："+result);
//                                Gson gson = new Gson();
//                                List<MediaListBean> mediaLists = gson.fromJson(result, new TypeToken<List<MediaListBean>>() {
//                                }.getType());
//                                mMediaListPresenter.loadSuccess(mediaLists);
//                            } else {
//                                mMediaListPresenter.loadFail(context.getResources().getString(R.string.load_failed));
//                            }
//
//                        }
//
//                        @Override
//                        public void onError(String error) {
//                            LogUtil.showI("Roshine", "失败:" + error);
//                            mMediaListPresenter.loadFail(error);
//                        }
//                    }).excute();
        } else {
            mMediaListPresenter.loadFail(LsXmlRpcApplication.getContext().getResources().getString(R.string.load_failed));
            return null;
        }
    }
}
