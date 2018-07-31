package com.roshine.lstypechoblog.event;

/**
 * @author Roshine
 * @date 2017/9/10 19:56
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class PreBlogDataEvent {
    private String title;

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    private String content;
    public PreBlogDataEvent(String title,String content){
        this.title = title;
        this.content = content;
    }


}
