package com.microlan.rushhandingoffline.OfflineModel;

public class StateModel {

    String State,Statecode;

    public StateModel(String state, String statecode) {
        State = state;
        Statecode = statecode;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getStatecode() {
        return Statecode;
    }

    public void setStatecode(String statecode) {
        Statecode = statecode;
    }
}
