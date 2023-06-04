package com.sowermate.tenantService.entities.common;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class CommonEntity implements Serializable {

    private static final long serialVersionUID = 3981140897718611608L;

    @Column(name="uuid", unique=true, updatable=false)
    protected String uuid;

    @Column(name="created_dttm")
    protected Date createdDttm;

    @Column(name="updated_dttm")
    protected Date updatedDttm;

}
