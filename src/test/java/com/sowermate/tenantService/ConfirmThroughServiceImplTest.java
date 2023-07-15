/**
 * Test package.
 *
 */
package com.sowermate.tenantService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.sowermate.tenantService.entities.ConfirmThroughEntity;
import com.sowermate.tenantService.entities.value.ConfirmThroughValue;
import com.sowermate.tenantService.repositories.ConfirmThroughRepository;
import com.sowermate.tenantService.services.impl.ConfirmThroughServiceImpl;
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
public class ConfirmThroughServiceImplTest {
    public static final String TEST_UUID="f17ecfa0-0cf3-4512-9f1b-939f891b0f65";

    @InjectMocks
    private ConfirmThroughServiceImpl classUnderTest;

    @Mock
    private ConfirmThroughRepository mockConfirmRepository;
    @Before
    public  void init(){

        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests {@link ConfirmThroughServiceImpl#createConfirmThrough(ConfirmThroughValue)}
     * when valid {@link ConfirmThroughValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while createConfirmThrough.
     */
    @Test
    public void testCreateConfirmThroughWhenConfirmThroughValueIsPassedExpectConfirmThroughValueSaved()throws Exception{
        //given
        ConfirmThroughValue confirmThroughValue=prepareConfirmThroughValue();
        ConfirmThroughEntity confirmThroughEntity=prepareConfirmThroughEntity();
        when(mockConfirmRepository.save(any(ConfirmThroughEntity.class))).thenReturn(confirmThroughEntity);

        //when
        ConfirmThroughValue result=classUnderTest.createConfirmThrough(confirmThroughValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link ConfirmThroughServiceImpl#editConfirmThrough(ConfirmThroughValue)}
     * when valid {@link ConfirmThroughValue} object is passed, expect the record updated in the database.
     *
     * @throws Exception when there is problem while fetching editConfirmThrough.
     */
    @Test
    public void testEditConfirmThroughWhenConfirmThroughValueIsPassedExpectConfirmThroughValueUpdated()throws Exception{
        //given
        ConfirmThroughValue confirmThroughValue=prepareConfirmThroughValue();
        ConfirmThroughEntity confirmThroughEntity=prepareConfirmThroughEntity();
        when(mockConfirmRepository.save(any(ConfirmThroughEntity.class))).thenReturn(confirmThroughEntity);
        List<ConfirmThroughEntity> entities = new ArrayList<ConfirmThroughEntity>();
        entities.add(confirmThroughEntity);
        when(mockConfirmRepository.findByUuid(any())).thenReturn(entities);

        //when
        ConfirmThroughValue result=classUnderTest.editConfirmThrough(confirmThroughValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * ests {@link ConfirmThroughServiceImpl#getConfirmThrough(String)}
     * when valid {@link ConfirmThroughValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getConfirmThrough by using UUID.
     */
    @Test
    public void testGetConfirmThroughWhenConfirmThroughUuidIsPassedExpectConfirmThroughValueGet() throws Exception{
        //given
        ConfirmThroughEntity confirmThroughEntity=prepareConfirmThroughEntity();

        List<ConfirmThroughEntity> mockedConfirmThroughEntities = new ArrayList<>();
        mockedConfirmThroughEntities.add(confirmThroughEntity);

        when(mockConfirmRepository.findByUuid(any())).thenReturn(mockedConfirmThroughEntities);
        //when
        ConfirmThroughValue result=classUnderTest.getConfirmThrough(TEST_UUID);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link ConfirmThroughServiceImpl#getAllConfirmThrough()}
     * when valid object is passed, expect the all records get in the database.
     *
     * @throws Exception when there is problem while fetching getAllConfirmThrough by using UUID.
     */

    @Test
    public void testGetAllConfirmThroughWhenRequestIsPassedExpectGetAllConfirmThrough() throws Exception {
        // given
        ConfirmThroughEntity confirmThroughEntity = prepareConfirmThroughEntity();
        List<ConfirmThroughEntity> entities = new ArrayList<ConfirmThroughEntity>();
        entities.add(confirmThroughEntity);
        when(mockConfirmRepository.findAll()).thenReturn(entities);

        // when
        List<ConfirmThroughValue> result = classUnderTest.getAllConfirmThrough();

        // then
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.get(0).getUuid()).isEqualTo(TEST_UUID);
    }

    /**
     * Tests {@link ConfirmThroughServiceImpl#deleteConfirmThrough(String)}
     * when valid {@link ConfirmThroughValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is a problem while fetching deleteConfirmThrough by using UUID.
     */
    @Test
    public void testDeleteConfirmThroughWhenConfirmThroughUuidIsPassedExpectConfirmThroughValueDelete() throws Exception {
        // given
        ConfirmThroughEntity confirmThroughEntity = prepareConfirmThroughEntity();

        List<ConfirmThroughEntity> entities = new ArrayList<ConfirmThroughEntity>();
        entities.add(confirmThroughEntity);
        when(mockConfirmRepository.deleteByUuid(any())).thenReturn(entities);

        // when
        ConfirmThroughValue result = classUnderTest.deleteConfirmThrough(TEST_UUID);

        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());

    }
    private ConfirmThroughValue prepareConfirmThroughValue(){
        ConfirmThroughValue confirmThroughValue=new ConfirmThroughValue();
        confirmThroughValue.setUuid(TEST_UUID);
        return confirmThroughValue;
    }
    private ConfirmThroughEntity prepareConfirmThroughEntity(){
        ConfirmThroughEntity confirmThroughEntity=new ConfirmThroughEntity();
        confirmThroughEntity.setUuid(TEST_UUID);
        return confirmThroughEntity;
    }

}



