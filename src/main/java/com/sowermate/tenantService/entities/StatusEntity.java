package com.sowermate.tenantService.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="status")
public class StatusEntity {
    private static final long serialVersionUID = -241370177952331642L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "status_id", unique = true, nullable = false, updatable = false)
    private int statusId;

    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String uuid;
    @Column(name = "name")
    private String name;

    @Column(name = "is_active")
    private Boolean isActive;

}
