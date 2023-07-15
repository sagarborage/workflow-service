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

import com.sowermate.tenantService.entities.ServiceRateInvoiceEntity;
import com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue;
import com.sowermate.tenantService.entities.value.ServiceRateValue;
import com.sowermate.tenantService.repositories.ServiceRateInvoiceRepository;
import com.sowermate.tenantService.services.ServiceRateInvoiceService;
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
public class ServiceRateInvoiceServiceImplTest {
        public static String TEST_UUID="c7cf3a76-12f7-448f-b9eb-e9b9135c5349";

        @InjectMocks
        private ServiceRateInvoiceService classUnderTest;

        @Mock
        private ServiceRateInvoiceRepository mockServiceRateInvoiceRepository;
        @Before
        public  void init(){

                MockitoAnnotations.initMocks(this);
        }

        /**
         * Tests {@link com.sowermate.tenantService.services.impl.ServiceRateServiceImpl#createServiceRate(ServiceRateValue)}
         * when valid {@link com.sowermate.tenantService.entities.value.ServiceRateInvoiceValue} object is passed, expect the record saved in the database.
         *
         * @throws Exception when there is problem while createServiceRateInvoice.
         */
        @Test
        public void testCreateServiceRateInvoiceWhenServiceRateInvoiceValueIsPassedExpectServiceRateInvoiceValueSaved()throws Exception{
                //given
                ServiceRateInvoiceValue serviceRateInvoiceValue=prepareServiceRateInvoiceValue();
                ServiceRateInvoiceEntity serviceRateInvoiceEntity=prepareServiceRateInvoiceEntity();
                when(mockServiceRateInvoiceRepository.save(any(ServiceRateInvoiceEntity.class))).thenReturn(serviceRateInvoiceEntity);

                //when
                ServiceRateInvoiceValue result=classUnderTest.createServiceRateInvoice(serviceRateInvoiceValue);

                //then
                assertNotNull(result);
                assertEquals(TEST_UUID,result.getUuid());
        }

        /**
         * Tests {@link com.sowermate.tenantService.services.impl.ServiceRateServiceImpl#editServiceRate(ServiceRateValue)}
         * when valid {@link ServiceRateInvoiceValue} object is passed, expect the record updated in the database.
         *
         * @throws Exception when there is problem while fetching editServiceRateInvoice.
         */
        @Test
        public void testEditServiceRateInvoiceWhenServiceRateInvoiceValueIsPassedExpectServiceRateInvoiceValueUpdated()throws Exception{
                //given
                ServiceRateInvoiceValue serviceRateInvoiceValue=prepareServiceRateInvoiceValue();
                ServiceRateInvoiceEntity serviceRateInvoiceEntity=prepareServiceRateInvoiceEntity();
                when(mockServiceRateInvoiceRepository.save(any(ServiceRateInvoiceEntity.class))).thenReturn(serviceRateInvoiceEntity);
                List<ServiceRateInvoiceEntity> entities = new ArrayList<ServiceRateInvoiceEntity>();
                entities.add(serviceRateInvoiceEntity);
                when(mockServiceRateInvoiceRepository.findByUuid(any())).thenReturn(entities);

                //when
                ServiceRateInvoiceValue result=classUnderTest.editServiceRateInvoice(serviceRateInvoiceValue);

                //then
                assertNotNull(result);
                assertEquals(TEST_UUID,result.getUuid());
        }

        /**
         * Tests {@link com.sowermate.tenantService.services.impl.ServiceRateServiceImpl#getServiceRate(String)}
         * when valid {@link ServiceRateInvoiceValue} object is passed, expect the record get in the database.
         *
         * @throws Exception when there is problem while fetching getServiceRate by using UUID.
         */
        @Test
        public void testGetServiceRateInvoiceWhenServiceRateInvoiceUuidIsPassedExpectServiceRateInvoiceValueGet() throws Exception{
                //given
                ServiceRateInvoiceEntity serviceRateInvoiceEntity=prepareServiceRateInvoiceEntity();

                List<ServiceRateInvoiceEntity> mockedServiceRateInvoiceEntities = new ArrayList<>();
                mockedServiceRateInvoiceEntities.add(serviceRateInvoiceEntity);

                when(mockServiceRateInvoiceRepository.findByUuid(any())).thenReturn(mockedServiceRateInvoiceEntities);
                //when
                ServiceRateInvoiceValue result=classUnderTest.getServiceRateInvoice(TEST_UUID);

                //then
                assertNotNull(result);
                assertEquals(TEST_UUID,result.getUuid());
        }

        /**
         * Tests {@link ServiceRateServiceImpl#getAllServiceRate()} ()}
         * when valid object is passed, expect the all records get in the database.
         *
         * @throws Exception when there is problem while fetching getAllServiceRateInvoice by using UUID.
         */

        @Test
        public void testGetAllServiceRateInvoiceWhenRequestIsPassedExpectGetAllServiceRateInvoice() throws Exception {
                // given
                ServiceRateInvoiceEntity serviceRateInvoiceEntity = prepareServiceRateInvoiceEntity();
                List<ServiceRateInvoiceEntity> entities = new ArrayList<ServiceRateInvoiceEntity>();
                entities.add(serviceRateInvoiceEntity);
                when(mockServiceRateInvoiceRepository.findAll()).thenReturn(entities);

                // when
                List<ServiceRateInvoiceValue> result = classUnderTest.getAllServiceRateInvoice();

                // then
                assertThat(result).isNotNull().isNotEmpty();
                assertThat(result.get(0).getUuid()).isEqualTo(TEST_UUID);
        }

        /**
         * Tests {@link ServiceRateServiceImpl#deleteServiceRate(String)}
         * when valid {@link ServiceRateInvoiceValue} object is passed, expect the record delete in the database.
         *
         * @throws Exception when there is a problem while fetching deleteServiceRateInvoice by using UUID.
         */
        @Test
        public void testDeleteServiceRateInvoiceWhenServiceRateInvoiceUuidIsPassedExpectServiceRateInvoiceValueDelete() throws Exception {
                // given
                ServiceRateInvoiceEntity serviceRateInvoiceEntity = prepareServiceRateInvoiceEntity();

                List<ServiceRateInvoiceEntity> entities = new ArrayList<ServiceRateInvoiceEntity>();
                entities.add(serviceRateInvoiceEntity);
                when(mockServiceRateInvoiceRepository.deleteByUuid(any())).thenReturn(entities);

                // when
                ServiceRateInvoiceValue result = classUnderTest.deleteServiceRateInvoice(TEST_UUID);

                // then
                assertNotNull(result);
                assertEquals(TEST_UUID, result.getUuid());

        }
        private ServiceRateInvoiceValue prepareServiceRateInvoiceValue(){
                ServiceRateInvoiceValue serviceRateInvoiceValue=new ServiceRateInvoiceValue();
                serviceRateInvoiceValue.setUuid(TEST_UUID);
                return serviceRateInvoiceValue;
        }
        private ServiceRateInvoiceEntity prepareServiceRateInvoiceEntity(){
                ServiceRateInvoiceEntity serviceRateInvoiceEntity=new ServiceRateInvoiceEntity();
                serviceRateInvoiceEntity.setUuid(TEST_UUID);
                return serviceRateInvoiceEntity;
        }

}



