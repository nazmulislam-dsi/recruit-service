package org.ni.recruitservice.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ni.recruitservice.dto.ApplicationPostDto;
import org.ni.recruitservice.dto.OfferGetDto;
import org.ni.recruitservice.dto.OfferPostDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
}
