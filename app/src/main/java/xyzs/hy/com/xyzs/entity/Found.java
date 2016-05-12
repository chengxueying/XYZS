package xyzs.hy.com.xyzs.entity;

import cn.bmob.v3.*;

/**
 * 固定用法，详情查看bmob官方文档
 */

public class Found extends BmobObject {
    private String title;//标题
    private String describe;//内容
    private String phone;//手机
    private String imageURL;//图片地址
    private User publisher;//用户
    private int status;//有无图片标识

    public Found() {

    }

    public Found(String title, String describe, String phone, String imageURL, int status, User publisher) {
        this.title = title;
        this.describe = describe;
        this.phone = phone;
        this.imageURL = imageURL;
        this.status = status;
        this.publisher = publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public User getPublisher() {
        return publisher;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
