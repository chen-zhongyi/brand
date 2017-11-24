package com.chen.brand.http.request.menu;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MenuInsert {

    @NotNull
    @ApiModelProperty( required = true)
    private String name;
    @NotNull
    @ApiModelProperty( required = true)
    @Min(0)
    private Long pid;
    private String url;
    private String sort;
    private String image;
    @NotNull
    @ApiModelProperty( required = true)
    private String system;
    @NotNull
    @Pattern(regexp = "^(admin)|(user)", message = "只能是admin、user中的")
    @ApiModelProperty( required = true)
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
