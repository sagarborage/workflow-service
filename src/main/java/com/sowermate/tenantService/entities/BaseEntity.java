package com.sowermate.tenantService.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@ToString
@Getter
@NoArgsConstructor
@SuperBuilder(builderMethodName = "newBaseEntityBuilder", toBuilder = true)
public class BaseEntity implements Serializable {

    @Column(name = "created_dttm")
    private Date createdTimeStamp;

    @Column(name = "updated_dttm")
    private Date lastUpdatedTimeStamp;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;
}
