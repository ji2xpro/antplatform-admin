package com.antplatform.admin.common.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: maoyan
 * @date: 2020/10/22 17:13:44
 * @description:
 */
@Data
public class TreeDTO extends BaseDTO {

    private Integer id;

    private Integer parentId;

    private List children = new ArrayList<>();
}
