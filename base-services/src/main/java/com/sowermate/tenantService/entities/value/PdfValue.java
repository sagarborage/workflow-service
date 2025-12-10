package com.sowermate.tenantService.entities.value;

import com.sowermate.base.dtos.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PdfValue extends BaseDto {
    List<String> pdfs;
}
