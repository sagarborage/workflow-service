/**
 * Test package.
 *
 */
package com.sowermate.tenantService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import com.sowermate.tenantService.entities.AdditionalChargesEntity;
import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.AdditionalChargesValue;
import com.sowermate.tenantService.repositories.AdditionalChargesRepository;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.impl.AdditionalChargesServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Anil Jadhav
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class AdditionalChargesServiceImplTest {
    public static final String TEST_UUID="fa9d7f05-a989-4551-b991-0128813df748";
    public static final String TEST_UUID1="d3620386-1913-4e03-a93b-3f285b255e7a";

    @InjectMocks
    private AdditionalChargesServiceImpl mockAdditionalChargesServiceImpl;
    @Mock
    private AdditionalChargesRepository mockAdditionalChargesRepository;
    @Mock
    private TenantRepository mockTenantRepository;
    @Before
    public  void init(){

        MockitoAnnotations.initMocks(this);
    }
    /**
     * Tests {@link AdditionalChargesServiceImpl#saveAdditionalCharges(AdditionalChargesValue)}
     * when a valid {@link AdditionalChargesValue} object is passed, expecting the record to be saved in the database.
     *
     * @throws Exception when there is a problem while saving additional charges.
     */
    @Test
    public void testCreateAdditionalChargesWhenValidAdditionalChargesValueIsPassedExpectAdditionalChargesValueSaved() throws Exception {
        // given
        AdditionalChargesValue additionalChargesValue = prepareAdditionalChargesValue();
        AdditionalChargesEntity additionalChargesEntity = prepareAdditionalChargesEntity();
        when(mockAdditionalChargesRepository.save(any(AdditionalChargesEntity.class))).thenReturn(additionalChargesEntity);

        //for TenantEntity
        TenantEntity tenantEntity=prepareTenantEntity();
        List<TenantEntity> tenantEntities = new ArrayList<TenantEntity>();
        tenantEntities.add(tenantEntity);

        when(mockTenantRepository.findByUuid(anyString())).thenReturn(tenantEntity);

        // when
        AdditionalChargesValue result = mockAdditionalChargesServiceImpl.createAdditionalCharges(additionalChargesValue);

        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());
    }
    /**
     * Tests {@link AdditionalChargesServiceImpl#editAdditionalCharges(AdditionalChargesValue)}
     * when a valid {@link AdditionalChargesValue} object is passed, expecting the record to be updated in the database.
     *
     * @throws Exception when there is a problem while fetching editAdditionalCharges.
     */
    @Test
    public void testEditAdditionalChargesWhenValidAdditionalChargesValueIsPassedExpectAdditionalChargesValueUpdated() throws Exception {
        // given
        AdditionalChargesValue additionalChargesValue = prepareAdditionalChargesValue();
        AdditionalChargesEntity additionalChargesEntity = prepareAdditionalChargesEntity();
        when(mockAdditionalChargesRepository.save(any(AdditionalChargesEntity.class))).thenReturn(additionalChargesEntity);
        List<AdditionalChargesEntity> additionalChargesEntities = new ArrayList<>();
        additionalChargesEntities.add(additionalChargesEntity);
        when(mockAdditionalChargesRepository.findByUuid(any())).thenReturn(additionalChargesEntities);
        //for AdTenantEntity
        TenantEntity tenantEntity=prepareTenantEntity();
        List<TenantEntity> tenantEntities=new ArrayList<>();
        tenantEntities.add(tenantEntity);
        when(mockTenantRepository.findByUuid(any())).thenReturn(tenantEntity);
        // when
        AdditionalChargesValue result = mockAdditionalChargesServiceImpl.editAdditionalCharges(additionalChargesValue);

        // then1
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());
    }
    /**
     * Tests {@link AdditionalChargesServiceImpl#getAdditionalCharges(String)}
     * when valid {@link AdditionalChargesValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getAdditionalCharges by using UUID.
     */
    @Test
    public void testGetAdditionalChargesWhenAdditionalChargesUuidIsPassedExpectAdditionalChargesValue() throws Exception{
        //given
        AdditionalChargesEntity additionalChargesEntity=prepareAdditionalChargesEntity();

        List<AdditionalChargesEntity> mockedAdditionalChargesEntities = new ArrayList<>();
        mockedAdditionalChargesEntities.add(additionalChargesEntity);

        when(mockAdditionalChargesRepository.findByUuid(any())).thenReturn(mockedAdditionalChargesEntities);
        //when
        AdditionalChargesValue result=mockAdditionalChargesServiceImpl.getAdditionalCharges(TEST_UUID);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link AdditionalChargesServiceImpl#getAllAdditionalCharges()}
     * when valid object is passed, expect the all records get in the database.
     *
     * @throws Exception when there is problem while fetching getAllAdditionalCharges by using UUID.
     */
    @Test
    public void testGetAllAdditionalChargesWhenRequestIsPassedExpectGetAllAdditionalCharges() throws Exception {
        // given
        AdditionalChargesEntity additionalChargesEntity = prepareAdditionalChargesEntity();
        List<AdditionalChargesEntity> entities = new ArrayList<AdditionalChargesEntity>();
        entities.add(additionalChargesEntity);
        when(mockAdditionalChargesRepository.findAll()).thenReturn(entities);

        // when
        List<AdditionalChargesValue> result = mockAdditionalChargesServiceImpl.getAllAdditionalCharges();

        // then
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.get(0).getUuid()).isEqualTo(TEST_UUID);
    }
    /**
     * Tests {@link AdditionalChargesServiceImpl#deleteAdditionalCharges(String)}
     * when valid {@link AdditionalChargesValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is a problem while fetching deleteAdditionalCharges by using UUID.
     */
    @Test
    public void testDeleteAdditionalChargesWhenAdditionalChargesUuidIsPassedExpectAdditionalChargesValueDelete() throws Exception {
        // given
        AdditionalChargesEntity additionalChargesEntity = prepareAdditionalChargesEntity();
        List<AdditionalChargesEntity> entities = new ArrayList<AdditionalChargesEntity>();
        entities.add(additionalChargesEntity);
        when(mockAdditionalChargesRepository.deleteAdditionalByUuid(any())).thenReturn(entities);

        // when
        AdditionalChargesValue result = mockAdditionalChargesServiceImpl.deleteAdditionalCharges(TEST_UUID);

        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());
    }

    private AdditionalChargesValue prepareAdditionalChargesValue(){
        AdditionalChargesValue additionalChargesValue=new AdditionalChargesValue();
        additionalChargesValue.setUuid(TEST_UUID);
        return additionalChargesValue;
    }
    private AdditionalChargesEntity prepareAdditionalChargesEntity(){
        AdditionalChargesEntity additionalChargesEntity=new AdditionalChargesEntity();
        additionalChargesEntity.setUuid(TEST_UUID);
        return additionalChargesEntity;
    }

    private TenantEntity prepareTenantEntity() {
        TenantEntity tenantEntity = new TenantEntity();
        tenantEntity.setUuid(TEST_UUID1);
        tenantEntity.setIsActive(true);
        return tenantEntity;
    }
}



