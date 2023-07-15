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

import com.sowermate.tenantService.entities.ProFormaInvoiceEntity;
import com.sowermate.tenantService.entities.value.ProFormInvoiceValue;
import com.sowermate.tenantService.repositories.ProFormaInvoiceRepository;
import com.sowermate.tenantService.services.impl.ProFormaInvoiceServiceImpl;
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
ProFormaInvoiceServiceImplTest {
    public static final String TEST_UUID="34637e08-fa96-4ff1-aef0-fbb55f222e67";

    @InjectMocks
    private ProFormaInvoiceServiceImpl classUnderTest;

    @Mock
    private ProFormaInvoiceRepository mockProFormInvoiceRepository;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests {@link ProFormaInvoiceServiceImpl#createProFormInvoice(ProFormInvoiceValue)}
     *
     * when valid {@link ProFormInvoiceValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while createProFormInvoice.
     */
    @Test
    public void testCreateProFormInvoiceWhenProFormInvoiceValueIsPassedExpectProFormInvoiceValueSaved()throws Exception{
        //given
        ProFormInvoiceValue proFormInvoiceValue=prepareProFormaInvoiceValue();
        ProFormaInvoiceEntity proFormInvoiceEntity=prepareProFormaInvoiceEntity();
        when(mockProFormInvoiceRepository.save(any(ProFormaInvoiceEntity.class))).thenReturn(proFormInvoiceEntity);

        //when
        ProFormInvoiceValue result=classUnderTest.createProFormInvoice(proFormInvoiceValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link ProFormaInvoiceServiceImpl#editProFormInvoice(ProFormInvoiceValue)}
     * when valid {@link ProFormInvoiceValue} object is passed, expect the record updated in the database.
     *
     * @throws Exception when there is problem while fetching editProFormInvoice.
     */
    @Test
    public void testEditProFormInvoiceWhenProFormInvoiceValueIsPassedExpectProFormInvoiceUpdated()throws Exception{
        //given
        ProFormInvoiceValue proFormInvoiceValue=prepareProFormaInvoiceValue();
        ProFormaInvoiceEntity proFormaInvoiceEntity=prepareProFormaInvoiceEntity();
        when(mockProFormInvoiceRepository.save(any(ProFormaInvoiceEntity.class))).thenReturn(proFormaInvoiceEntity);
        List<ProFormaInvoiceEntity> entities = new ArrayList<ProFormaInvoiceEntity>();
        entities.add(proFormaInvoiceEntity);
        when(mockProFormInvoiceRepository.findByUuid(any())).thenReturn(entities);

        //when
        ProFormInvoiceValue result=classUnderTest.editProFormInvoice(proFormInvoiceValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link ProFormaInvoiceServiceImpl#getProFormInvoice(String)}
     * when valid {@link ProFormInvoiceValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getProFormInvoice by using UUID.
     */
    @Test
    public void testGetProFormInvoiceWhenProFormInvoiceUuidIsPassedProFormInvoiceThroughValueGet() throws Exception{
        //given
        ProFormaInvoiceEntity proFormInvoiceEntity=prepareProFormaInvoiceEntity();

        List<ProFormaInvoiceEntity> mockedProFormInvoiceEntities = new ArrayList<>();
        mockedProFormInvoiceEntities.add(proFormInvoiceEntity);

        when(mockProFormInvoiceRepository.findByUuid(any())).thenReturn(mockedProFormInvoiceEntities);
        //when
        ProFormInvoiceValue result=classUnderTest.getProFormInvoice(TEST_UUID);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link ProFormaInvoiceServiceImpl#getAllProFormInvoice()}
     * when valid object is passed, expect the all records get in the database.
     *
     * @throws Exception when there is problem while fetching getAllProFormInvoice by using UUID.
     */

    @Test
    public void testGetAllProFormInvoiceWhenRequestIsPassedExpectGetAllProFormInvoice() throws Exception {
        // given
        ProFormaInvoiceEntity proFormaInvoiceEntity = prepareProFormaInvoiceEntity();
        List<ProFormaInvoiceEntity> entities = new ArrayList<ProFormaInvoiceEntity>();
        entities.add(proFormaInvoiceEntity);
        when(mockProFormInvoiceRepository.findAll()).thenReturn(entities);

        // when
        List<ProFormInvoiceValue> result = classUnderTest.getAllProFormInvoice();

        // then
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.get(0).getUuid()).isEqualTo(TEST_UUID);
    }

    /**
     * Tests {@link ProFormaInvoiceServiceImpl#deleteProFormInvoice(String)}
     * when valid {@link ProFormInvoiceValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is a problem while fetching deleteProFormInvoice by using UUID.
     */
    @Test
    public void testDeleteProFormInvoiceWhenProFormaInvoiceUuidIsPassedExpectProFormaInvoiceValueDelete() throws Exception {
        // given
        ProFormaInvoiceEntity proFormaInvoiceEntity = prepareProFormaInvoiceEntity();

        List<ProFormaInvoiceEntity> entities = new ArrayList<ProFormaInvoiceEntity>();
        entities.add(proFormaInvoiceEntity);
        when(mockProFormInvoiceRepository.deleteByUuid(any())).thenReturn(entities);

        // when
        ProFormInvoiceValue result = classUnderTest.deleteProFormInvoice(TEST_UUID);

        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());

    }
    private ProFormInvoiceValue prepareProFormaInvoiceValue(){
        ProFormInvoiceValue proFormInvoiceValue=new ProFormInvoiceValue();
        proFormInvoiceValue.setUuid(TEST_UUID);
        return proFormInvoiceValue;
    }
    private ProFormaInvoiceEntity prepareProFormaInvoiceEntity(){
        ProFormaInvoiceEntity proFormInvoiceEntity=new ProFormaInvoiceEntity();
        proFormInvoiceEntity.setUuid(TEST_UUID);
        return proFormInvoiceEntity;
    }

}



