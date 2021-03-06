package com.antplatform.admin.api.dto;

import com.antplatform.admin.common.dto.BaseDTO;
import lombok.Data;

/**
 * @author: maoyan
 * @date: 2020/9/18 10:25:00
 * @description:
 */
@Data
public class RoleAuthDTO extends BaseDTO {
    private Integer id;

    private Integer roleId;

    private Integer authId;

    private String name;

    private Integer Type;

}
