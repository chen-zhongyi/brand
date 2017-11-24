package com.chen.brand.http.request.record;

import com.chen.brand.model.TableBase;
import com.chen.brand.model.TableJygm;
import com.chen.brand.model.TableQkdc;
import com.chen.brand.model.TableXse;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

public class RecordRequest {

    @Min(1)
    @Max(4)
    private Long tableId;
    private Long planId;
    private Long sampleId;
    private List<TableBase> base;
    private TableJygm jygm;
    private Map<Long, TableXse> xses;
    private TableQkdc qkdc;

    public List<TableBase> getBase() {
        return base;
    }

    public void setBase(List<TableBase> base) {
        this.base = base;
    }

    public Map<Long, TableXse> getXses() {
        return xses;
    }

    public void setXses(Map<Long, TableXse> xses) {
        this.xses = xses;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getSampleId() {
        return sampleId;
    }

    public void setSampleId(Long sampleId) {
        this.sampleId = sampleId;
    }

    public TableJygm getJygm() {
        return jygm;
    }

    public void setJygm(TableJygm jygm) {
        this.jygm = jygm;
    }

    public TableQkdc getQkdc() {
        return qkdc;
    }

    public void setQkdc(TableQkdc qkdc) {
        this.qkdc = qkdc;
    }
}
