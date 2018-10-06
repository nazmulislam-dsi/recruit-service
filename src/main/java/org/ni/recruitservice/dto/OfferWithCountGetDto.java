package org.ni.recruitservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.ni.recruitservice.utils.JsonDateDeserializer;
import org.ni.recruitservice.utils.JsonDateSerializer;

import java.util.Date;

/**
 * Created by nazmul on 10/6/2018.
 */
public @Data
class OfferWithCountGetDto {
    private Long id;

    private String title;

    @JsonSerialize(using=JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date startDate;

    private Integer numberOfApplications = 0;

}
