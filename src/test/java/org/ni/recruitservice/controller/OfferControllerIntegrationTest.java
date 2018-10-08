package org.ni.recruitservice.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ni.recruitservice.dto.*;
import org.ni.recruitservice.enums.ApplicationStatus;
import org.ni.recruitservice.model.Application;
import org.ni.recruitservice.repository.ApplicationRepository;
import org.ni.recruitservice.repository.OfferRepository;
import org.ni.recruitservice.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by nazmul on 10/8/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@FixMethodOrder
public class OfferControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Before
    public void setup() {
        applicationRepository.deleteAll();
        offerRepository.deleteAll();
    }

    @Test
    public void offerPostOK() throws Exception {

        OfferPostDto offerPostDto = new OfferPostDto();
        offerPostDto.setTitle("Test");
        offerPostDto.setStartDate(new Date());
        mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(offerPostDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void offerPostBAD_REQUEST() throws Exception {

        mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("GGGGGG")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void offerPostCONFLICT() throws Exception {

        OfferPostDto offerPostDto = new OfferPostDto();
        offerPostDto.setTitle("Test");
        offerPostDto.setStartDate(new Date());
        mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(offerPostDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(offerPostDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void applicationPostOK() throws Exception {

        OfferPostDto offerPostDto = new OfferPostDto();
        offerPostDto.setTitle("Test");
        offerPostDto.setStartDate(new Date());
        MvcResult result = mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(offerPostDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        OfferGetDto offerGetDto = Commons.getObjectFromJsonString(content,OfferGetDto.class);
        ApplicationPostDto application = new ApplicationPostDto();
        application.setApplicationStatus(ApplicationStatus.APPLIED);
        application.setCandidateEmail("naim.5221@gmail.com");
        application.setResume("Test");
        mvc.perform(post("/offers/"+offerGetDto.getId()+"/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(application))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void applicationPostCONFLICT() throws Exception {

        OfferPostDto offerPostDto = new OfferPostDto();
        offerPostDto.setTitle("Test");
        offerPostDto.setStartDate(new Date());
        MvcResult result = mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(offerPostDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        OfferGetDto offerGetDto = Commons.getObjectFromJsonString(content,OfferGetDto.class);
        ApplicationPostDto application = new ApplicationPostDto();
        application.setApplicationStatus(ApplicationStatus.APPLIED);
        application.setCandidateEmail("naim.5221@gmail.com");
        application.setResume("Test");
        mvc.perform(post("/offers/"+offerGetDto.getId()+"/applications").param("offerId",offerGetDto.getId()+"")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(application))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        mvc.perform(post("/offers").param("offerId",offerGetDto.getId()+"")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(application))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mvc.perform(post("/offers").param("offerId",offerGetDto.getId()+"")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(application))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void applicationPostBAD_REQUEST() throws Exception {

        OfferPostDto offerPostDto = new OfferPostDto();
        offerPostDto.setTitle("Test");
        offerPostDto.setStartDate(new Date());
        MvcResult result = mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(offerPostDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        OfferGetDto offerGetDto = Commons.getObjectFromJsonString(content,OfferGetDto.class);
        ApplicationPostDto application = new ApplicationPostDto();
        application.setApplicationStatus(ApplicationStatus.APPLIED);
        application.setCandidateEmail("naim.5221@gmail.com");
        application.setResume("Test");
        mvc.perform(post("/offers/"+offerGetDto.getId()+"/applications").param("offerId",offerGetDto.getId()+"")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(application))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        mvc.perform(post("/offers/"+offerGetDto.getId()+"/applications").param("offerId",offerGetDto.getId()+"")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(application))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        mvc.perform(post("/offers/"+offerGetDto.getId()+"/applications").param("offerId",offerGetDto.getId()+"")
                .contentType(MediaType.APPLICATION_JSON)
                .content("GGGGG")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    @Test
    public void offerGetOK() throws Exception {

        OfferPostDto offerPostDto = new OfferPostDto();
        offerPostDto.setTitle("Test");
        offerPostDto.setStartDate(new Date());
        MvcResult result = mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(offerPostDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        OfferPostDto offerPostDto2 = new OfferPostDto();
        offerPostDto2.setTitle("Test2");
        offerPostDto2.setStartDate(new Date());
        MvcResult result2 = mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(offerPostDto2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        MvcResult result3 = mvc.perform(get("/offers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content3 = result3.getResponse().getContentAsString();
        List<OfferGetDto> offerGetDtoList = Commons.getObjectMapperList(content3,OfferGetDto.class);
        Assert.assertEquals(2,offerGetDtoList.size());

    }

    @Test
    public void testGetOfferNoContent() throws Exception {
        mvc.perform(get("/offers/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testGetOffersNoContent() throws Exception {
        mvc.perform(get("/offers")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }


    @Test
    public void applicationGeOK() throws Exception {

        OfferPostDto offerPostDto = new OfferPostDto();
        offerPostDto.setTitle("Test");
        offerPostDto.setStartDate(new Date());
        MvcResult result = mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(offerPostDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        OfferGetDto offerGetDto = Commons.getObjectFromJsonString(content,OfferGetDto.class);
        ApplicationPostDto application = new ApplicationPostDto();
        application.setApplicationStatus(ApplicationStatus.APPLIED);
        application.setCandidateEmail("naim.5221@gmail.com");
        application.setResume("Test");
        MvcResult applicationResult = mvc.perform(post("/offers/"+offerGetDto.getId()+"/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(application))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String applicationContent = applicationResult.getResponse().getContentAsString();

        ApplicationGetDto applicationGetDto = Commons.getObjectFromJsonString(applicationContent, ApplicationGetDto.class);

        mvc.perform(get("/offers/"+offerGetDto.getId()+"/applications/" + applicationGetDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(application))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void offerGeOK() throws Exception {

        OfferPostDto offerPostDto = new OfferPostDto();
        offerPostDto.setTitle("Test");
        offerPostDto.setStartDate(new Date());
        MvcResult result = mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(offerPostDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        OfferGetDto offerGetDto = Commons.getObjectFromJsonString(content,OfferGetDto.class);
        ApplicationPostDto application = new ApplicationPostDto();
        application.setApplicationStatus(ApplicationStatus.APPLIED);
        application.setCandidateEmail("naim.5221@gmail.com");
        application.setResume("Test");
        MvcResult applicationResult = mvc.perform(post("/offers/"+offerGetDto.getId()+"/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(application))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String applicationContent = applicationResult.getResponse().getContentAsString();

        ApplicationGetDto applicationGetDto = Commons.getObjectFromJsonString(applicationContent, ApplicationGetDto.class);

        MvcResult result2 = mvc.perform(get("/offers/"+offerGetDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String content2 = result2.getResponse().getContentAsString();
        OfferWithCountGetDto offerWithCountGetDto = Commons.getObjectFromJsonString(content2,OfferWithCountGetDto.class);
        Assert.assertEquals(new Integer(1),offerWithCountGetDto.getNumberOfApplications());

    }

    @Test
    public void applicationListGetOK() throws Exception {

        OfferPostDto offerPostDto = new OfferPostDto();
        offerPostDto.setTitle("Test");
        offerPostDto.setStartDate(new Date());
        MvcResult result = mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(offerPostDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        OfferGetDto offerGetDto = Commons.getObjectFromJsonString(content,OfferGetDto.class);
        ApplicationPostDto application = new ApplicationPostDto();
        application.setApplicationStatus(ApplicationStatus.APPLIED);
        application.setCandidateEmail("naim.5221@gmail.com");
        application.setResume("Test");
        MvcResult applicationResult = mvc.perform(post("/offers/"+offerGetDto.getId()+"/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(application))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String applicationContent = applicationResult.getResponse().getContentAsString();

        ApplicationGetDto applicationGetDto = Commons.getObjectFromJsonString(applicationContent, ApplicationGetDto.class);

        MvcResult result3 =mvc.perform(get("/offers/"+offerGetDto.getId()+"/applications/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(application))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content3 = result3.getResponse().getContentAsString();
        List<ApplicationGetDto> offerGetDtoList = Commons.getObjectMapperList(content3,ApplicationGetDto.class);
        Assert.assertEquals(1,offerGetDtoList.size());
    }

    @Test
    public void applicationGeNoContent() throws Exception {

        OfferPostDto offerPostDto = new OfferPostDto();
        offerPostDto.setTitle("Test");
        offerPostDto.setStartDate(new Date());
        MvcResult result = mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(offerPostDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        OfferGetDto offerGetDto = Commons.getObjectFromJsonString(content,OfferGetDto.class);

        mvc.perform(get("/offers/"+offerGetDto.getId()+"/applications/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testApplicationPatch() throws Exception {

        OfferPostDto offerPostDto = new OfferPostDto();
        offerPostDto.setTitle("Test");
        offerPostDto.setStartDate(new Date());
        MvcResult result = mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(offerPostDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        OfferGetDto offerGetDto = Commons.getObjectFromJsonString(content,OfferGetDto.class);
        ApplicationPostDto application = new ApplicationPostDto();
        application.setApplicationStatus(ApplicationStatus.APPLIED);
        application.setCandidateEmail("naim.5221@gmail.com");
        application.setResume("Test");
        MvcResult applicationResult = mvc.perform(post("/offers/"+offerGetDto.getId()+"/applications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(application))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String applicationContent = applicationResult.getResponse().getContentAsString();

        ApplicationGetDto applicationGetDto = Commons.getObjectFromJsonString(applicationContent, ApplicationGetDto.class);

        ApplicationPatchDto newApplication = new ApplicationPatchDto();
        newApplication.setApplicationStatus(ApplicationStatus.HIRED);
        newApplication.setResume("Test");

        mvc.perform(patch("/offers/applications/" + applicationGetDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .param("applicationId", String.valueOf(applicationGetDto.getId()))
                .content(Commons.objectToJsonString(newApplication))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(202))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testApplicationPatchNoContent() throws Exception {

        OfferPostDto offerPostDto = new OfferPostDto();
        offerPostDto.setTitle("Test");
        offerPostDto.setStartDate(new Date());
        MvcResult result = mvc.perform(post("/offers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(offerPostDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        String content = result.getResponse().getContentAsString();
        OfferGetDto offerGetDto = Commons.getObjectFromJsonString(content,OfferGetDto.class);
        ApplicationPostDto application = new ApplicationPostDto();
        application.setApplicationStatus(ApplicationStatus.APPLIED);
        application.setCandidateEmail("naim.5221@gmail.com");
        application.setResume("Test");

        ApplicationPatchDto newApplication = new ApplicationPatchDto();
        newApplication.setApplicationStatus(ApplicationStatus.HIRED);
        newApplication.setResume("Test");

        mvc.perform(patch("/offers/applications/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Commons.objectToJsonString(newApplication))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
