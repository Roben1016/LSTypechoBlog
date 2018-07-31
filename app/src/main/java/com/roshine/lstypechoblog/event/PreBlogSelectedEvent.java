package com.roshine.lstypechoblog.event;

/**
 * @author Roshine
 * @date 2017/9/10 19:52
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class PreBlogSelectedEvent {
    public boolean isHasSelectedPre() {
        return hasSelectedPre;
    }

    private boolean hasSelectedPre;
    public PreBlogSelectedEvent(boolean hasSelectedPre){
        this.hasSelectedPre = hasSelectedPre;
    }

}
