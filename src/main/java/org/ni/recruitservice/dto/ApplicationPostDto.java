package org.ni.recruitservice.dto;

import lombok.Data;
import org.ni.recruitservice.enums.ApplicationStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * Created by nazmul on 10/6/2018.
 */
public @Data
class ApplicationPostDto {
    @NotNull
    @Email
    private String candidateEmail;

    @NotNull
    private String resume;

    @NotNull
    private ApplicationStatus applicationStatus;
}
