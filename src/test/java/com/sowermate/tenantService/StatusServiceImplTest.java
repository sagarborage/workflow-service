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

import com.sowermate.tenantService.entities.StatusEntity;
import com.sowermate.tenantService.entities.value.StatusValue;
import com.sowermate.tenantService.repositories.StatusRepository;
import com.sowermate.tenantService.services.impl.ServiceRateServiceImpl;
import com.sowermate.tenantService.services.impl.StatusServiceImpl;
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
public class StatusServiceImplTest {
    public static String TEST_UUID="10e17bb4-543c-40cb-9d11-72de37f7a779";

    @InjectMocks
    private StatusServiceImpl classUnderTest;

    @Mock
    private StatusRepository mockStatusRepository;
    @Before
    public  void init(){

        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests {@link StatusServiceImpl#createStatus(StatusValue)}
     * when valid {@link StatusValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while createStatusValue.
     */
    @Test
    public void testCreateStatusWhenStatusValueIsPassedExpectStatusValueSaved()throws Exception{
        //given
        StatusValue statusValue=prepareStatusValue();
        StatusEntity statusEntity=prepareStatusEntity();
        when(mockStatusRepository.save(any(StatusEntity.class))).thenReturn(statusEntity);

        //when
        StatusValue result=classUnderTest.createStatus(statusValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link StatusServiceImpl#editStatus(StatusValue)}
     * when valid {@link StatusValue} object is passed, expect the record updated in the database.
     *
     * @throws Exception when there is problem while fetching editStatus.
     */
    @Test
    public void testEditStatusWhenStatusValueIsPassedExpectStatusValueUpdated()throws Exception{
        //given
        StatusValue statusValue=prepareStatusValue();
        StatusEntity statusEntity=prepareStatusEntity();
        when(mockStatusRepository.save(any(StatusEntity.class))).thenReturn(statusEntity);
        List<StatusEntity> entities = new ArrayList<StatusEntity>();
        entities.add(statusEntity);
        when(mockStatusRepository.findByUuid(any())).thenReturn(entities);

        //when
        StatusValue result=classUnderTest.editStatus(statusValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link StatusServiceImpl#getStatus(String)}
     * when valid {@link StatusValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getStatus by using UUID.
     */
    @Test
    public void testGetStatusWhenStatusUuidIsPassedExpectStatusValueGet() throws Exception{
        //given
        StatusEntity statusEntity=prepareStatusEntity();

        List<StatusEntity> mockedStatusEntities = new ArrayList<>();
        mockedStatusEntities.add(statusEntity);

        when(mockStatusRepository.findByUuid(any())).thenReturn(mockedStatusEntities);
        //when
        StatusValue result=classUnderTest.getStatus(TEST_UUID);

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
    public void testGetAllStatusWhenRequestIsPassedExpectGetAllStatus() throws Exception {
        // given
        StatusEntity statusEntity = prepareStatusEntity();
        List<StatusEntity> entities = new ArrayList<StatusEntity>();
        entities.add(statusEntity);
        when(mockStatusRepository.findAll()).thenReturn(entities);

        // when
        List<StatusValue> result = classUnderTest.getAllStatus();

        // then
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.get(0).getUuid()).isEqualTo(TEST_UUID);
    }

    /**
     * Tests {@link StatusServiceImpl#deleteStatus(String)}
     * when valid {@link StatusValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is a problem while fetching deleteStatus by using UUID.
     */
    @Test
    public void testDeleteStatusWhenStatusUuidIsPassedExpectStatusValueDelete() throws Exception {
        // given
        StatusEntity statusEntity = prepareStatusEntity();

        List<StatusEntity> entities = new ArrayList<StatusEntity>();
        entities.add(statusEntity);
        when(mockStatusRepository.deleteByUuid(any())).thenReturn(entities);

        // when
        StatusValue result = classUnderTest.deleteStatus(TEST_UUID);

        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());

    }
    private StatusValue prepareStatusValue(){
        StatusValue statusValue=new StatusValue();
        statusValue.setUuid(TEST_UUID);
        return statusValue;
    }
    private StatusEntity prepareStatusEntity(){
        StatusEntity statusEntity=new StatusEntity();
        statusEntity.setUuid(TEST_UUID);
        return statusEntity;
    }

}



