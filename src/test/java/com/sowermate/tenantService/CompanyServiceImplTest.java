/**
 * Test package.
 *
 */
package com.sowermate.tenantService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import com.sowermate.tenantService.entities.*;
import com.sowermate.tenantService.entities.value.CompanyValue;
import com.sowermate.tenantService.repositories.*;
import com.sowermate.tenantService.services.impl.CompanyServiceImpl;
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
public class CompanyServiceImplTest {
    public static  String TEST_UUID="10fb0b34-7f57-4906-ab72-a463b019edf07";
    public static final String TEST_UUID1="34637e08-fa96-4ff1-aef0-fbb55f222e67";

    @InjectMocks
    private CompanyServiceImpl mockCompanyServiceImpl;

    @Mock

    private CompanyRepository mockCompanyRepository;

    @Mock
    private TenantRepository mockTenantRepository;

    @Mock
    private AddressRepository mockAddressRepository;

    @Mock
    private CompanyTypeRepository mockCompanyTypeRepository;

    @Mock
    private ProFormaInvoiceRepository mockProFormaInvoiceRepository;

    @Before
    public  void init(){

        MockitoAnnotations.initMocks(this);
    }
    /**
     * Tests {@link CompanyServiceImpl#createCompany(CompanyValue)}
     * when valid {@link CompanyValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while createCompany.
     */
    @Test
    public void testCreateCompanyWhenCompanyValueIsPassedExpectCompanyValueSaved()throws Exception{
        //given
        CompanyValue companyValue=prepareCompanyValue();
        CompanyEntity companyEntity=prepareCompanyEntity();
        when(mockCompanyRepository.save(any(CompanyEntity.class))).thenReturn(companyEntity);

        //for TenantEntity
        TenantEntity tenantEntity=prepareTenantEntity();
        List<TenantEntity> tenantEntities = new ArrayList<TenantEntity>();
        tenantEntities.add(tenantEntity);

        when(mockTenantRepository.findByUuid(anyString())).thenReturn(tenantEntity);

        //for AddressEntity
        AddressEntity addressEntity=prepareAddressEntity();
        List<AddressEntity> addressEntities = new ArrayList<AddressEntity>();
        addressEntities.add(addressEntity);

        when(mockAddressRepository.findByUuid(anyString())).thenReturn(addressEntities);

        //for CompanyTypeEntity
        CompanyTypeEntity companyTypeEntity=prepareCompanyTypeEntity();
        List<CompanyTypeEntity> companyTypeEntities = new ArrayList<CompanyTypeEntity>();
        companyTypeEntities.add(companyTypeEntity);

        when(mockCompanyTypeRepository.findByUuid(anyString())).thenReturn(companyTypeEntities);

        //for ProFormaInvoiceEntity
        ProFormaInvoiceEntity proFormaInvoiceEntity=prepareProFormaInvoiceEntity();
        List<ProFormaInvoiceEntity> proFormaInvoiceEntities = new ArrayList<ProFormaInvoiceEntity>();
        proFormaInvoiceEntities.add(proFormaInvoiceEntity);

        when(mockProFormaInvoiceRepository.findByUuid(anyString())).thenReturn(proFormaInvoiceEntities);

        //when
        CompanyValue result=mockCompanyServiceImpl.createCompany(companyValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link CompanyServiceImpl#editCompany(CompanyValue)}
     * when valid {@link CompanyValue} object is passed, expect the record updated in the database.
     *
     * @throws Exception when there is problem while fetching editCompany.
     */
    @Test
    public void testEditCompanyWhenCompanyValueIsPassedExpectCompanyValueUpdated()throws Exception{
        //given
        CompanyValue companyValue=prepareCompanyValue();
        CompanyEntity companyEntity=prepareCompanyEntity();
        when(mockCompanyRepository.save(any(CompanyEntity.class))).thenReturn(companyEntity);
        List<CompanyEntity> entities = new ArrayList<CompanyEntity>();
        entities.add(companyEntity);
        when(mockCompanyRepository.findByUuid(any())).thenReturn(entities);

        //for AdTenantEntity
        TenantEntity tenantEntity=prepareTenantEntity();
        List<TenantEntity> tenantEntities=new ArrayList<>();
        tenantEntities.add(tenantEntity);
        when(mockTenantRepository.findByUuid(any())).thenReturn(tenantEntity);

        //for AddressEntity
        AddressEntity addressEntity=prepareAddressEntity();
        List<AddressEntity> addressEntities = new ArrayList<AddressEntity>();
        addressEntities.add(addressEntity);

        when(mockAddressRepository.findByUuid(anyString())).thenReturn(addressEntities);

        //for CompanyTypeEntity
        CompanyTypeEntity companyTypeEntity=prepareCompanyTypeEntity();
        List<CompanyTypeEntity> companyTypeEntities = new ArrayList<CompanyTypeEntity>();
        companyTypeEntities.add(companyTypeEntity);

        when(mockCompanyTypeRepository.findByUuid(anyString())).thenReturn(companyTypeEntities);

        //for ProFormaInvoiceEntity
        ProFormaInvoiceEntity proFormaInvoiceEntity=prepareProFormaInvoiceEntity();
        List<ProFormaInvoiceEntity> proFormaInvoiceEntities = new ArrayList<ProFormaInvoiceEntity>();
        proFormaInvoiceEntities.add(proFormaInvoiceEntity);

        when(mockProFormaInvoiceRepository.findByUuid(anyString())).thenReturn(proFormaInvoiceEntities);

        //when
        CompanyValue result=mockCompanyServiceImpl.editCompany(companyValue);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }

    /**
     * Tests {@link CompanyServiceImpl#getCompany(String)}
     * when valid {@link CompanyValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getCompany by using UUID.
     */
    @Test
    public void testGetCompanyWhenCompanyUuidIsPassedExpectCompanyValueGet() throws Exception{
        //given
        CompanyEntity companyEntity=prepareCompanyEntity();

        List<CompanyEntity> mockedCompanyEntities = new ArrayList<>();
        mockedCompanyEntities.add(companyEntity);

        when(mockCompanyRepository.findByUuid(any())).thenReturn(mockedCompanyEntities);

        //when
        CompanyValue result=mockCompanyServiceImpl.getCompany(TEST_UUID);

        //then
        assertNotNull(result);
        assertEquals(TEST_UUID,result.getUuid());
    }
    /**
     * Tests {@link CompanyServiceImpl#getAllCompany()}
     * when valid object is passed, expect the all records get in the database.
     *
     * @throws Exception when there is problem while fetching getAllCompany by using UUID.
     */

    @Test
    public void testGetAllCompanyWhenRequestIsPassedExpectGetAllCompany() throws Exception {
        // given
        CompanyEntity companyEntity = prepareCompanyEntity();
        List<CompanyEntity> entities = new ArrayList<CompanyEntity>();
        entities.add(companyEntity);
        when(mockCompanyRepository.findAll()).thenReturn(entities);

        // when
        List<CompanyValue> result = mockCompanyServiceImpl.getAllCompany();

        // then
        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.get(0).getUuid()).isEqualTo(TEST_UUID);
    }

    /**
     * Tests {@link CompanyServiceImpl#deleteCompany(String)}
     * when valid {@link CompanyValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is a problem while fetching deleteCompany by using UUID.
     */
    @Test
    public void testDeleteCompanyWhenCompanyUuidIsPassedExpectCompanyValueDelete() throws Exception {
        // given
        CompanyEntity companyEntity = prepareCompanyEntity();

        List<CompanyEntity> entities = new ArrayList<CompanyEntity>();
        entities.add(companyEntity);
        when(mockCompanyRepository.deleteByUuid(any())).thenReturn(entities);

        // when
        CompanyValue result = mockCompanyServiceImpl.deleteCompany(TEST_UUID);

        // then
        assertNotNull(result);
        assertEquals(TEST_UUID, result.getUuid());
    }
    private CompanyValue prepareCompanyValue(){
        CompanyValue companyValue=new CompanyValue();
        companyValue.setUuid(TEST_UUID);
        return companyValue;
    }
    private CompanyEntity prepareCompanyEntity(){
        CompanyEntity companyEntity=new CompanyEntity();
        companyEntity.setUuid(TEST_UUID);
        return companyEntity;
    }
    private TenantEntity prepareTenantEntity(){
        TenantEntity tenantEntity=new TenantEntity();
        tenantEntity.setUuid(TEST_UUID);
        return tenantEntity;
    }
    private AddressEntity prepareAddressEntity(){
        AddressEntity addressEntity=new AddressEntity();
        addressEntity.setUuid(TEST_UUID);
        return addressEntity;
    }
    private CompanyTypeEntity prepareCompanyTypeEntity(){
        CompanyTypeEntity companyTypeEntity=new CompanyTypeEntity();
        companyTypeEntity.setUuid(TEST_UUID);
        return companyTypeEntity;
    }
    private ProFormaInvoiceEntity prepareProFormaInvoiceEntity() {
        ProFormaInvoiceEntity proFormaInvoiceEntity = new ProFormaInvoiceEntity();
        proFormaInvoiceEntity.setUuid(TEST_UUID1);
        proFormaInvoiceEntity.setProFormaInvoiceId(1);
        return proFormaInvoiceEntity;
    }
}



