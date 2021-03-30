package com.example.zenly2;

public class UserProfile {
    private String name;
    private String age;
    private String myCode;
    private String friendCode;

    public UserProfile(){

    }

    public UserProfile(String name,String age, String myCode){
        this.name = name;
        this.age = age;
        this.myCode = myCode;
    }


    public UserProfile(String name,String age, String myCode, String friendCode){
        this.name = name;
        this.age = age;
        this.myCode = myCode;
        this.friendCode = friendCode;
    }

    public String getName(){
        return name;
    }
    public String getAge(){
        return age;
    }
    public String getMyCode(){
        return myCode;
    }
    public String getFriendCode(){
        return friendCode;
    }



}
