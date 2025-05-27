package com.sowermate.workflow.domain.entities.value;

import com.sowermate.core.base.dtos.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PdfValue extends BaseDto {
    List<String> pdfs;
}
