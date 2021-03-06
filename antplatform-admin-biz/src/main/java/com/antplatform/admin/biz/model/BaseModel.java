package com.antplatform.admin.biz.model;


import com.antplatform.admin.biz.model.entity.AbstractDomainObject;
import io.swagger.annotations.ApiModelProperty;
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
    /**
     * 创建人
     */
    @Column(name = "creator")
    private String creator;

    /**
     * 修改人
     */
    @Column(name = "editor")
    private String editor;

    @Column(name = "create_time",insertable = false)
    private Date createTime;

    @Column(name = "update_time",insertable = false)
    private Date updateTime;

    @Override
    public void causeChanged() {
        super.causeChanged();
        setUpdateTime(new Date());
    }
}
