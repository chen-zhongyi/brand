package com.chen.brand.Enum;

public enum ApproveStatus {

    NotApprove(1L, "未审核"),
    FirstApproveNotPass(2L, "初审未通过"),
    FirstApprovePass(3L, "初审通过"),
    FinalApproveNotPass(4L, "终审未通过"),
    FinalApprovePass(5L, "终审通过");

    private Long status;
    private String description;

    ApproveStatus(Long status, String description){
        this.status = status;
        this.description = description;
    }

    public static ApproveStatus convert(Long status){
        for(ApproveStatus approveStatus : ApproveStatus.values()){
            if(approveStatus.getStatus() == status) return approveStatus;
        }
        return null;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
