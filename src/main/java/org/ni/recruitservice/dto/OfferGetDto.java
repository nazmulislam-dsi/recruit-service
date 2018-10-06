package org.ni.recruitservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.ni.recruitservice.model.Application;
import org.ni.recruitservice.utils.JsonDateDeserializer;
import org.ni.recruitservice.utils.JsonDateSerializer;

import java.util.Date;
import java.util.List;

/**
 * Created by nazmul on 10/6/2018.
 */
public @Data
class OfferGetDto {
    private Long id;

    private String title;

    @JsonSerialize(using=JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date startDate;

}
