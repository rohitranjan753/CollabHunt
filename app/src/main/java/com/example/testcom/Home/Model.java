package com.example.testcom.Home;

public class Model {
    String rName, rAreaOfInterest, rState, rProfileImageUrl, rUid;

    public Model(String rName, String rAreaOfInterest, String rState, String rProfileImageUrl, String rUid) {
        this.rName = rName;
        this.rAreaOfInterest = rAreaOfInterest;
        this.rState = rState;
        this.rProfileImageUrl = rProfileImageUrl;
        this.rUid = rUid;
    }

    public String getrName() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName = rName;
    }

    public String getrAreaOfInterest() {
        return rAreaOfInterest;
    }

    public void setrAreaOfInterest(String rAreaOfInterest) {
        this.rAreaOfInterest = rAreaOfInterest;
    }

    public String getrState() {
        return rState;
    }

    public void setrState(String rState) {
        this.rState = rState;
    }

    public String getrProfileImageUrl() {
        return rProfileImageUrl;
    }

    public void setrProfileImageUrl(String rProfileImageUrl) {
        this.rProfileImageUrl = rProfileImageUrl;
    }

    public String getrUid() {
        return rUid;
    }
}
