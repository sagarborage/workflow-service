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

import com.sowermate.tenantService.entities.ServiceRateEntity;
import com.sowermate.tenantService.entities.value.ServiceRateValue;
import com.sowermate.tenantService.repositories.ServiceRateRepository;
import com.sowermate.tenantService.services.ServiceRateService;
import com.sowermate.tenantService.services.impl.ServiceRateServiceImpl;
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
public class ServiceRateServiceImplTest {
    public static String TEST_UUID="34637e08-fa96-4ff1-aef0-fbb55f222e67";

    @InjectMocks
    private ServiceRateService classUnderTest;

    @Mock
    private ServiceRateRepository mockServiceRateRepository;
    @Before
    public  void init(){

        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests {@link ServiceRateServiceImpl#createServiceRate(ServiceRateValue)}
     * when valid {@link ServiceRateValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while createServiceRateInvoice.
     */
    @Test
    public void testCreateServiceRateWhenServiceRateValueIsPassedExpectServiceRateValueSaved()throws Exception{
        //given
        ServiceRateValue serviceRateValue=prepareServiceRateValue();
        ServiceRateEntity serviceRateEntity=prepareServiceRateEntity();
        when(mockServiceRateRepository.save(any(ServiceRateEntity.class))).thenReturn(serviceRateEntity);

        //when
        ServiceRateValue result=classUnderTest.createServiceRate(serviceRateValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link ServiceRateServiceImpl#editServiceRate(ServiceRateValue)}
     * when valid {@link ServiceRateValue} object is passed, expect the record updated in the database.
     *
     * @throws Exception when there is problem while fetching editServiceRate.
     */
    @Test
    public void testEditServiceRateWhenServiceRateValueIsPassedExpectServiceRateValueUpdated()throws Exception{
        //given
        ServiceRateValue serviceRateValue=prepareServiceRateValue();
        ServiceRateEntity serviceRateEntity=prepareServiceRateEntity();
        when(mockServiceRateRepository.save(any(ServiceRateEntity.class))).thenReturn(serviceRateEntity);
        List<ServiceRateEntity> entities = new ArrayList<ServiceRateEntity>();
        entities.add(serviceRateEntity);
        when(mockServiceRateRepository.findByUuid(any())).thenReturn(entities);

        //when
        ServiceRateValue result=classUnderTest.editServiceRate(serviceRateValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link ServiceRateServiceImpl#getServiceRate(String)}
     * when valid {@link ServiceRateValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getServiceRate by using UUID.
     */
    @Test
    public void testGetServiceRateServiceRateWhenServiceRateServiceRateUuidIsPassedExpectServiceRateValueGet() throws Exception{
        //given
        ServiceRateEntity serviceRateEntity=prepareServiceRateEntity();

        List<ServiceRateEntity> mockedServiceRateEntities = new ArrayList<>();
        mockedServiceRateEntities.add(serviceRateEntity);

        when(mockServiceRateRepository.findByUuid(any())).thenReturn(mockedServiceRateEntities);
        //when
        ServiceRateValue result=classUnderTest.getServiceRate(TEST_UUID);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link ServiceRateServiceImpl#getAllServiceRate()}
     * when valid object is passed, expect the all records get in the database.
     *
     * @throws Exception when there is problem while fetching getAllServiceRate by using UUID.
     */

    @Test
    public void testGetAllServiceRateWhenRequestIsPassedExpectGetAllServiceRate() throws Exception {
        // given
        ServiceRateEntity serviceRateEntity = prepareServiceRateEntity();
        List<ServiceRateEntity> entities = new ArrayList<ServiceRateEntity>();
        entities.add(serviceRateEntity);
        when(mockServiceRateRepository.findAll()).thenReturn(entities);

        // when
        List<ServiceRateValue> result = classUnderTest.getAllServiceRate();

        // then
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.get(0).getUuid()).isEqualTo(TEST_UUID);
    }

    /**
     * Tests {@link ServiceRateServiceImpl#deleteServiceRate(String)}
     * when valid {@link ServiceRateValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is a problem while fetching deleteServiceRate by using UUID.
     */
    @Test
    public void testDeleteServiceRateWhenServiceRateUuidIsPassedExpectServiceRateValueDelete() throws Exception {
        // given
        ServiceRateEntity serviceRateEntity = prepareServiceRateEntity();

        List<ServiceRateEntity> entities = new ArrayList<ServiceRateEntity>();
        entities.add(serviceRateEntity);
        when(mockServiceRateRepository.deleteByUuid(any())).thenReturn(entities);

        // when
        ServiceRateValue result = classUnderTest.deleteServiceRate(TEST_UUID);

        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());

    }
    private ServiceRateValue prepareServiceRateValue(){
        ServiceRateValue serviceRateValue=new ServiceRateValue();
        serviceRateValue.setUuid(TEST_UUID);
        return serviceRateValue;
    }
    private ServiceRateEntity prepareServiceRateEntity(){
        ServiceRateEntity serviceRateEntity=new ServiceRateEntity();
        serviceRateEntity.setUuid(TEST_UUID);
        return serviceRateEntity;
    }

}



