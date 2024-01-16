package com.sowermate.base.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * <h1>BaseIdDto Class</h1>
 * This Data Transfer Object (DTO) class represents a BaseId object for use in API calls.
 * It extends the {@link BaseIdDto} class which provides common fields such as UUID.
 *
 * @author ajadhav
 * @version 1.0
 * @see BaseIdDto
 * @since 2023-10-13
 */
@Getter
@Setter
public class BaseIdDto {

    /**
     * This UUID belongs to BaseId class.
     */
    private String uuid;
}
