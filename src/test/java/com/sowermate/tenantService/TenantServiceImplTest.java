/**
 * Test package.
 *
 */
package com.sowermate.tenantService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.sowermate.tenantService.entities.TenantEntity;
import com.sowermate.tenantService.entities.value.TenantDetailsValue;
import com.sowermate.tenantService.repositories.TenantRepository;
import com.sowermate.tenantService.services.impl.TenantServiceImpl;
import org.junit.Before;
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
public class
TenantServiceImplTest {
    public static String TEST_UUID="c62a5fc9-eb97-4f0b-9583-9507171a1754";

    @InjectMocks
    private TenantServiceImpl classUnderTest;

    @Mock
    private TenantRepository mockTenantRepository;
    @Before
    public  void init(){

        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests {@link TenantServiceImpl#saveTenantDetails(TenantDetailsValue)}
     * when valid {@link TenantDetailsValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while saveTenantDetails.
     */
    @Test
    public void testCreateTenantDetailsWhenTenantDetailsValueIsPassedExpectTenantDetailsValueSaved()throws Exception{
        //given
        TenantDetailsValue tenantDetailsValue=prepareTenantDetailsValue();
        TenantEntity tenantEntity=prepareTenantEntity();
        when(mockTenantRepository.save(any(TenantEntity.class))).thenReturn(tenantEntity);

        //when
        TenantDetailsValue result=classUnderTest.saveTenantDetails(tenantDetailsValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link TenantServiceImpl#editTenantDetails(TenantDetailsValue)}
     * when a valid {@link TenantDetailsValue} object is passed, expecting the record to be updated in the database.
     *
     * @throws Exception when there is a problem while fetching editTenantDetails.
     */
    @Test
    public void testEditTenantWhenValidTenantValueIsPassedExpectTenantValueUpdated() throws Exception {
        // given
        TenantDetailsValue tenantDetailsValue = prepareTenantDetailsValue();
        TenantEntity tenantEntity = prepareTenantEntity();
        when(mockTenantRepository.save(any(TenantEntity.class))).thenReturn(tenantEntity);

        // when
        TenantDetailsValue result = classUnderTest.editTenantDetails(tenantDetailsValue);

        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());
    }

    /**
     * Tests {@link TenantServiceImpl#getTenantDetails(String)}
     * when a valid {@link TenantDetailsValue} object is passed, expecting the record to be retrieved from the database.
     *
     * @throws Exception when there is a problem while fetching tenant details by using UUID.
     */
    @Test
    public void testGetTenantDetailsWhenValidTenantDetailsUuidIsPassedExpectTenantDetailsValueRetrieved() throws Exception {
        // given
        TenantEntity tenantEntity = prepareTenantEntity();

        List<TenantEntity> mockedTenantDetailsEntities = new ArrayList<TenantEntity>();
        mockedTenantDetailsEntities.add(tenantEntity);

        when(mockTenantRepository.findByUuid(any())).thenReturn(tenantEntity);

        // when
        TenantDetailsValue result = classUnderTest.getTenantDetails(TEST_UUID);

        // then
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
    public void testGetAllTenantWhenRequestIsPassedExpectGetAllTenant() throws Exception {
        // given
        TenantEntity tenantEntity = prepareTenantEntity();
        List<TenantEntity> entities = new ArrayList<TenantEntity>();
        entities.add(tenantEntity);
        when(mockTenantRepository.findAll()).thenReturn(entities);

        // when
        List<TenantDetailsValue> result = classUnderTest.getAllTenantDetails();

        // then
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.get(0).getUuid()).isEqualTo(TEST_UUID);
    }

    /**
     * Tests {@link TenantServiceImpl#deleteTenantDetails(String)}
     * when valid {@link TenantDetailsValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is a problem while fetching deleteTenantDetails by using UUID.
     */
    @Test
    public void testDeleteTenantDetailsWhenTenantDetailsUuidIsPassedExpectStatusValueTenantDelete() throws Exception {
        // given
        TenantEntity tenantEntity = prepareTenantEntity();

        List<TenantEntity> entities = new ArrayList<TenantEntity>();
        entities.add(tenantEntity);
        when(mockTenantRepository.deleteByUuid(any())).thenReturn(entities);

        // when
        TenantDetailsValue result = classUnderTest.deleteTenantDetails(TEST_UUID);

        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());

    }
    private TenantDetailsValue prepareTenantDetailsValue(){
        TenantDetailsValue tenantDetailsValue=new TenantDetailsValue();
        tenantDetailsValue.setUuid(TEST_UUID);
        return tenantDetailsValue;
    }
    private TenantEntity prepareTenantEntity(){
        TenantEntity tenantEntity=new TenantEntity();
        tenantEntity.setUuid(TEST_UUID);
        return tenantEntity;
    }

}



