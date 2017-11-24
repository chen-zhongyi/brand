package com.chen.brand.http.request.Role;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class RoleInsert {

    @NotNull
    @ApiModelProperty(required = true)
    private String code;
    private String description;
    @NotNull
    @ApiModelProperty(required = true)
    private Map<String, String> right;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getRight() {
        return right;
    }

    public void setRight(Map<String, String> right) {
        this.right = right;
    }
}
