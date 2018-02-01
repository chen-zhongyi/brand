package com.chen.brand.model;

public class BrandApprove {

    private Long id;
    private Long userId;
    private Long brandId;
    private String year;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
