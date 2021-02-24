package com.antplatform.admin.api.request;

import com.antplatform.admin.common.dto.PagedRequest;
import lombok.Data;

/**
 * @author: maoyan
 * @date: 2020/10/22 14:06:12
 * @description:
 */
@Data
public class OrganizationPageSpec extends PagedRequest {
    private String name;

    private String keypoint;
}
