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
import com.sowermate.tenantService.entities.AddressEntity;
import com.sowermate.tenantService.entities.value.AddressValue;
import com.sowermate.tenantService.repositories.AddressRepository;
import com.sowermate.tenantService.services.impl.AddressServiceImpl;
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
public class AddressServiceImplTest {

    public static String TEST_UUID="10fb0b34-7f57-4906-ab72-a463b019edf0";

    @InjectMocks
    private AddressServiceImpl classUnderTest;

    @Mock
    private AddressRepository mockAddressRepository;
    @Before
    public  void init(){

        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests {@link AddressServiceImpl#createAddress(AddressValue)}
     * when valid {@link AddressValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while createAddress.
     */
    @Test
    public void testCreateAddressWhenAddressValueIsPassedExpectAddressValueSaved()throws Exception{
        //given
        AddressValue addressValue=prepareAddressValue();
        AddressEntity addressEntity=prepareAddressEntity();
        when(mockAddressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);

        //when
        AddressValue result=classUnderTest.createAddress(addressValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link AddressServiceImpl#editAddress(AddressValue)}
     * when valid {@link AddressValue} object is passed, expect the record updated in the database.
     *
     * @throws Exception when there is problem while fetching editAddress.
     */
    @Test
    public void testEditAddressWhenAddressValueIsPassedExpectAddressValueUpdated()throws Exception{
        //given
        AddressValue addressValue=prepareAddressValue();
        AddressEntity addressEntity=prepareAddressEntity();
        when(mockAddressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);
        List<AddressEntity> entities = new ArrayList<AddressEntity>();
        entities.add(addressEntity);
        when(mockAddressRepository.findByUuid(any())).thenReturn(entities);

        //when
        AddressValue result=classUnderTest.editAddress(addressValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }
    /**
     * Tests {@link AddressServiceImpl#getAddress(String)}
     * when valid {@link AddressValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getAddress by using UUID.
     */
    @Test
    public void testGetAddressWhenAddressUuidIsPassedExpectAddressValueGet() throws Exception{
        //given
        AddressEntity addressEntity=prepareAddressEntity();

        List<AddressEntity> mockedAddressEntities = new ArrayList<>();
        mockedAddressEntities.add(addressEntity);

        when(mockAddressRepository.findByUuid(any())).thenReturn(mockedAddressEntities);
        //when
        AddressValue result=classUnderTest.getAddress(TEST_UUID);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }
    /**
     * Tests {@link AddressServiceImpl#getAllAddress()}
     * when valid object is passed, expect the all records get in the database.
     *
     * @throws Exception when there is problem while fetching getAllAddress by using UUID.
     */
    @Test
    public void testGetAllAddressWhenRequestIsPassedExpectGetAllAddress() throws Exception {
        // given
        AddressEntity addressEntity = prepareAddressEntity();
        List<AddressEntity> entities = new ArrayList<AddressEntity>();
        entities.add(addressEntity);
        when(mockAddressRepository.findAll()).thenReturn(entities);

        // when
        List<AddressValue> result = classUnderTest.getAllAddress();

        // then
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.get(0).getUuid()).isEqualTo(TEST_UUID);
    }

    /**
     * Tests {@link AddressServiceImpl#deleteAddress(String)}
     * when valid {@link AddressValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is a problem while fetching deleteAddress by using UUID.
     */
   /* @Test
    public void testDeleteAddressWhenAddressUuidIsPassedExpectAddressValueDelete() throws Exception {
        // given
        AddressEntity addressEntity = prepareAddressEntity();
        List<AddressEntity> entities = new ArrayList<AddressEntity>();
        entities.add(addressEntity);
        when(mockAddressRepository.deleteByUuid(any())).thenReturn(entities);

        // when
       AddressValue result = classUnderTest.deleteAddress(TEST_UUID);

        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());
    }*/
    private AddressValue prepareAddressValue(){
        AddressValue addressValue=new AddressValue();
        addressValue.setUuid(TEST_UUID);
        return addressValue;
    }
    private AddressEntity prepareAddressEntity(){
        AddressEntity addressEntity=new AddressEntity();
        addressEntity.setUuid(TEST_UUID);
        return addressEntity;
    }

}



