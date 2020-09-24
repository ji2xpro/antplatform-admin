package com.antplatform.admin.api.request;

import com.antplatform.admin.common.dto.PagedRequest;
import com.antplatform.admin.common.enums.DataType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: maoyan
 * @date: 2020/8/31 19:40:05
 * @description:
 */
@Data
public class RolePageSpec extends PagedRequest {

    private Integer id;

    private String name;

    private String keypoint;

    private Integer status;
}
