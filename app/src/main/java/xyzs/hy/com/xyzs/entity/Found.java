package xyzs.hy.com.xyzs.entity;

import cn.bmob.v3.*;

public class Found extends BmobObject {
    private String title;
    private String describe;
    private String phone;
    private String imageURL;

    public Found(String title, String describe, String phone, String imageURL) {
        this.title = title;
        this.describe = describe;
        this.phone = phone;
        this.imageURL = imageURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDescribe() {
        return describe;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setImageURL(String omageURL) {
        this.imageURL = omageURL;
    }

    public String getImageURL() {
        return imageURL;
    }
}
