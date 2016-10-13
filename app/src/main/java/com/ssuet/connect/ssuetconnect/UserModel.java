package com.ssuet.connect.ssuetconnect;

/**
 * Created by DELL on 10/13/2016.
 */

public class UserModel {

    private String Name;
    private String UserName;
    private String BatchNo;
    private String UserImage;


    public UserModel() {
    }

    public UserModel(String name, String userName, String batchNo, String userImage) {
        Name = name;
        UserName = userName;
        BatchNo = batchNo;
        UserImage = userImage;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getBatchNo() {
        return BatchNo;
    }

    public void setBatchNo(String batchNo) {
        BatchNo = batchNo;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }
}
