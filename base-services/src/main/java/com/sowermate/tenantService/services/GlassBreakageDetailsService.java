package com.sowermate.tenantService.services;

import com.sowermate.tenantService.entities.value.ConfirmThroughValue;
import com.sowermate.tenantService.entities.value.GlassBreakageDetailsValue;

import java.util.List;

public interface GlassBreakageDetailsService {

    public GlassBreakageDetailsValue createGlassBreakageDetails(GlassBreakageDetailsValue glassBreakageDetailsValue);

    public List<GlassBreakageDetailsValue> getAllGlassBreakageDetails(String tenantUuid, String companyUuid, String proFormaInvoiceUuid, String workOrderUuid, String proFormaInvoiceItemUuid);

    public GlassBreakageDetailsValue editGlassBreakageDetails(GlassBreakageDetailsValue  glassBreakageDetailsValue);

    public GlassBreakageDetailsValue getGlassBreakageDetails(String glassBreakageDetailsUuid,String proFormaInvoiceItemUuid);

    public int deleteGlassBreakageDetails(String glassBreakageDetailsUuid, String tenantUuid, String companyUuid, String proFormaInvoiceUuid, String workOrderUuid, String proFormaInvoiceItemUuid);

}
