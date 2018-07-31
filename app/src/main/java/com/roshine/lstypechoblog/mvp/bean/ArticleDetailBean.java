package com.roshine.lstypechoblog.mvp.bean;

import java.util.List;

/**
 * @author Roshine
 * @date 2017/9/8 22:54
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class ArticleDetailBean {


    /**
     * categories : ["android"]
     * custom_fields : []
     * dateCreated : Jun 18, 2017 5:43:00 PM
     * date_created_gmt : Jun 18, 2017 9:43:00 AM
     * description :
     * link : http://www.roshine.xyz/index.php/archives/44/
     * mt_allow_comments : 1
     * mt_allow_pings : 1
     * mt_excerpt : 基本概念&emsp;&emsp;一张静止的图片我们称之为图像，那么视频便是很多张图片连续变化所产生，而连续的图像变化每秒超过 24 帧画面以上时，根椐视觉暂留原理，人眼无法辨别每付单独的静态画面...
     * mt_keywords : android, 笔记, 视频开发
     * mt_text_more :
     * permaLink : http://www.roshine.xyz/index.php/archives/44/
     * post_status : publish
     * postid : 44
     * sticky : 0
     * title : 关于视频开发笔记
     * userid : 1
     * wp_author : Roshine
     * wp_author_display_name : Roshine
     * wp_author_id : 1
     * wp_password :
     * wp_slug : 44
     */

    private String dateCreated;
    private String date_created_gmt;
    private String description;
    private String link;
    private int mt_allow_comments;
    private int mt_allow_pings;
    private String mt_excerpt;
    private String mt_keywords;
    private String mt_text_more;
    private String permaLink;
    private String post_status;
    private String postid;
    private int sticky;
    private String title;
    private String userid;
    private String wp_author;
    private String wp_author_display_name;
    private String wp_author_id;
    private String wp_password;
    private String wp_slug;
    private List<String> categories;
    private List<?> custom_fields;

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDate_created_gmt() {
        return date_created_gmt;
    }

    public void setDate_created_gmt(String date_created_gmt) {
        this.date_created_gmt = date_created_gmt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getMt_allow_comments() {
        return mt_allow_comments;
    }

    public void setMt_allow_comments(int mt_allow_comments) {
        this.mt_allow_comments = mt_allow_comments;
    }

    public int getMt_allow_pings() {
        return mt_allow_pings;
    }

    public void setMt_allow_pings(int mt_allow_pings) {
        this.mt_allow_pings = mt_allow_pings;
    }

    public String getMt_excerpt() {
        return mt_excerpt;
    }

    public void setMt_excerpt(String mt_excerpt) {
        this.mt_excerpt = mt_excerpt;
    }

    public String getMt_keywords() {
        return mt_keywords;
    }

    public void setMt_keywords(String mt_keywords) {
        this.mt_keywords = mt_keywords;
    }

    public String getMt_text_more() {
        return mt_text_more;
    }

    public void setMt_text_more(String mt_text_more) {
        this.mt_text_more = mt_text_more;
    }

    public String getPermaLink() {
        return permaLink;
    }

    public void setPermaLink(String permaLink) {
        this.permaLink = permaLink;
    }

    public String getPost_status() {
        return post_status;
    }

    public void setPost_status(String post_status) {
        this.post_status = post_status;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public int getSticky() {
        return sticky;
    }

    public void setSticky(int sticky) {
        this.sticky = sticky;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getWp_author() {
        return wp_author;
    }

    public void setWp_author(String wp_author) {
        this.wp_author = wp_author;
    }

    public String getWp_author_display_name() {
        return wp_author_display_name;
    }

    public void setWp_author_display_name(String wp_author_display_name) {
        this.wp_author_display_name = wp_author_display_name;
    }

    public String getWp_author_id() {
        return wp_author_id;
    }

    public void setWp_author_id(String wp_author_id) {
        this.wp_author_id = wp_author_id;
    }

    public String getWp_password() {
        return wp_password;
    }

    public void setWp_password(String wp_password) {
        this.wp_password = wp_password;
    }

    public String getWp_slug() {
        return wp_slug;
    }

    public void setWp_slug(String wp_slug) {
        this.wp_slug = wp_slug;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<?> getCustom_fields() {
        return custom_fields;
    }

    public void setCustom_fields(List<?> custom_fields) {
        this.custom_fields = custom_fields;
    }
}
