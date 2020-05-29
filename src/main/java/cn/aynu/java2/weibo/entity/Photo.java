package cn.aynu.java2.weibo.entity;

import javax.validation.constraints.NotEmpty;

public class Photo {
    private String id;
    private String url;
    private String photoName;

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", photoName='" + photoName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }
}
