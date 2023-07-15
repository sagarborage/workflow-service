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
import com.sowermate.tenantService.entities.GlassThicknessEntity;
import com.sowermate.tenantService.entities.value.GlassThicknessValue;
import com.sowermate.tenantService.repositories.GlassThicknessRepository;
import com.sowermate.tenantService.services.impl.GlassThicknessServiceImpl;
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
public class GlassThicknessServiceImplTest {
    public static final String TEST_UUID="fc30efeb-fd8c-4608-9b56-ccd342a54f80";

    @InjectMocks
    private GlassThicknessServiceImpl classUnderTest;

    @Mock
    private GlassThicknessRepository mockGlassThicknessRepository;

    @Before
    public  void init(){

        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests {@link GlassThicknessServiceImpl#createGlassThickness(GlassThicknessValue)}
     * when valid {@link GlassThicknessValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while createGlassThickness.
     */
    @Test
    public void testCreateGlassThicknessWhenGlassThicknessValueIsPassedExpectGlassThicknessValueSaved()throws Exception{
        //given
        GlassThicknessValue glassThicknessValue=prepareGlassThicknessValue();
        GlassThicknessEntity glassThicknessEntity=prepareGlassThicknessEntity();
        when(mockGlassThicknessRepository.save(any(GlassThicknessEntity.class))).thenReturn(glassThicknessEntity);

        //when
        GlassThicknessValue result=classUnderTest.createGlassThickness(glassThicknessValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link GlassThicknessServiceImpl#editGlassThickness(GlassThicknessValue)}
     * when valid {@link GlassThicknessValue} object is passed, expect the record updated in the database.
     *
     * @throws Exception when there is problem while fetching editGlassThickness.
     */
    @Test
    public void testEditGlassThicknessWhenGlassThicknessValueIsPassedExpectGlassThicknessValueUpdated()throws Exception{
        //given
        GlassThicknessValue glassThicknessValue=prepareGlassThicknessValue();
        GlassThicknessEntity glassThicknessEntity=prepareGlassThicknessEntity();
        when(mockGlassThicknessRepository.save(any(GlassThicknessEntity.class))).thenReturn(glassThicknessEntity);
        List<GlassThicknessEntity> entities = new ArrayList<GlassThicknessEntity>();
        entities.add(glassThicknessEntity);
        when(mockGlassThicknessRepository.findByUuid(any())).thenReturn(entities);

        //when
        GlassThicknessValue result=classUnderTest.editGlassThickness(glassThicknessValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link GlassThicknessServiceImpl#getGlassThickness(String)}
     * when valid {@link GlassThicknessValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getGlassThickness by using UUID.
     */
    @Test
    public void testGetGlassThicknessWhenGlassThicknessUuidIsPassedGlassThicknessThroughValueGet() throws Exception{
        //given
        GlassThicknessEntity glassThicknessEntity=prepareGlassThicknessEntity();

        List<GlassThicknessEntity> mockedGlassThicknessEntities = new ArrayList<>();
        mockedGlassThicknessEntities.add(glassThicknessEntity);

        when(mockGlassThicknessRepository.findByUuid(any())).thenReturn(mockedGlassThicknessEntities);
        //when
        GlassThicknessValue result=classUnderTest.getGlassThickness(TEST_UUID);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link GlassThicknessServiceImpl#getAllGlassThickness()}
     * when valid object is passed, expect the all records get in the database.
     *
     * @throws Exception when there is problem while fetching getAllGlassThickness by using UUID.
     */

    @Test
    public void testGetAllGlassThicknessWhenRequestIsPassedExpectGetAllGlassThickness() throws Exception {
        // given
        GlassThicknessEntity glassThicknessEntity = prepareGlassThicknessEntity();
        List<GlassThicknessEntity> entities = new ArrayList<GlassThicknessEntity>();
        entities.add(glassThicknessEntity);
        when(mockGlassThicknessRepository.findAll()).thenReturn(entities);

        // when
        List<GlassThicknessValue> result = classUnderTest.getAllGlassThickness();

        // then
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.get(0).getUuid()).isEqualTo(TEST_UUID);
    }

    /**
     * Tests {@link GlassThicknessServiceImpl#deleteGlassThickness(String)}
     * when valid {@link GlassThicknessValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is a problem while fetching deleteGlassThickness by using UUID.
     */
    @Test
    public void testDeleteGlassThicknessWhenGlassThicknessUuidIsPassedExpectGlassThicknessValueDelete() throws Exception {
        // given
        GlassThicknessEntity glassThicknessEntity = prepareGlassThicknessEntity();

        List<GlassThicknessEntity> entities = new ArrayList<>();
        entities.add(glassThicknessEntity);
        when(mockGlassThicknessRepository.deleteByUuid(any())).thenReturn(entities);

        // when
        GlassThicknessValue result = classUnderTest.deleteGlassThickness(TEST_UUID);

        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());

    }
    private GlassThicknessValue prepareGlassThicknessValue(){
        GlassThicknessValue glassThicknessValue=new GlassThicknessValue();
        glassThicknessValue.setUuid(TEST_UUID);
        return glassThicknessValue;
    }
    private GlassThicknessEntity prepareGlassThicknessEntity(){
        GlassThicknessEntity glassThicknessEntity=new GlassThicknessEntity();
        glassThicknessEntity.setUuid(TEST_UUID);
        return glassThicknessEntity;
    }

}



