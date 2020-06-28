package com.antplatform.admin.core.model.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * User: changming.xie
 * Date: 14-6-25
 * Time: 下午6:57
 */
public interface DomainObject extends Serializable {


    Date getCreateTime();

    Date getUpdateTime();
}