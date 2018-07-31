package com.roshine.lstypechoblog.event;

import com.roshine.lstypechoblog.mvp.bean.ArticleDetailBean;

/**
 * @author Roshine
 * @date 2017/9/9 23:34
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class ArticleDetailReceivedEvent {
    public ArticleDetailBean getArticleDetailBean() {
        return articleDetailBean;
    }

    public void setArticleDetailBean(ArticleDetailBean articleDetailBean) {
        this.articleDetailBean = articleDetailBean;
    }

    private ArticleDetailBean articleDetailBean;
    public ArticleDetailReceivedEvent(ArticleDetailBean articleDetailBean){
        this.articleDetailBean = articleDetailBean;
    }
}
