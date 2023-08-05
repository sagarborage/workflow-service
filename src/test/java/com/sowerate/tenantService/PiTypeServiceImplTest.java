/**
 * Test package.
 */
package com.sowerate.tenantService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.sowermate.tenantService.entities.PiTypeEntity;
import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.TenantEntity;
//import com.sowermate.tenantService.entities.value.PiTypeValue;
import com.sowermate.tenantService.repositories.PiTypeRepository;
import com.sowermate.tenantService.repositories.ProFormaInvoiceRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.impl.PiTypeServiceImpl;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Vithoba Hipparkar
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//public class PiTypeServiceImplTest {
//    public static String TEST_UUID = "91e6aeb1-0718-458e-aebd-dd5651d8444b";
//    @InjectMocks
//    private PiTypeServiceImpl mockPiTypeServiceImpl;
//    @Mock
//    private PiTypeRepository mockPiTypeRepository;
//    @Mock
//    private ProFormaInvoiceRepository mockProFormaInvoiceRepository;
//    @Mock
//    private TenantRepository mockTenantRepository;
//
//    @Before
//    public void init() {
//
//        MockitoAnnotations.initMocks(this);
//    }

    /**
     * Tests {@link PiTypeServiceImpl#createPiType(PiTypeValue)}
     * when valid {@link PiTypeValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while createPiType.
     */
//    @Test
//    public void testCreatePiTypeWhenPiTypeValueIsPassedExpectPiTypeValueSaved() throws Exception {
//        //given
//        PiTypeValue piTypeValue = preparePiTypeValue();
//        PiTypeEntity piTypeEntity = preparePiTypeEntity();
//        when(mockPiTypeRepository.save(any(PiTypeEntity.class))).thenReturn(piTypeEntity);
//
//        //for ProFormaInvoiceEntity
//        ProFormaInvoiceEntity proFormaInvoiceEntity = prepareProFormaInvoiceEntity();
//        List<ProFormaInvoiceEntity> proFormaInvoiceEntities = new ArrayList<ProFormaInvoiceEntity>();
//        proFormaInvoiceEntities.add(proFormaInvoiceEntity);
//
//        when(mockProFormaInvoiceRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(proFormaInvoiceEntities);
//
//        //for TenantEntity
//        TenantEntity tenantEntity = preparePiTypeEntity().getTenantEntity();
//        List<TenantEntity> tenantEntities = new ArrayList<TenantEntity>();
//        tenantEntities.add(tenantEntity);
//
//        when(mockTenantRepository.findByTenantUuid(anyString())).thenReturn(tenantEntity);
//        //when
//        PiTypeValue result = mockPiTypeServiceImpl.createPiType(piTypeValue);
//        //then
//        assertNotNull(result);
//        assertEquals(TEST_UUID, result.getPiTypeUuid());
//    }

    /**
     * Tests {@link PiTypeServiceImpl#editPiType(PiTypeValue)}
     * when valid {@link PiTypeValue} object is passed, expect the record updated in the database.
     *
     * @throws Exception when there is problem while fetching editPiType.
     */
   /* @Test
    public void testEditPiTypeWhenPiTypeValueIsPassedExpectAddressValueUpdated() throws Exception {
        //given
        PiTypeValue piTypeValue = preparePiTypeValue();
        PiTypeEntity piTypeEntity = preparePiTypeEntity();
        when(mockPiTypeRepository.save(any(PiTypeEntity.class))).thenReturn(piTypeEntity);
        List<PiTypeEntity> entities = new ArrayList<PiTypeEntity>();
        entities.add(piTypeEntity);
        when(mockPiTypeRepository.findByUuid(any())).thenReturn(entities);

        //for ProFormaInvoiceEntity
        ProFormaInvoiceEntity proFormaInvoiceEntity = prepareProFormaInvoiceEntity();
        List<ProFormaInvoiceEntity> proFormaInvoiceEntities = new ArrayList<ProFormaInvoiceEntity>();
        proFormaInvoiceEntities.add(proFormaInvoiceEntity);
        when(mockProFormaInvoiceRepository.findAllByTenantEntity_Uuid(any())).thenReturn(proFormaInvoiceEntities);

        //for TenantEntity
        TenantEntity tenantEntity = prepareTenantEntity();
        List<TenantEntity> tenantEntities = new ArrayList<>();
        tenantEntities.add(tenantEntity);
        when(mockTenantRepository.findByTenantUuid(any())).thenReturn(tenantEntity);

        //when
        PiTypeValue result = mockPiTypeServiceImpl.editPiType(piTypeValue);
        //then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getPiTypeUuid());
    }

    /**
     * Tests {@link PiTypeServiceImpl#getPiType(String, String)}
     * when valid {@link PiTypeValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getPiType by using UUID.
     */
//    @Test
//    public void testGetPiType_when_requiredParameterPassed_ExpectPiTypeValue() throws Exception {
//        //given
//        PiTypeEntity piTypeEntity = PiTypeEntity.builder()
//                .piTypeId(1)
//                .piTypeUuid(TEST_UUID)
//                .mm(3)
//                .sqft(4)
//                .tenantEntity(new TenantEntity())
//                .build();
//
//        when(mockPiTypeRepository.findByTenantEntity_UuidAndPiTypeUuid(anyString(), anyString())).thenReturn(piTypeEntity);
//        //when
//        PiTypeValue result = mockPiTypeServiceImpl.getPiType(TEST_UUID, TEST_UUID);
//        //then
//        assertNotNull(result);
//        assertEquals(TEST_UUID, result.getPiTypeUuid());
//        assertEquals(TEST_UUID, result.getTenantUuid());
//    }

    /**
     * Tests {@link PiTypeServiceImpl#getAllPiType(String)}
     * when valid object is passed, expect the all records get in the database.
     *
     * @throws Exception when there is problem while fetching getAllPiType by using UUID.
     */
    //@Test
//    public void testGetAllPiTypeWhenRequestIsPassedExpectGetAllPiType() throws Exception {
//        // given
//        PiTypeEntity piTypeEntity = preparePiTypeEntity();
//        List<PiTypeEntity> entities = new ArrayList<PiTypeEntity>();
//        entities.add(piTypeEntity);
//        when(mockPiTypeRepository.findAllByTenantEntity_Uuid(anyString())).thenReturn(entities);
//        // when
//        List<PiTypeValue> result = mockPiTypeServiceImpl.getAllPiType(TEST_UUID);
//        // then
//        assertThat(result).isNotNull().isNotEmpty();
//        assertThat(result.get(0).getPiTypeUuid()).isEqualTo(TEST_UUID);
//    }

    /**
     * Tests {@link PiTypeServiceImpl#deletePiType(String, String)}
     * when valid {@link PiTypeValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is a problem while fetching deletePiType by using UUID.
     */
   // @Test
    //public void testDeletePiTypeWhenPiTypeUuidIsPassedExpectPiTypeValueDelete() throws Exception {
        // given
        //PiTypeEntity piTypeEntity = preparePiTypeEntity();
       // List<PiTypeEntity> entities = Arrays.asList();

       // when(mockPiTypeRepository.deleteByPiTypeUuid(any())).thenReturn(1);
        // when
        //int recordCount = mockPiTypeServiceImpl.deletePiType(TEST_UUID, TEST_UUID);
        // then
        //assertEquals(1, recordCount);
    //}

//    private PiTypeValue preparePiTypeValue() {
//        PiTypeValue piTypeValue = new PiTypeValue();
//        piTypeValue.setPiTypeUuid(TEST_UUID);
//        return piTypeValue;
//    }
//
//    private PiTypeEntity preparePiTypeEntity() {
//        return PiTypeEntity.builder()
//                .piTypeUuid(TEST_UUID)
//                .build();
//    }
//
//    private ProFormaInvoiceEntity prepareProFormaInvoiceEntity() {
//        ProFormaInvoiceEntity proFormaInvoiceEntity = new ProFormaInvoiceEntity();
//        proFormaInvoiceEntity.setProFormInvoiceUuid(TEST_UUID);
//        return proFormaInvoiceEntity;
//    }
//
//    private TenantEntity prepareTenantEntity() {
//        TenantEntity tenantEntity = new TenantEntity();
//        tenantEntity.setUuid(TEST_UUID);
//        tenantEntity.setIsActive(true);
//        return tenantEntity;
//    }
//}
