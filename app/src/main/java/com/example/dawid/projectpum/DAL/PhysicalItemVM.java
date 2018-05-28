package com.example.dawid.projectpum.DAL;

/**
 * Created by Dawid on 15.05.2018.
 */

public class PhysicalItemVM {
    String SportName;
    String SportValue;
    String SportTime;

    public PhysicalItemVM(String sportName, String sportValue, String sportTime) {
        SportName = sportName;
        SportValue = sportValue;
        SportTime = sportTime;
    }

    public String getSportName() {
        return SportName;
    }

    public void setSportName(String sportName) {
        SportName = sportName;
    }

    public String getSportValue() {
        return SportValue;
    }

    public void setSportValue(String sportValue) {
        SportValue = sportValue;
    }

    public String getSportTime() {
        return SportTime;
    }

    public void setSportTime(String sportTime) {
        SportTime = sportTime;
    }
}
