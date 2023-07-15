package com.sowermate.tenantController;
/**
 * Test package.
 *
 */
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.sql.Date;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
/**
 * @author Vithoba Hipparkar
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RestAdditionalChargesControllerTest{
    public static  final String TEST_UUID="ea11f09a1-a13d-4617-af8b-468a48a85692";
    @InjectMocks
    private RestAdd classUnderTest;
    @Mock
    private AdCampaignServiceImpl mockAdCampaignServiceImpl;
    @Before
    public  void init(){
        MockitoAnnotations.initMocks(this);
    }
    /**
     * Tests {@link RestAdCampaignController#createAdCampaign(com.borages.rest.value.AdCampaignValue)}
     * when valid {@link AdCampaignValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while createAdCampaign.
     */
    @Test
    public void testCreateAdCampaignWhenAdCampaignValueIsPassedExpectAdCampaignValueCreated() throws Exception {
        //given
        CommonRespValue commonRespValue =prepareCommonRespValue();
        commonRespValue.setUuid(TEST_UUID);
        AdCampaignValue adCampaignValue=prepareAdCampaignValue();
        //when
        when(mockAdCampaignServiceImpl.createAdCampaign(any(AdCampaignValue.class))).thenReturn(adCampaignValue);
        ResponseEntity<CommonRespValue> result= classUnderTest.createAdCampaign(adCampaignValue);
        //then
        assertThat (result.getStatusCodeValue()).isEqualTo(201);
    }
    /**
     * Tests {@link RestAdCampaignController#createAdCampaign(com.borages.rest.value.AdCampaignValue)}
     * when valid {@link AdCampaignValue} object is passed, expect the record saved in the database.
     *
     * @throws Exception when there is problem while createAdCampaign.
     */
    @Test
    public void testCreateAdCampaignWhenExceptionInServiceExpectLogAdCampaignIsPrinted() throws Exception {
        //given
        CommonRespValue commonRespValue = prepareCommonRespValue();
        commonRespValue.setUuid(TEST_UUID);
        AdCampaignValue adCampaignValue = prepareAdCampaignValue();
        //when
        when(mockAdCampaignServiceImpl.createAdCampaign(any(AdCampaignValue.class))).thenThrow(new Exception("test Exception"));
        ResponseEntity<CommonRespValue> result= classUnderTest.createAdCampaign(adCampaignValue);
        //then
        assertThat (result.getStatusCodeValue()).isEqualTo(201);
    }
    /**
     * Tests {@link RestAdCampaignController#editAdCampaign(com.borages.rest.value.AdCampaignValue)}
     * when valid {@link AdCampaignValue} object is passed, expect the record updated in the database.
     *
     * @throws Exception when there is problem while fetching editAdCampaign.
     */
    @Test
    public void testEditAdCampaignWhenAdCampaignValueIsPassedExpectAdCampaignValueUpdated() throws Exception {
        //given
        AdCampaignValue adCampaignValue=prepareAdCampaignValue();
        //when
        when(mockAdCampaignServiceImpl.editAdCampaign(any(AdCampaignValue.class))).thenReturn(adCampaignValue);
        ResponseEntity<CommonRespValue> result= classUnderTest.editAdCampaign(adCampaignValue);
        //then
        assertThat (result.getStatusCodeValue()).isEqualTo(201);
        assertEquals(TEST_UUID, result.getBody().getUuid());
        assertThat(result.getClass());
    }
    /**
     * Tests {@link RestAdCampaignController#editAdCampaign(com.borages.rest.value.AdCampaignValue)}
     * when valid {@link AdCampaignValue} object is passed, expect the record updated in the database.
     *
     * @throws Exception when there is problem while fetching editAdCampaign.
     */
    @Test
    public void testEditAdCampaignWhenExceptionInServiceExpectLogAdCampaignIsPrinted() throws Exception {
        //given
        AdCampaignValue adCampaignValue=prepareAdCampaignValue();
        //when
        when(mockAdCampaignServiceImpl.editAdCampaign(any(AdCampaignValue.class))).thenThrow(new Exception("test Exception"));
        ResponseEntity<CommonRespValue> result= classUnderTest.editAdCampaign(adCampaignValue);
        //then
        assertThat (result.getStatusCodeValue()).isEqualTo(201);
        assertThat(result.getClass());
    }
    /**
     * Tests {@link RestAdCampaignController#getAdCampaign(String)}
     * when valid {@link AdCampaignValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getAdCampaign by using UUID.
     */
    @Test
    public void testGetAdCampaignWhenAdCampaignUuidIsPassedExpectAdCampaignValueGet() throws Exception {
        //given
        AdCampaignValue adCampaignValue=prepareAdCampaignValue();
        //when
        when(mockAdCampaignServiceImpl.getAdCampaign(TEST_UUID)).thenReturn(adCampaignValue);
        ResponseEntity<AdCampaignValue> result=classUnderTest.getAdCampaign(TEST_UUID);
        //then
        assertThat(result.getHeaders().size());
    }
    /**
     * Tests {@link RestAdCampaignController#getAdCampaign(String)}
     * when valid {@link AdCampaignValue} object is passed, expect the record get in the database.
     *
     * @throws Exception when there is problem while fetching getAdCampaign by using UUID.
     */
    @Test
    public void testGetAdCampaignWhenExceptionInServiceExpectLogAdCampaignIsPrinted() throws Exception {
        //given
        AdCampaignValue adCampaignValue=prepareAdCampaignValue();
        //when
        when(mockAdCampaignServiceImpl.getAdCampaign(TEST_UUID)).thenThrow(new Exception("Test Exception"));
        ResponseEntity<AdCampaignValue> result=classUnderTest.getAdCampaign(TEST_UUID);
        //then
        assertThat(result.getHeaders().size());
    }
    /**
     * Tests {@link RestAdCampaignController#deleteAdCampaign(String)}
     * when valid {@link AdCampaignValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is problem while fetching deleteAdCampaign by using UUID.
     */
    @Test
    public void testDeleteAdCampaignWhenAdCampaignUuidIsPassedExpectAdCampaignValueDeleted() throws Exception {
        // given
        AdCampaignValue adCampaignValue=prepareAdCampaignValue();
        //when
        when(mockAdCampaignServiceImpl.deleteAdCampaign(TEST_UUID)).thenReturn(adCampaignValue);
        ResponseEntity<CommonRespValue> result=classUnderTest.deleteAdCampaign(TEST_UUID);
        //then
        assertThat(result.getHeaders().size());
    }
    /**
     * Tests {@link RestAdCampaignController#deleteAdCampaign(String)}
     * when valid {@link AdCampaignValue} object is passed, expect the record delete in the database.
     *
     * @throws Exception when there is problem while fetching deleteAdCampaign by using UUID.
     */
    @Test
    public void testDeleteMessageWhenExceptionInServiceExpectLogMessageIsPrinted() throws Exception {
        // given
        AdCampaignValue adCampaignValue=prepareAdCampaignValue();
        //when
        when(mockAdCampaignServiceImpl.deleteAdCampaign(TEST_UUID)).thenThrow(new Exception("Test Exception"));
        ResponseEntity<CommonRespValue> result=classUnderTest.deleteAdCampaign(TEST_UUID);
        //then
        assertThat(result.getHeaders().size());
    }
    /**
     * Tests {@link RestAdCampaignController#getAllAdCampaign()()}
     * when valid object is passed, expect the all records get in the database.
     *
     * @throws Exception when there is problem while fetching getAllAdCampaign() by using UUID.
     */
    @Test
    public void testGetAllAdCampaignWhenRequestIsPassedExpectGetAllAdCampaign() throws Exception {
        //given
        AdCampaignValue adCampaignValue=prepareAdCampaignValue();
        List<AdCampaignValue> adCampaignValues=new ArrayList<AdCampaignValue>();
        adCampaignValues.add(adCampaignValue);
        //when
        when(mockAdCampaignServiceImpl.getAllAdCampaign()).thenReturn(adCampaignValues);
        ResponseEntity<List<AdCampaignValue>> result=classUnderTest.getAllAdCampaign();
        //then
        assertThat(result.getHeaders().size());
    }
    /**
     * Tests {@link RestAdCampaignController#getAllAdCampaign()()}
     * when valid object is passed, expect the all records get in the database.
     *
     * @throws Exception when there is problem while fetching getAllAdCampaign() by using UUID.
     */
    @Test
    public void testGetAllAdCampaignWhenExceptionInServiceExpectLogAdCampaignIsPrinted() throws Exception {
        //given
        AdCampaignValue adCampaignValue=prepareAdCampaignValue();
        List<AdCampaignValue> adCampaignValues=new ArrayList<AdCampaignValue>();
        adCampaignValues.add(adCampaignValue);
        //when
        when(mockAdCampaignServiceImpl.getAllAdCampaign()).thenThrow(new Exception("Test Exception"));
        ResponseEntity<List<AdCampaignValue>> result=classUnderTest.getAllAdCampaign();
        //then
        assertThat(result.getHeaders().size());
    }
    /**
     * Tests {@link RestAdCampaignController#getAllDateWiseAdCampaign(Date, Date)}
     * when valid fromDate and toDate object is passed, expect the get all date wise record in the database.
     * @throws Exception when there is problem while fetching all date wise adCampaigns.
     */
    @Test
    public void testGetAllDateWiseWhenFromDateAndToDateIsPassedExpectGetAllDateWiseAdCampaign() throws Exception {
        // given
        LocalDate localDate = LocalDate.of(2022, 9, 11);
        Date fromDate = Date.valueOf(localDate);
        Date toDate = Date.valueOf(localDate);
        AdCampaignValue adCampaignValue=prepareAdCampaignValue();
        List<AdCampaignValue> adCampaignValues=new ArrayList<AdCampaignValue>();
        adCampaignValues.add(adCampaignValue);
        //when
        when(mockAdCampaignServiceImpl.getAllDateWise(any(),any())).thenReturn(adCampaignValues);
        ResponseEntity <List<AdCampaignValue>> result= classUnderTest.getAllDateWiseAdCampaign(fromDate,toDate);
        //then
        assertThat(result.getHeaders().size());
    }
    /**
     * Tests {@link RestAdCampaignController#getAllDateWiseAdCampaign(Date, Date)}
     * when valid fromDate and toDate object is passed, expect the get all date wise record in the database.
     * @throws Exception when there is problem while fetching all date wise adCampaigns.
     */
    @Test
    public void testGetAllDateWiseWhenExceptionInServiceExpectLogAdCampaignIsPrinted() throws Exception {
        // given
        LocalDate localDate = LocalDate.of(2022, 9, 11);
        Date fromDate = Date.valueOf(localDate);
        Date toDate = Date.valueOf(localDate);
        AdCampaignValue adCampaignValue=prepareAdCampaignValue();
        List<AdCampaignValue> adCampaignValues=new ArrayList<AdCampaignValue>();
        adCampaignValues.add(adCampaignValue);
        //when
        when(mockAdCampaignServiceImpl.getAllDateWise(any(),any())).thenThrow(new Exception("Test Exception"));
        ResponseEntity <List<AdCampaignValue>> result= classUnderTest.getAllDateWiseAdCampaign(fromDate,toDate);
        //then
        assertThat(result.getHeaders().size());
    }
    private AdCampaignValue prepareAdCampaignValue()
    {
        AdCampaignValue adCampaignValue=new AdCampaignValue();
        adCampaignValue.setUuid(TEST_UUID);
        adCampaignValue.setName("Controller");
        return adCampaignValue;
    }
    private CommonRespValue prepareCommonRespValue()
    {
        CommonRespValue commonRespValue=new CommonRespValue();
        commonRespValue.setUuid(TEST_UUID);
        return commonRespValue;
    }
}
