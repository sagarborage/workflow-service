/**
 * Test package.
 */
package com.sowerate.tenantService;

import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.value.TenantValue;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.impl.TenantServiceImpl;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Before;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vithoba Hipparkar
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class TenantServiceImplTest {
    public static String TEST_UUID = "a91411ca-82bc-44ea-8e6b-a05bb1994fc7";
    @InjectMocks
    private TenantServiceImpl mockTenantServiceImpl;

    @Mock
    private TenantRepository mockTenantRepository;
    @Mock
    private CompanyRepository mockCompanyRepository;
    @Mock
    private AdditionalChargesRepository mockAdditionalChargesRepository;
    @Mock
    private ServiceRateRepository mockServiceRateRepository;
    @Mock
    private GlassTypeRepository mockGlassTypeRepository;
    @Mock
    private GlassThicknessRepository mockGlassThicknessRepository;
    @Mock
    private PiTypeRepository mockPiTypeRepository;
    @Mock
    private ConfirmThroughRepository mockConfirmThroughRepository;
    @Mock
    private CompanyTypeRepository mockCompanyTypeRepository;
    @Mock
    private AddressRepository mockAddressRepository;
    @Mock
    private ProFormaInvoiceRepository mockProFormaInvoiceRepository;
    @Mock
    private ProFormaInvoiceItemRepository mockProFormaInvoiceItemRepository;
    @Mock
    private StatusRepository mockStatusRepository;
    @Mock
    private ServiceRateInvoiceRepository mockServiceRateInvoiceRepository;
    @Mock
    private AddressTypeRepository mockAddressTypeRepository;
/*    @Mock
    private CompanyAddressRepository mockCompanyAddressRepository;*/

    @Before
    public void init() {

        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests {@link TenantServiceImpl#saveTenantDetails(com.sowermate.tenantService.entities.value.TenantValue)}
     * when valid {@link com.sowermate.tenantService.entities.value.TenantValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while saveTenantDetails.
     */
    @Test
    public void testSaveTenantDetailsWhenTenantValueIsPassedExpectTenantDetailsSaved() {
        //given
        TenantValue tenantValue = prepareTenantValue();
        TenantEntity tenantEntity = prepareTenantEntity();
        when(mockTenantRepository.save(any(TenantEntity.class))).thenReturn(tenantEntity);

        //for CompanyEntity
/*        CompanyEntity companyEntity = prepareCompanyEntity();
        List<CompanyEntity> companyEntities = new ArrayList<CompanyEntity>();
        companyEntities.add(companyEntity);*/

        //when(mockCompanyRepository.findAllByTenantEntityTenantId(anyInt())).thenReturn(companyEntities);

        //for AdditionalChargesEntity
/*        AdditionalChargesEntity additionalChargesEntity = prepareAdditionalChargesEntity();
        List<AdditionalChargesEntity> additionalChargesEntities = new ArrayList<AdditionalChargesEntity>();
        additionalChargesEntities.add(additionalChargesEntity);

        //when(mockAdditionalChargesRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(additionalChargesEntities);

        //for ServiceRateEntity
        ServiceRateEntity serviceRateEntity = prepareServiceRateEntity();
        List<ServiceRateEntity> serviceRateEntities = new ArrayList<ServiceRateEntity>();
        serviceRateEntities.add(serviceRateEntity);

        when(mockServiceRateRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(serviceRateEntities);

        //for GlassTypeEntity
        GlassTypeEntity glassTypeEntity = prepareGlassTypeEntity();
        List<GlassTypeEntity> glassTypeEntities = new ArrayList<GlassTypeEntity>();
        glassTypeEntities.add(glassTypeEntity);

        when(mockGlassTypeRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(glassTypeEntities);

        //for GlassThicknessEntity
        GlassThicknessEntity glassThicknessEntity = prepareGlassThicknessEntity();
        List<GlassThicknessEntity> glassThicknessEntities = new ArrayList<GlassThicknessEntity>();
        glassThicknessEntities.add(glassThicknessEntity);

        when(mockGlassThicknessRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(glassThicknessEntities);

        //for PiTypeEntity
        PiTypeEntity piTypeEntity = preparePiTypeEntity();
        List<PiTypeEntity> piTypeEntities = new ArrayList<PiTypeEntity>();
        piTypeEntities.add(piTypeEntity);

        when(mockPiTypeRepository.findAllByTenantEntityUuid(anyString())).thenReturn(piTypeEntities);

        //for ConfirmThroughEntity
        ConfirmThroughEntity confirmThroughEntity = prepareConfirmThroughEntity();
        List<ConfirmThroughEntity> confirmThroughEntities = new ArrayList<ConfirmThroughEntity>();
        confirmThroughEntities.add(confirmThroughEntity);

        when(mockConfirmThroughRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(confirmThroughEntities);

        //for CompanyTypeEntity
        CompanyTypeEntity companyTypeEntity = prepareCompanyTypeEntity();
        List<CompanyTypeEntity> companyTypeEntities = new ArrayList<CompanyTypeEntity>();
        companyTypeEntities.add(companyTypeEntity);

        //when(mockCompanyTypeRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(companyTypeEntities);

        //for AddressEntity
        AddressEntity addressEntity = prepareAddressEntity();
        List<AddressEntity> addressEntities = new ArrayList<AddressEntity>();
        addressEntities.add(addressEntity);

        //when(mockAddressRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(addressEntities);

        //for ProFormaInvoiceEntity
        ProFormaInvoiceEntity proFormaInvoiceEntity = prepareProFormaInvoiceEntity();
        List<ProFormaInvoiceEntity> proFormaInvoiceEntities = new ArrayList<ProFormaInvoiceEntity>();
        proFormaInvoiceEntities.add(proFormaInvoiceEntity);

        //when(mockProFormaInvoiceRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(proFormaInvoiceEntities);

        //for ProFormaInvoiceItemEntity
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = prepareProFormaInvoiceItemEntity();
        List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntities = new ArrayList<ProFormaInvoiceItemEntity>();
        proFormaInvoiceItemEntities.add(proFormaInvoiceItemEntity);

        when(mockProFormaInvoiceItemRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(proFormaInvoiceItemEntities);

        //for StatusEntity
        StatusEntity statusEntity = prepareStatusEntity();
        List<StatusEntity> statusEntities = new ArrayList<StatusEntity>();
        statusEntities.add(statusEntity);

        when(mockStatusRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(statusEntities);

        //for ServiceRateInvoiceEntity
        ServiceRateInvoiceEntity serviceRateInvoiceEntity = prepareServiceRateInvoiceEntity();
        List<ServiceRateInvoiceEntity> serviceRateInvoiceEntities = new ArrayList<ServiceRateInvoiceEntity>();
        serviceRateInvoiceEntities.add(serviceRateInvoiceEntity);

        //when(mockServiceRateInvoiceRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(serviceRateInvoiceEntities);

        //for AddressTypeEntity
        AddressTypeEntity addressTypeEntity = prepareAddressTypeEntity();
        List<AddressTypeEntity> addressTypeEntities = new ArrayList<AddressTypeEntity>();
        addressTypeEntities.add(addressTypeEntity);*/

        //when(mockAddressTypeRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(addressTypeEntities);

        //for CompanyAddressEntity
        //CompanyAddressEntity companyAddressEntity = prepareCompanyAddressEntity();
        //List<CompanyAddressEntity> companyAddressEntities = new ArrayList<CompanyAddressEntity>();
        //companyAddressEntities.add(companyAddressEntity);

        //when(mockCompanyAddressRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(companyAddressEntities);

        //when
        TenantValue result = mockTenantServiceImpl.saveTenantDetails(tenantValue);
        //then
        assertNotNull(result);
        //assertEquals(TEST_UUID, result.getTenantUuid());
    }

    /**
     * Tests {@link TenantServiceImpl#editTenantDetails(TenantValue)}
     * when valid {@link TenantValue} object is passed, expect the record updated in the database.
     *
     * @throws Exception when there is problem while fetching editTenantDetails.
     */
    @Test
    public void testEditTenantDetailsWhenTenantValueIsPassedExpectAddressValueUpdated() {
        //given
        TenantValue tenantValue = prepareTenantValue();
        TenantEntity tenantEntity = prepareTenantEntity();
        when(mockTenantRepository.save(any(TenantEntity.class))).thenReturn(tenantEntity);
        List<TenantEntity> entities = new ArrayList<TenantEntity>();
        entities.add(tenantEntity);
        //when(mockTenantRepository.findByTenantUuid(any())).thenReturn(tenantEntity);

        //for CompanyEntity
        //CompanyEntity companyEntity = prepareCompanyEntity();
        //List<CompanyEntity> companyEntities = new ArrayList<CompanyEntity>();
        //companyEntities.add(companyEntity);
        //when(mockCompanyRepository.findAllByTenantEntityTenantId(anyInt())).thenReturn(companyEntities);

        //for AdditionalChargesEntity
/*        AdditionalChargesEntity additionalChargesEntity = prepareAdditionalChargesEntity();
        List<AdditionalChargesEntity> additionalChargesEntities = new ArrayList<AdditionalChargesEntity>();
        additionalChargesEntities.add(additionalChargesEntity);
        //when(mockAdditionalChargesRepository.findAllByTenantEntity_Uuid(any())).thenReturn(additionalChargesEntities);

        //for ServiceRateEntity
        ServiceRateEntity serviceRateEntity = prepareServiceRateEntity();
        List<ServiceRateEntity> serviceRateEntities = new ArrayList<ServiceRateEntity>();
        serviceRateEntities.add(serviceRateEntity);
        when(mockServiceRateRepository.findAllByTenantEntity_Uuid(any())).thenReturn(serviceRateEntities);

        //for GlassTypeEntity
        GlassTypeEntity glassTypeEntity = prepareGlassTypeEntity();
        List<GlassTypeEntity> glassTypeEntities = new ArrayList<GlassTypeEntity>();
        glassTypeEntities.add(glassTypeEntity);
        when(mockGlassTypeRepository.findAllByTenantEntity_Uuid(any())).thenReturn(glassTypeEntities);

        //for GlassThicknessEntity
        GlassThicknessEntity glassThicknessEntity = prepareGlassThicknessEntity();
        List<GlassThicknessEntity> glassThicknessEntities = new ArrayList<GlassThicknessEntity>();
        glassThicknessEntities.add(glassThicknessEntity);
        when(mockGlassThicknessRepository.findAllByTenantEntity_Uuid(any())).thenReturn(glassThicknessEntities);

        //for PiTypeEntity
        PiTypeEntity piTypeEntity = preparePiTypeEntity();
        List<PiTypeEntity> piTypeEntities = new ArrayList<PiTypeEntity>();
        piTypeEntities.add(piTypeEntity);
        when(mockPiTypeRepository.findAllByTenantEntityUuid(any())).thenReturn(piTypeEntities);

        //for ConfirmThroughEntity
        ConfirmThroughEntity confirmThroughEntity = prepareConfirmThroughEntity();
        List<ConfirmThroughEntity> confirmThroughEntities = new ArrayList<ConfirmThroughEntity>();
        confirmThroughEntities.add(confirmThroughEntity);
        when(mockConfirmThroughRepository.findAllByTenantEntity_Uuid(any())).thenReturn(confirmThroughEntities);

        //for CompanyTypeEntity
        CompanyTypeEntity companyTypeEntity = prepareCompanyTypeEntity();
        List<CompanyTypeEntity> companyTypeEntities = new ArrayList<CompanyTypeEntity>();
        companyTypeEntities.add(companyTypeEntity);
        //when(mockCompanyTypeRepository.findAllByTenantEntity_Uuid(any())).thenReturn(companyTypeEntities);

        //for AddressEntity
        AddressEntity addressEntity = prepareAddressEntity();
        List<AddressEntity> addressEntities = new ArrayList<AddressEntity>();
        addressEntities.add(addressEntity);
        //when(mockAddressRepository.findAllByTenantEntity_Uuid(any())).thenReturn(addressEntities);

        //for ProFormaInvoiceEntity
        ProFormaInvoiceEntity proFormaInvoiceEntity = prepareProFormaInvoiceEntity();
        List<ProFormaInvoiceEntity> proFormaInvoiceEntities = new ArrayList<ProFormaInvoiceEntity>();
        proFormaInvoiceEntities.add(proFormaInvoiceEntity);
        //when(mockProFormaInvoiceRepository.findAllByTenantEntity_Uuid(any())).thenReturn(proFormaInvoiceEntities);

        //for ProFormaInvoiceItemEntity
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = prepareProFormaInvoiceItemEntity();
        List<ProFormaInvoiceItemEntity> proFormaInvoiceItemEntities = new ArrayList<ProFormaInvoiceItemEntity>();
        proFormaInvoiceItemEntities.add(proFormaInvoiceItemEntity);
        when(mockProFormaInvoiceItemRepository.findAllByTenantEntity_Uuid(any())).thenReturn(proFormaInvoiceItemEntities);

        //for StatusEntity
        StatusEntity statusEntity = prepareStatusEntity();
        List<StatusEntity> statusEntities = new ArrayList<StatusEntity>();
        statusEntities.add(statusEntity);
        when(mockStatusRepository.findAllByTenantEntity_Uuid(any())).thenReturn(statusEntities);

        //for ServiceRateEntity
        ServiceRateEntity serviceRateEntity1 = prepareServiceRateEntity();
        List<ServiceRateEntity> serviceRateEntities1 = new ArrayList<ServiceRateEntity>();
        serviceRateEntities1.add(serviceRateEntity1);
        when(mockServiceRateRepository.findAllByTenantEntity_Uuid(any())).thenReturn(serviceRateEntities1);

        //for AddressTypeEntity
        AddressTypeEntity addressTypeEntity = prepareAddressTypeEntity();
        List<AddressTypeEntity> addressTypeEntities = new ArrayList<AddressTypeEntity>();
        addressTypeEntities.add(addressTypeEntity);
        when(mockAddressTypeRepository.findAllByTenantEntity_Uuid(any())).thenReturn(addressTypeEntities);*/

        //for CompanyAddressEntity
        //CompanyAddressEntity companyAddressEntity = prepareCompanyAddressEntity();
        //List<CompanyAddressEntity> companyAddressEntities = new ArrayList<CompanyAddressEntity>();
        //companyAddressEntities.add(companyAddressEntity);
        //when(mockCompanyAddressRepository.findAllByTenantEntity_Uuid(any())).thenReturn(companyAddressEntities);

        //when
        TenantValue result = mockTenantServiceImpl.editTenantDetails(tenantValue);
        //then
        assertNotNull(result);
        //assertEquals(TEST_UUID, result.getTenantUuid());
    }

    /**
     * Tests {@link TenantServiceImpl#getTenantDetails(String)}
     * when valid {@link TenantValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getTenantDetails by using UUID.
     */
    @Test
    public void testGetTenantDetailsWhenTenantUuidIsPassedExpectTenantDetailsValueGet() {
        //given
        TenantEntity tenantEntity = prepareTenantEntity();
        List<TenantEntity> mockedTenantEntities = new ArrayList<>();
        mockedTenantEntities.add(tenantEntity);
        //when(mockTenantRepository.findByTenantUuid(anyString())).thenReturn(tenantEntity);
        //when
        TenantValue result = mockTenantServiceImpl.getTenantDetails(TEST_UUID);
        //then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());
    }

    /**
     * Tests {@link TenantServiceImpl#getAllTenantDetails()}
     * when valid object is passed, expect the all records get in the database.
     *
     * @throws Exception when there is problem while fetching getAllTenantDetails by using UUID.
     */
    @Test
    public void testGetAllTenantDetailsWhenRequestIsPassedExpectGetAllTenantDetailsType() {
        // given
        TenantEntity tenantEntity = prepareTenantEntity();
        List<TenantEntity> entities = new ArrayList<TenantEntity>();
        entities.add(tenantEntity);
        //when(mockTenantRepository.findByTenantUuid(anyString())).thenReturn(tenantEntity);
        // when
        List<TenantValue> result = mockTenantServiceImpl.getAllTenantDetails();
        // then
        //assertThat(result).isNotNull().isNotEmpty();
        //assertThat(result.get(0).getTenantUuid()).isEqualTo(TEST_UUID);
    }

    /**
     * Tests {@link TenantServiceImpl#deleteTenantDetails(String)}
     * when valid {@link TenantValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is a problem while fetching deleteTenantDetails by using UUID.
     */
   /* @Test
    public void testDeleteTenantDetailsWhenTenantUuidIsPassedExpectTenantValueDelete() {
        // given
        TenantEntity tenantEntity = prepareTenantEntity();
        List<TenantEntity> entities = new ArrayList<TenantEntity>();
        entities.add(tenantEntity);
        when(mockTenantRepository.deleteByTenantUuid(any())).thenReturn(1);
        // when
        TenantValue result = mockTenantServiceImpl.deleteTenantDetails(TEST_UUID);
        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getTenantUuid());
    }
*/
    private TenantValue prepareTenantValue() {
        return  TenantValue.newBuilder().build().toBuilder().uuid(TEST_UUID).build();
    }

    private TenantEntity prepareTenantEntity() {
        return TenantEntity.newBuilder().build().toBuilder().uuid(TEST_UUID).build();
    }

/*    private AdditionalChargesEntity prepareAdditionalChargesEntity() {
        return AdditionalChargesEntity.newBuilder().build().toBuilder().additionalChargesUuid(TEST_UUID).build();
    }*/

/*    private ServiceRateEntity prepareServiceRateEntity() {
        ServiceRateEntity serviceRateEntity = new ServiceRateEntity();
        serviceRateEntity.setServiceRateUuid(TEST_UUID);
        return serviceRateEntity;
    }*/

/*
    private GlassTypeEntity prepareGlassTypeEntity() {
        GlassTypeEntity glassTypeEntity = new GlassTypeEntity();
        glassTypeEntity.setGlassTypeUuid(TEST_UUID);
        return glassTypeEntity;
    }
*/

/*    private GlassThicknessEntity prepareGlassThicknessEntity() {
        GlassThicknessEntity glassThicknessEntity = new GlassThicknessEntity();
        glassThicknessEntity.setGlassThicknessUuid(TEST_UUID);
        return glassThicknessEntity;
    }

    private PiTypeEntity preparePiTypeEntity() {
        PiTypeEntity piTypeEntity = new PiTypeEntity();
        piTypeEntity.setPiTypeUuid(TEST_UUID);
        return piTypeEntity;
    }

    private ConfirmThroughEntity prepareConfirmThroughEntity() {
        ConfirmThroughEntity confirmThroughEntity = new ConfirmThroughEntity();
        confirmThroughEntity.setConfirmThroughUuid(TEST_UUID);
        return confirmThroughEntity;
    }*/

  /*  private CompanyTypeEntity prepareCompanyTypeEntity() {
        return CompanyTypeEntity.newBuilder().build().toBuilder().companyTypeUuid(TEST_UUID).build();
    }

    private AddressEntity prepareAddressEntity() {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressUuid(TEST_UUID);
        return addressEntity;
    }

    private ProFormaInvoiceEntity prepareProFormaInvoiceEntity() {
        ProFormaInvoiceEntity proFormaInvoiceEntity = new ProFormaInvoiceEntity();
        proFormaInvoiceEntity.setproFormaInvoiceUuid(TEST_UUID);
        return proFormaInvoiceEntity;
    }

    private ProFormaInvoiceItemEntity prepareProFormaInvoiceItemEntity() {
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = new ProFormaInvoiceItemEntity();
        proFormaInvoiceItemEntity.setProFormaInvoiceItemUuid(TEST_UUID);
        return proFormaInvoiceItemEntity;
    }

    private StatusEntity prepareStatusEntity() {
        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setStatusUuid(TEST_UUID);
        return statusEntity;
    }

    private ServiceRateInvoiceEntity prepareServiceRateInvoiceEntity() {
        ServiceRateInvoiceEntity serviceRateInvoiceEntity = new ServiceRateInvoiceEntity();
        serviceRateInvoiceEntity.setServiceRateInvoiceUuid(TEST_UUID);
        return serviceRateInvoiceEntity;
    }

    private AddressTypeEntity prepareAddressTypeEntity() {
        AddressTypeEntity addressTypeEntity = new AddressTypeEntity();
        addressTypeEntity.setAddressTypeUuid(TEST_UUID);
        return addressTypeEntity;
    }

    private CompanyAddressEntity prepareCompanyAddressEntity() {
        CompanyAddressEntity companyAddressEntity = new CompanyAddressEntity();
        companyAddressEntity.setCompanyAddressUuid(TEST_UUID);
        return companyAddressEntity;
    }*/
}
