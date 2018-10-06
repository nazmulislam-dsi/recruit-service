package org.ni.recruitservice.dto;

import lombok.Data;
import org.ni.recruitservice.enums.ApplicationStatus;

import javax.validation.constraints.NotNull;

/**
 * Created by nazmul on 10/7/2018.
 */
public @Data
class ApplicationPatchDto {
    private String resume;
    private ApplicationStatus applicationStatus;
}
