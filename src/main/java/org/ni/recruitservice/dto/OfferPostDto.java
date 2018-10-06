package org.ni.recruitservice.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.ni.recruitservice.utils.JsonDateDeserializer;
import org.ni.recruitservice.utils.JsonDateSerializer;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by nazmul on 10/6/2018.
 */
public @Data
class OfferPostDto {
    @NotNull
    private String title;

    @NotNull
    @JsonSerialize(using=JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date startDate;
}
