package com.antplatform.admin.api.dto;

import com.antplatform.admin.common.dto.BaseDTO;
import lombok.Data;

import java.util.Date;

/**
 * @author: maoyan
 * @date: 2020/8/31 16:29:34
 * @description:
 */
@Data
public class RoleDTO extends BaseDTO {
    private Integer id;

    private String name;

    private String keypoint;

    private Integer type;

    private Date createTime;


    private String remark;

    private Integer status;
}

