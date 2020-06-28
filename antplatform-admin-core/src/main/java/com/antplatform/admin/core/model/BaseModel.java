package com.antplatform.admin.core.model;


import com.antplatform.admin.core.model.entity.AbstractDomainObject;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: maoyan
 * @date: 2020/1/6 19:01:45
 * @description:
 */
@Data
public class BaseModel extends AbstractDomainObject implements Serializable{

    @Column(name = "CreateTime")
    private Date createTime;

    @Column(name = "UpdateTime")
    private Date updateTime;

    @Override
    public void causeChanged() {
        super.causeChanged();
        setUpdateTime(new Date());
    }
}
