package com.antplatform.admin.api.request;

import com.antplatform.admin.common.dto.PagedRequest;
import lombok.Data;

/**
 * @author: maoyan
 * @date: 2020/1/22 14:37:45
 * @description:
 */
@Data
public class UserMgtSpec extends PagedRequest {
    String username;
    String password;
    String token;
}
