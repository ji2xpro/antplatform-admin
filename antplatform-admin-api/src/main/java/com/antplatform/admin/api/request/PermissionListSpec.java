package com.antplatform.admin.api.request;

import com.antplatform.admin.common.dto.Request;
import lombok.Data;

import java.util.Collection;

/**
 * @author: maoyan
 * @date: 2020/8/11 17:03:34
 * @description:
 */
@Data
public class PermissionListSpec extends Request {

    private Collection<Integer> ids;

    private int parentId;

}
