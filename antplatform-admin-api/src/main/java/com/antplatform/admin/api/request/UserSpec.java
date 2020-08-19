package com.antplatform.admin.api.request;

import com.antplatform.admin.common.dto.Request;
import lombok.Data;

/**
 * @author: maoyan
 * @date: 2020/8/11 17:48:32
 * @description:
 */
@Data
public class UserSpec extends Request {
    private int userId;

    private String username;

    private String password;
}
