package com.werewolf.models;

public class ExecuteResultModel {

    private int sourceSitId;
    private int targetSitId;
    private boolean executeResult;

    public int getSourceSitId() {
        return sourceSitId;
    }

    public void setSourceSitId(int sourceSitId) {
        this.sourceSitId = sourceSitId;
    }

    public int getTargetSitId() {
        return targetSitId;
    }

    public void setTargetSitId(int targetSitId) {
        this.targetSitId = targetSitId;
    }

    public boolean isExecuteResult() {
        return executeResult;
    }

    public void setExecuteResult(boolean executeResult) {
        this.executeResult = executeResult;
    }
}
