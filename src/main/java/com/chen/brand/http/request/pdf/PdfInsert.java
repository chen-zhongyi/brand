package com.chen.brand.http.request.pdf;

import javax.validation.constraints.NotNull;

public class PdfInsert {

    @NotNull
    private Long brandId;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
