package org.ni.recruitservice.dto;

import lombok.Data;
import org.ni.recruitservice.enums.ApplicationStatus;

/**
 * Created by nazmul on 10/6/2018.
 */
public @Data
class ApplicationGetDto {

    private Long id;

    private String candidateEmail;

    private String resume;

    private ApplicationStatus applicationStatus;
}
