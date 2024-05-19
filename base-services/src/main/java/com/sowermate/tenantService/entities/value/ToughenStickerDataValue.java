package com.sowermate.tenantService.entities.value;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ToughenStickerDataValue {
    private String piNo;
    private String thickness;
    private String partyName;
    private String size;
    private LocalDate date;
    private String unknownColumn;
    private List<ToughenStickerDataValue> abc;
}
