package com.sowermate.tenantService.services.impl;

import com.sowermate.image.services.PdfService;
import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceValue;
import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;
import com.sowermate.tenantService.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProFormaInvoiceServiceImplTest {

    @Mock
    private ProFormaInvoiceRepository proFormaInvoiceRepository;
    @Mock
    private GlassTypeRepository glassTypeRepository;
    @Mock
    private GlassSpecificationRepository glassSpecificationRepository;
    @Mock
    private GlassThicknessRepository glassThicknessRepository;
    @Mock
    private PdfService pdfService;
    @Mock
    private ConfirmThroughRepository confirmThroughRepository;
    @Mock
    private PiTypeRepository piTypeRepository;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private TenantRepository tenantRepository;
    @Mock
    private ServiceRateRepository serviceRateRepository;
    @Mock
    private WorkOrderRepository workOrderRepository;

    @InjectMocks
    private ProFormaInvoiceServiceImpl proFormaInvoiceService;

    private ProFormaInvoiceEntity existingInvoice;
    private ProFormaInvoiceValue updateValue;
    private String tenantUuid;
    private String invoiceUuid;

    @BeforeEach
    void setUp() {
        tenantUuid = UUID.randomUUID().toString();
        invoiceUuid = UUID.randomUUID().toString();

        TenantEntity tenantEntity = TenantEntity.newBuilder().build();
        tenantEntity.setUuid(tenantUuid);

        existingInvoice = ProFormaInvoiceEntity.newBuilder()
                .id(1L)
                .uuid(invoiceUuid)
                .tenantEntity(tenantEntity)
                .proFormaInvoiceItemEntities(new ArrayList<>())
                .serviceRateInvoiceEntities(new ArrayList<>())
                .build();

        updateValue = ProFormaInvoiceValue.newBuilder()
                .tenantUuid(tenantUuid)
                .proFormaInvoiceUuid(invoiceUuid)
                .proFormaInvoiceItems(new ArrayList<>())
                .serviceRateInvoices(new ArrayList<>())
                .build();
    }

    @Test
    void updateInvoiceWithItems_shouldRemoveItemsAndFiles() {
        // Arrange
        String itemUuidToRemove = UUID.randomUUID().toString();
        ProFormaInvoiceItemEntity itemToRemove = ProFormaInvoiceItemEntity.newBuilder()
                .id(100L)
                .uuid(itemUuidToRemove)
                .fileUrl("http://example.com/file.pdf")
                .build();
        existingInvoice.getProFormaInvoiceItemEntities().add(itemToRemove);

        when(glassTypeRepository.findAllByTenantEntity_Uuid(tenantUuid)).thenReturn(Collections.emptyList());
        when(glassSpecificationRepository.findAllByTenantEntity_Uuid(tenantUuid)).thenReturn(Collections.emptyList());
        when(glassThicknessRepository.findAllByTenantEntity_Uuid(tenantUuid)).thenReturn(Collections.emptyList());
        when(proFormaInvoiceRepository.save(any(ProFormaInvoiceEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        ProFormaInvoiceEntity result = proFormaInvoiceService.updateInvoiceWithItems(existingInvoice, updateValue);

        // Assert
        assertTrue(result.getProFormaInvoiceItemEntities().isEmpty());
        verify(pdfService).deleteFileAndParentDirectoryByUrl("http://example.com/file.pdf");
    }

    @Test
    void updateInvoiceWithItems_shouldUpdateExistingItem() {
        // Arrange
        String itemUuid = UUID.randomUUID().toString();
        ProFormaInvoiceItemEntity existingItem = ProFormaInvoiceItemEntity.newBuilder()
                .id(100L)
                .uuid(itemUuid)
                .quantity(5)
                .optimizeBucket(5)
                .build();
        existingInvoice.getProFormaInvoiceItemEntities().add(existingItem);
        existingInvoice.setWorkOrderEntity(new WorkOrderEntity()); // Needed for logic

        ProFormaInvoiceItemValue itemValue = ProFormaInvoiceItemValue.newBuilder()
                .uuid(itemUuid)
                .quantity(10)
                .glassTypeUuid("gt1")
                .glassSpecificationUuid("gs1")
                .glassThicknessUuid("gth1")
                .build();
        updateValue.getProFormaInvoiceItems().add(itemValue);

        // Mock maps
        GlassTypeEntity glassType = GlassTypeEntity.newBuilder().build(); glassType.setUuid("gt1");
        GlassSpecificationEntity glassSpec = GlassSpecificationEntity.newBuilder().build(); glassSpec.setUuid("gs1");
        GlassThicknessEntity glassThick = GlassThicknessEntity.newBuilder().build(); glassThick.setUuid("gth1");

        when(glassTypeRepository.findAllByTenantEntity_Uuid(tenantUuid)).thenReturn(List.of(glassType));
        when(glassSpecificationRepository.findAllByTenantEntity_Uuid(tenantUuid)).thenReturn(List.of(glassSpec));
        when(glassThicknessRepository.findAllByTenantEntity_Uuid(tenantUuid)).thenReturn(List.of(glassThick));
        when(proFormaInvoiceRepository.save(any(ProFormaInvoiceEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        ProFormaInvoiceEntity result = proFormaInvoiceService.updateInvoiceWithItems(existingInvoice, updateValue);

        // Assert
        assertEquals(1, result.getProFormaInvoiceItemEntities().size());
        ProFormaInvoiceItemEntity updatedItem = result.getProFormaInvoiceItemEntities().get(0);
        assertEquals(10, updatedItem.getQuantity());
        // Verify optimizeBucket logic: 5 (initial) + (10 - 5) = 10
        assertEquals(10, updatedItem.getOptimizeBucket());
    }

    @Test
    void updateInvoiceWithItems_shouldAddNewItem() {
        // Arrange
        String newItemUuid = UUID.randomUUID().toString();
        ProFormaInvoiceItemValue newItemValue = ProFormaInvoiceItemValue.newBuilder()
                .uuid(newItemUuid)
                .quantity(5)
                .glassTypeUuid("gt1")
                .glassSpecificationUuid("gs1")
                .glassThicknessUuid("gth1")
                .build();
        updateValue.getProFormaInvoiceItems().add(newItemValue);

        when(glassTypeRepository.findAllByTenantEntity_Uuid(tenantUuid)).thenReturn(Collections.emptyList());
        when(glassSpecificationRepository.findAllByTenantEntity_Uuid(tenantUuid)).thenReturn(Collections.emptyList());
        when(glassThicknessRepository.findAllByTenantEntity_Uuid(tenantUuid)).thenReturn(Collections.emptyList());

        when(tenantRepository.findByUuid(tenantUuid)).thenReturn(TenantEntity.newBuilder().build());
        when(glassTypeRepository.findByTenantEntity_UuidAndGlassTypeUuid(anyString(), anyString())).thenReturn(GlassTypeEntity.newBuilder().build());
        when(glassThicknessRepository.findByTenantEntity_UuidAndGlassThicknessUuid(anyString(), anyString())).thenReturn(GlassThicknessEntity.newBuilder().build());
        when(glassSpecificationRepository.findByTenantEntity_UuidAndGlassSpecificationUuid(anyString(), anyString())).thenReturn(GlassSpecificationEntity.newBuilder().build());
        when(proFormaInvoiceRepository.save(any(ProFormaInvoiceEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        ProFormaInvoiceEntity result = proFormaInvoiceService.updateInvoiceWithItems(existingInvoice, updateValue);

        // Assert
        assertEquals(1, result.getProFormaInvoiceItemEntities().size());
        assertEquals(newItemUuid, result.getProFormaInvoiceItemEntities().get(0).getUuid());
    }

    @Test
    void updateInvoiceWithItems_shouldUpdateFields() {
        // Arrange
        updateValue = updateValue.toBuilder()
                .proFormaInvoiceAmount(100.0)
                .payableAmount(105.0f)
                .build();

        when(glassTypeRepository.findAllByTenantEntity_Uuid(tenantUuid)).thenReturn(Collections.emptyList());
        when(glassSpecificationRepository.findAllByTenantEntity_Uuid(tenantUuid)).thenReturn(Collections.emptyList());
        when(glassThicknessRepository.findAllByTenantEntity_Uuid(tenantUuid)).thenReturn(Collections.emptyList());
        when(proFormaInvoiceRepository.save(any(ProFormaInvoiceEntity.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        ProFormaInvoiceEntity result = proFormaInvoiceService.updateInvoiceWithItems(existingInvoice, updateValue);

        // Assert
        assertEquals(100.0, result.getProFormaInvoiceAmount());
        assertEquals(105.0f, result.getPayableAmount());
    }
}
