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
import com.sowermate.tenantService.entities.GlassSpecificationEntity;
import com.sowermate.tenantService.entities.value.GlassSpecificationValue;
import com.sowermate.tenantService.repositories.GlassSpecificationRepository;
import com.sowermate.tenantService.services.impl.GlassSpecificationServiceImpl;
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
public class GlassSpecificationServiceImplTest {
    public static final String TEST_UUID="b20c4e8b-422a-4b2d-ab1f-65725b14bbd3";
    public static final String TEST_UUID1="fc30efeb-fd8c-4608-9b56-ccd342a54f80";

    @InjectMocks
    private GlassSpecificationServiceImpl classUnderTest;

    @Mock
    private GlassSpecificationRepository mockGlassSpecificationRepository;

    @Before
    public  void init(){

        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests {@link GlassSpecificationServiceImpl#createGlassSpecification(GlassSpecificationValue)}
     * when valid {@link GlassSpecificationValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while createGlassSpecification.
     */
    @Test
    public void testCreateGlassSpecificationWhenGlassSpecificationValueIsPassedExpectGlassSpecificationValueSaved()throws Exception{
        //given
        GlassSpecificationValue glassSpecificationValue=prepareGlassSpecificationValue();
        GlassSpecificationEntity glassSpecificationEntity=prepareGlassSpecificationEntity();
        when(mockGlassSpecificationRepository.save(any(GlassSpecificationEntity.class))).thenReturn(glassSpecificationEntity);

        //when
        GlassSpecificationValue result=classUnderTest.createGlassSpecification(glassSpecificationValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link GlassSpecificationServiceImpl#editGlassSpecification(GlassSpecificationValue)}
     * when valid {@link GlassSpecificationValue} object is passed, expect the record updated in the database.
     *
     * @throws Exception when there is problem while fetching editGlassSpecification.
     */
    @Test
    public void testEditGlassSpecificationWhenGlassSpecificationValueIsPassedExpectGlassSpecificationValueUpdated()throws Exception{
        //given
        GlassSpecificationValue glassSpecificationValue=prepareGlassSpecificationValue();
        GlassSpecificationEntity glassSpecificationEntity=prepareGlassSpecificationEntity();
        when(mockGlassSpecificationRepository.save(any(GlassSpecificationEntity.class))).thenReturn(glassSpecificationEntity);
        List<GlassSpecificationEntity> entities = new ArrayList<GlassSpecificationEntity>();
        entities.add(glassSpecificationEntity);
        when(mockGlassSpecificationRepository.findByUuid(any())).thenReturn(entities);

        //when
        GlassSpecificationValue result=classUnderTest.editGlassSpecification(glassSpecificationValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * ests {@link GlassSpecificationServiceImpl#getGlassSpecification(String)}
     * when valid {@link GlassSpecificationValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getGlassSpecification by using UUID.
     */
    @Test
    public void testGetGlassSpecificationWhenGlassSpecificationUuidIsPassedGlassSpecificationThroughValueGet() throws Exception{
        //given
        GlassSpecificationEntity glassSpecificationEntity=prepareGlassSpecificationEntity();

        List<GlassSpecificationEntity> mockedGlassSpecificationEntities = new ArrayList<>();
        mockedGlassSpecificationEntities.add(glassSpecificationEntity);

        when(mockGlassSpecificationRepository.findByUuid(any())).thenReturn(mockedGlassSpecificationEntities);
        //when
        GlassSpecificationValue result=classUnderTest.getGlassSpecification(TEST_UUID);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link GlassSpecificationServiceImpl#getAllGlassSpecification()}
     * when valid object is passed, expect the all records get in the database.
     *
     * @throws Exception when there is problem while fetching getAllGlassSpecification by using UUID.
     */

    @Test
    public void testGetAllGlassSpecificationWhenRequestIsPassedExpectGetAllGlassSpecification() throws Exception {
        // given
        GlassSpecificationEntity classSpecificationEntity = prepareGlassSpecificationEntity();
        List<GlassSpecificationEntity> entities = new ArrayList<GlassSpecificationEntity>();
        entities.add(classSpecificationEntity);
        when(mockGlassSpecificationRepository.findAll()).thenReturn(entities);

        // when
        List<GlassSpecificationValue> result = classUnderTest.getAllGlassSpecification();

        // then
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.get(0).getUuid()).isEqualTo(TEST_UUID);
    }

    /**
     * Tests {@link GlassSpecificationServiceImpl#deleteGlassSpecification(String)}
     * when valid {@link GlassSpecificationValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is a problem while fetching deleteGlassSpecification by using UUID.
     */
    @Test
    public void testDeleteGlassSpecificationWhenGlassSpecificationUuidIsPassedExpectGlassSpecificationValueDelete() throws Exception {
        // given
        GlassSpecificationEntity glassSpecificationEntity = prepareGlassSpecificationEntity();

        List<GlassSpecificationEntity> entities = new ArrayList<GlassSpecificationEntity>();
        entities.add(glassSpecificationEntity);
        when(mockGlassSpecificationRepository.deleteByUuid(any())).thenReturn(entities);

        // when
        GlassSpecificationValue result = classUnderTest.deleteGlassSpecification(TEST_UUID);

        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());

    }
    private GlassSpecificationValue prepareGlassSpecificationValue(){
        GlassSpecificationValue glassSpecificationValue=new GlassSpecificationValue();
        glassSpecificationValue.setUuid(TEST_UUID);
        return glassSpecificationValue;
    }
    private GlassSpecificationEntity prepareGlassSpecificationEntity(){
        GlassSpecificationEntity glassSpecificationEntity=new GlassSpecificationEntity();
        glassSpecificationEntity.setUuid(TEST_UUID);
        return glassSpecificationEntity;
    }

}



