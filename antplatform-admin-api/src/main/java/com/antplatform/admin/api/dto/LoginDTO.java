package com.antplatform.admin.api.dto;

import com.antplatform.admin.common.dto.BaseDTO;
import lombok.Data;

import java.util.Base64;

/**
 * @author: maoyan
 * @date: 2020/7/9 16:56:22
 * @description:
 */
@Data
public class LoginDTO extends BaseDTO {
    private String token;
}
