package com.example.testcom.Chat;

public class chatModel {
    String cName, cProfileImageUrl, cUid;

    public chatModel(String cName, String cProfileImageUrl, String cUid) {
        this.cName = cName;
        this.cProfileImageUrl = cProfileImageUrl;
        this.cUid = cUid;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcProfileImageUrl() {
        return cProfileImageUrl;
    }

    public void setcProfileImageUrl(String cProfileImageUrl) {
        this.cProfileImageUrl = cProfileImageUrl;
    }

    public String getcUid() {
        return cUid;
    }

    public void setcUid(String cUid) {
        this.cUid = cUid;
    }
}
