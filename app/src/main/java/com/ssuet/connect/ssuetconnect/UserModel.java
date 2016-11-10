package com.ssuet.connect.ssuetconnect;

/**
 * Created by DELL on 10/13/2016.
 */

public class UserModel {

    private String Name;
    private String Profile_Image;


    public UserModel() {
    }

    public UserModel(String name, String Images) {
        this.Name = name;
        this.Profile_Image= Images;
    }


    public String getProfile_Image() {
        return Profile_Image;
    }

    public void setProfile_Image(String profile_Image) {
        Profile_Image = profile_Image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
