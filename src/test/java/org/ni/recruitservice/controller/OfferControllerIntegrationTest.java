package org.ni.recruitservice.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ni.recruitservice.dto.OfferPostDto;
import org.ni.recruitservice.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by nazmul on 10/8/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OfferControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void initialStatistics() throws Exception {

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
}
