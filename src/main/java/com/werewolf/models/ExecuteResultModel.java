package com.werewolf.models;

public class ExecuteResultModel {

    private int sitId;
    private boolean result;

    public ExecuteResultModel(int sitId, boolean result) {
        this.sitId = sitId;
        this.result = result;
    }

    public int getSitId() {
        return sitId;
    }

    public void setSitId(int sitId) {
        this.sitId = sitId;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
