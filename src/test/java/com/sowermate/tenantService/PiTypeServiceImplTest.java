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
import com.sowermate.tenantService.entities.PiTypeEntity;
import com.sowermate.tenantService.entities.value.PiTypeValue;
import com.sowermate.tenantService.repositories.PiTypeRepository;
import com.sowermate.tenantService.services.impl.PiTypeServiceImpl;
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
public class PiTypeServiceImplTest {
    public static String TEST_UUID="91e6aeb1-0718-458e-aebd-dd5651d8444b";
    @InjectMocks
    private PiTypeServiceImpl classUnderTest;
    @Mock
    private PiTypeRepository mockPiTypeRepository;
    @Before
    public  void init(){

        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests {@link PiTypeServiceImpl#createPiType(PiTypeValue)}
     * when valid {@link PiTypeValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while createPiType.
     */
    @Test
    public void testCreatePiTypeWhenPiTypeValueIsPassedExpectPiTypeValueSaved()throws Exception{
        //given
        PiTypeValue piTypeValue=preparePiTypeValue();
        PiTypeEntity PiTypeEntity=preparePiTypeEntity();
        when(mockPiTypeRepository.save(any(PiTypeEntity.class))).thenReturn(PiTypeEntity);
        //when
        PiTypeValue result=classUnderTest.createPiType(piTypeValue);
        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }
    /**
     * Tests {@link PiTypeServiceImpl#editPiType(PiTypeValue)}
     * when valid {@link PiTypeValue} object is passed, expect the record updated in the database.
     *
     * @throws Exception when there is problem while fetching editPiType.
     */
    @Test
    public void testEditPiTypeWhenPiTypeValueIsPassedExpectAddressValueUpdated()throws Exception{
        //given
        PiTypeValue piTypeValue=preparePiTypeValue();
        PiTypeEntity piTypeEntity=preparePiTypeEntity();
        when(mockPiTypeRepository.save(any(PiTypeEntity.class))).thenReturn(piTypeEntity);
        List<PiTypeEntity> entities = new ArrayList<PiTypeEntity>();
        entities.add(piTypeEntity);
        when(mockPiTypeRepository.findByUuid(any())).thenReturn(entities);
        //when
        PiTypeValue result=classUnderTest.editPiType(piTypeValue);
        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }
    /**
     * Tests {@link PiTypeServiceImpl#getPiType(String)}
     * when valid {@link PiTypeValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getPiType by using UUID.
     */
    @Test
    public void testGetPiTypeWhenPiTypeUuidIsPassedExpectPiTypeValueGet() throws Exception{
        //given
        PiTypeEntity piTypeEntity=preparePiTypeEntity();
        List<PiTypeEntity> mockedPiTypeEntities = new ArrayList<>();
        mockedPiTypeEntities.add(piTypeEntity);
        when(mockPiTypeRepository.findByUuid(any())).thenReturn(mockedPiTypeEntities);
        //when
        PiTypeValue result=classUnderTest.getPiType(TEST_UUID);
        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link PiTypeServiceImpl#getAllPiType()}
     * when valid object is passed, expect the all records get in the database.
     *
     * @throws Exception when there is problem while fetching getAllPiType by using UUID.
     */
    @Test
    public void testGetAllPiTypeWhenRequestIsPassedExpectGetAllPiType() throws Exception {
        // given
        PiTypeEntity piTypeEntity = preparePiTypeEntity();
        List<PiTypeEntity> entities = new ArrayList<PiTypeEntity>();
        entities.add(piTypeEntity);
        when(mockPiTypeRepository.findAll()).thenReturn(entities);
        // when
        List<PiTypeValue> result = classUnderTest.getAllPiType();
        // then
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.get(0).getUuid()).isEqualTo(TEST_UUID);
    }

    /**
     * Tests {@link PiTypeServiceImpl#deletePiType(String)}
     * when valid {@link PiTypeValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is a problem while fetching deletePiType by using UUID.
     */
    @Test
    public void testDeletePiTypeWhenPiTypeUuidIsPassedExpectPiTypeValueDelete() throws Exception {
        // given
        PiTypeEntity piTypeEntity = preparePiTypeEntity();
        List<PiTypeEntity> entities = new ArrayList<PiTypeEntity>();
        entities.add(piTypeEntity);
        when(mockPiTypeRepository.deleteByUuid(any())).thenReturn(entities);
        // when
        PiTypeValue result = classUnderTest.deletePiType(TEST_UUID);
        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());
    }
    private PiTypeValue preparePiTypeValue(){
        PiTypeValue piTypeValue=new PiTypeValue();
        piTypeValue.setUuid(TEST_UUID);
        return piTypeValue;
    }
    private PiTypeEntity preparePiTypeEntity(){
        PiTypeEntity piTypeEntity=new PiTypeEntity();
        piTypeEntity.setUuid(TEST_UUID);
        return piTypeEntity;
    }

}



