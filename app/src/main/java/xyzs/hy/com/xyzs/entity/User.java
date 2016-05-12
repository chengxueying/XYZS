package xyzs.hy.com.xyzs.entity;

import cn.bmob.v3.*;

public class User extends BmobUser {
    private String headSculpture;//头像

    public void setHeadSculpture(String headSculpture) {
        this.headSculpture = headSculpture;
    }

    public String getHeadSculpture() {
        return headSculpture;
    }
}
