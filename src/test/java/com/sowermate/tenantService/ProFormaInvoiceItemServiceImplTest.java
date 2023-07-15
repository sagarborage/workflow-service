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
import com.sowermate.tenantService.entities.ProFormaInvoiceItemEntity;
import com.sowermate.tenantService.entities.value.GlassSpecificationValue;
import com.sowermate.tenantService.entities.value.ProFormaInvoiceItemValue;
import com.sowermate.tenantService.repositories.ProFormaInvoiceItemRepository;
import com.sowermate.tenantService.services.impl.ProFormaInvoiceItemServiceImpl;
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
public class ProFormaInvoiceItemServiceImplTest {
    public static final String TEST_UUID="34637e08-fa96-4ff1-aef0-fbb55f222e67";

    @InjectMocks
    private ProFormaInvoiceItemServiceImpl classUnderTest;

    @Mock
    private ProFormaInvoiceItemRepository mockProFormaInvoiceItemRepository;

    @Before
    public  void init(){

        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests {@link ProFormaInvoiceItemServiceImpl#createProFormInvoiceItem(ProFormaInvoiceItemValue)}
     *
     * when valid {@link ProFormaInvoiceItemValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while createProFormaInvoiceItem.
     */
    @Test
    public void testCreateProFormaInvoiceItemWhenProFormaInvoiceItemValueIsPassedExpectProFormaInvoiceItemValueSaved()throws Exception{
        //given
        ProFormaInvoiceItemValue proFormaInvoiceItemValue=prepareProFormaInvoiceItemValue();
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity=prepareProFormaInvoiceItemEntity();
        when(mockProFormaInvoiceItemRepository.save(any(ProFormaInvoiceItemEntity.class))).thenReturn(proFormaInvoiceItemEntity);

        //when
        ProFormaInvoiceItemValue result=classUnderTest.createProFormInvoiceItem(proFormaInvoiceItemValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link ProFormaInvoiceItemServiceImpl#editProFormInvoiceItem(ProFormaInvoiceItemValue)}
     * when valid {@link GlassSpecificationValue} object is passed, expect the record updated in the database.
     *
     * @throws Exception when there is problem while fetching editProFormaInvoiceItem.
     */
    @Test
    public void testEditProFormInvoiceItemWhenProFormInvoiceItemValueIsPassedExpectProFormInvoiceItemUpdated()throws Exception{
        //given
        ProFormaInvoiceItemValue proFormaInvoiceItemValue=prepareProFormaInvoiceItemValue();
        ProFormaInvoiceItemEntity ProFormaInvoiceItemEntity=prepareProFormaInvoiceItemEntity();
        when(mockProFormaInvoiceItemRepository.save(any(ProFormaInvoiceItemEntity.class))).thenReturn(ProFormaInvoiceItemEntity);
        List<ProFormaInvoiceItemEntity> entities = new ArrayList<ProFormaInvoiceItemEntity>();
        entities.add(ProFormaInvoiceItemEntity);
        when(mockProFormaInvoiceItemRepository.findByUuid(any())).thenReturn(entities);

        //when
        ProFormaInvoiceItemValue result=classUnderTest.editProFormInvoiceItem(proFormaInvoiceItemValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link ProFormaInvoiceItemServiceImpl#getProFormInvoiceItem(String)}
     * when valid {@link ProFormaInvoiceItemValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getProFormaInvoiceItem by using UUID.
     */
    @Test
    public void testGetProFormaInvoiceItemWhenProFormaInvoiceItemUuidIsPassedProFormaInvoiceItemThroughValueGet() throws Exception{
        //given
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity=prepareProFormaInvoiceItemEntity();

        List<ProFormaInvoiceItemEntity> mockedProFormaInvoiceItemEntities = new ArrayList<>();
        mockedProFormaInvoiceItemEntities.add(proFormaInvoiceItemEntity);

        when(mockProFormaInvoiceItemRepository.findByUuid(any())).thenReturn(mockedProFormaInvoiceItemEntities);
        //when
        ProFormaInvoiceItemValue result=classUnderTest.getProFormInvoiceItem(TEST_UUID);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link ProFormaInvoiceItemServiceImpl#getAllProFormInvoiceItem()}
     * when valid object is passed, expect the all records get in the database.
     *
     * @throws Exception when there is problem while fetching getAllProFormaInvoiceItem by using UUID.
     */

    @Test
    public void testGetAllProFormaInvoiceItemWhenRequestIsPassedExpectGetAllProFormaInvoiceItem() throws Exception {
        // given
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = prepareProFormaInvoiceItemEntity();
        List<ProFormaInvoiceItemEntity> entities = new ArrayList<ProFormaInvoiceItemEntity>();
        entities.add(proFormaInvoiceItemEntity);
        when(mockProFormaInvoiceItemRepository.findAll()).thenReturn(entities);

        // when
        List<ProFormaInvoiceItemValue> result = classUnderTest.getAllProFormInvoiceItem();

        // then
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.get(0).getUuid()).isEqualTo(TEST_UUID);
    }

    /**
     * Tests {@link ProFormaInvoiceItemServiceImpl#deleteProFormInvoiceItem(String)}
     * when valid {@link ProFormaInvoiceItemValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is a problem while fetching deleteProFormaInvoiceItem by using UUID.
     */
    @Test
    public void testDeleteProFormaInvoiceItemWhenProFormaInvoiceItemUuidIsPassedExpectProFormaInvoiceItemValueDelete() throws Exception {
        // given
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity = prepareProFormaInvoiceItemEntity();

        List<ProFormaInvoiceItemEntity> entities = new ArrayList<ProFormaInvoiceItemEntity>();
        entities.add(proFormaInvoiceItemEntity);
        when(mockProFormaInvoiceItemRepository.deleteByUuid(any())).thenReturn(entities);

        // when
        ProFormaInvoiceItemValue result = classUnderTest.deleteProFormInvoiceItem(TEST_UUID);

        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());

    }
    private ProFormaInvoiceItemValue prepareProFormaInvoiceItemValue(){
        ProFormaInvoiceItemValue proFormaInvoiceItemValue=new ProFormaInvoiceItemValue();
        proFormaInvoiceItemValue.setUuid(TEST_UUID);
        return proFormaInvoiceItemValue;
    }
    private ProFormaInvoiceItemEntity prepareProFormaInvoiceItemEntity(){
        ProFormaInvoiceItemEntity proFormaInvoiceItemEntity=new ProFormaInvoiceItemEntity();
        proFormaInvoiceItemEntity.setUuid(TEST_UUID);
        return proFormaInvoiceItemEntity;
    }

}



