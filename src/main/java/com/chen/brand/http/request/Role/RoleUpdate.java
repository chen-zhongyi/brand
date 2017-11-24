package com.chen.brand.http.request.Role;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Map;

public class RoleUpdate {

    @NotNull
    @ApiModelProperty(required = true)
    private Map<String, String> right;

    public Map<String, String> getRight() {
        return right;
    }

    public void setRight(Map<String, String> right) {
        this.right = right;
    }
}
