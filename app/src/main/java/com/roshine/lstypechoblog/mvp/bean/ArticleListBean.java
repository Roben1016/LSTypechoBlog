package com.roshine.lstypechoblog.mvp.bean;

import java.util.List;

/**
 * @author Roshine
 * @date 2017/7/31 18:28
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc 文章数据bean
 */
public class ArticleListBean{

    /**
     * postid : 22
     * wp_post_thumbnail :
     * mt_allow_comments : 1
     * permaLink : http://www.roshine.xyz/index.php/archives/22/
     * post_status : pending
     * link : http://www.roshine.xyz/index.php/archives/22/
     * mt_text_more :
     * userid : 2
     * mt_keywords : test
     * mt_allow_pings : 1
     * title : 第二篇测试文章
     * wp_author : Roshine2
     * date_modified_gmt : Jul 31, 2017 10:26:09 AM
     * wp_password :
     * wp_more_text :
     * description : <p>这是第二篇测试文章的内容</p>

     * wp_author_display_name : Roshine2
     * custom_fields : []
     * date_modified : Jul 31, 2017 6:26:09 PM
     * mt_excerpt : 这是第二篇测试文章的内容
     * wp_post_format : standard
     * sticky : 0
     * date_created_gmt : Jul 31, 2017 10:26:00 AM
     * dateCreated : Jul 31, 2017 6:26:00 PM
     * categories : ["android"]
     * wp_author_id : 2
     * wp_slug : 22
     */

    private String postid;
    private String wp_post_thumbnail;
    private int mt_allow_comments;
    private String permaLink;
    private String post_status;
    private String link;
    private String mt_text_more;
    private String userid;
    private String mt_keywords;
    private int mt_allow_pings;
    private String title;
    private String wp_author;
    private String date_modified_gmt;
    private String wp_password;
    private String wp_more_text;
    private String description;
    private String wp_author_display_name;
    private String date_modified;
    private String mt_excerpt;
    private String wp_post_format;
    private int sticky;
    private String date_created_gmt;
    private String dateCreated;
    private String wp_author_id;
    private String wp_slug;
    private List<?> custom_fields;
    private List<String> categories;

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getWp_post_thumbnail() {
        return wp_post_thumbnail;
    }

    public void setWp_post_thumbnail(String wp_post_thumbnail) {
        this.wp_post_thumbnail = wp_post_thumbnail;
    }

    public int getMt_allow_comments() {
        return mt_allow_comments;
    }

    public void setMt_allow_comments(int mt_allow_comments) {
        this.mt_allow_comments = mt_allow_comments;
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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMt_text_more() {
        return mt_text_more;
    }

    public void setMt_text_more(String mt_text_more) {
        this.mt_text_more = mt_text_more;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMt_keywords() {
        return mt_keywords;
    }

    public void setMt_keywords(String mt_keywords) {
        this.mt_keywords = mt_keywords;
    }

    public int getMt_allow_pings() {
        return mt_allow_pings;
    }

    public void setMt_allow_pings(int mt_allow_pings) {
        this.mt_allow_pings = mt_allow_pings;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWp_author() {
        return wp_author;
    }

    public void setWp_author(String wp_author) {
        this.wp_author = wp_author;
    }

    public String getDate_modified_gmt() {
        return date_modified_gmt;
    }

    public void setDate_modified_gmt(String date_modified_gmt) {
        this.date_modified_gmt = date_modified_gmt;
    }

    public String getWp_password() {
        return wp_password;
    }

    public void setWp_password(String wp_password) {
        this.wp_password = wp_password;
    }

    public String getWp_more_text() {
        return wp_more_text;
    }

    public void setWp_more_text(String wp_more_text) {
        this.wp_more_text = wp_more_text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWp_author_display_name() {
        return wp_author_display_name;
    }

    public void setWp_author_display_name(String wp_author_display_name) {
        this.wp_author_display_name = wp_author_display_name;
    }

    public String getDate_modified() {
        return date_modified;
    }

    public void setDate_modified(String date_modified) {
        this.date_modified = date_modified;
    }

    public String getMt_excerpt() {
        return mt_excerpt;
    }

    public void setMt_excerpt(String mt_excerpt) {
        this.mt_excerpt = mt_excerpt;
    }

    public String getWp_post_format() {
        return wp_post_format;
    }

    public void setWp_post_format(String wp_post_format) {
        this.wp_post_format = wp_post_format;
    }

    public int getSticky() {
        return sticky;
    }

    public void setSticky(int sticky) {
        this.sticky = sticky;
    }

    public String getDate_created_gmt() {
        return date_created_gmt;
    }

    public void setDate_created_gmt(String date_created_gmt) {
        this.date_created_gmt = date_created_gmt;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getWp_author_id() {
        return wp_author_id;
    }

    public void setWp_author_id(String wp_author_id) {
        this.wp_author_id = wp_author_id;
    }

    public String getWp_slug() {
        return wp_slug;
    }

    public void setWp_slug(String wp_slug) {
        this.wp_slug = wp_slug;
    }

    public List<?> getCustom_fields() {
        return custom_fields;
    }

    public void setCustom_fields(List<?> custom_fields) {
        this.custom_fields = custom_fields;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
