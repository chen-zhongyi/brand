package com.chen.brand.http.request.Plan;

import javax.validation.constraints.Min;
import java.sql.Date;

public class PlanRequest {

    private String name;
    private Date startAt;
    private Date endAt;
    @Min(1)
    private Long groupId;
    private Date round;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartAt() {
        return startAt;
    }

    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    public Date getEndAt() {
        return endAt;
    }

    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Date getRound() {
        return round;
    }

    public void setRound(Date round) {
        this.round = round;
    }
}
