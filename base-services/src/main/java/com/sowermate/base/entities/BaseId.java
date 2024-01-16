package com.sowermate.base.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * <h1>BaseId Class</h1>
 * This class represents a BaseId entity in the application. It extends the {@link BaseId} class
 * which provides common entity fields such as UUID and ID.
 *
 * @author ajadhav
 * @version 1.0
 * @see BaseId
 * @since 2023-10-13
 */
@Getter
@Setter
@MappedSuperclass
public class BaseId {

    /**
     * The ID of the class is auto increment and Unique.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * The UUID of the class is auto generated and Unique.
     */
    @Column(name = "uuid")
    private String uuid;

    @PrePersist
    public void autofillCreate1() {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString();
        }
    }
}
