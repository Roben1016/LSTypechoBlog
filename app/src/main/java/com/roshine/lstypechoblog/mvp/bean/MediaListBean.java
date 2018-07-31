package com.roshine.lstypechoblog.mvp.bean;

/**
 * @author Roshine
 * @date 2017/8/10 14:25
 * @blog http://www.roshine.xyz
 * @email roshines1016@gmail.com
 * @github https://github.com/Roben1016
 * @phone 136****1535
 * @desc
 */
public class MediaListBean {

    /**
     * attachment_id : 18
     * caption : auto_note_gif5-gif
     * date_created_gmt : Jun 30, 2017 22:39:33
     * description :
     * link : http://roshine.xyz/usr/uploads/2017/06/2608909200.gif
     * metadata : {"file":"/usr/uploads/2017/06/2608909200.gif","size":2065141}
     * parent : 15
     * thumbnail : http://roshine.xyz/usr/uploads/2017/06/2608909200.gif
     * title : auto_note_gif5.gif
     */

    private String attachment_id;
    private String caption;
    private String date_created_gmt;
    private String description;
    private String link;
    private MetadataBean metadata;
    private String parent;
    private String thumbnail;
    private String title;

    public String getAttachment_id() {
        return attachment_id;
    }

    public void setAttachment_id(String attachment_id) {
        this.attachment_id = attachment_id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
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

    public MetadataBean getMetadata() {
        return metadata;
    }

    public void setMetadata(MetadataBean metadata) {
        this.metadata = metadata;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class MetadataBean {
        /**
         * file : /usr/uploads/2017/06/2608909200.gif
         * size : 2065141
         */

        private String file;
        private int size;

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}
