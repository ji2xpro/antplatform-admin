package com.antplatform.admin.api.request;

import com.antplatform.admin.common.dto.PagedRequest;
import lombok.Data;

/**
 * @author: maoyan
 * @date: 2020/8/31 19:40:05
 * @description:
 */
@Data
public class UserPageSpec extends PagedRequest {

    private Integer id;

    private String username;

    private String mobile;

    private String email;

    private Integer roleId;

    private Integer status;
}
