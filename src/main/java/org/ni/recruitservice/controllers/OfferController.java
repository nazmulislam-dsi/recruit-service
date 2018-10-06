package org.ni.recruitservice.controllers;

import io.swagger.annotations.*;
import org.hibernate.exception.ConstraintViolationException;
import org.ni.recruitservice.dto.*;
import org.ni.recruitservice.exception.ApiException;
import org.ni.recruitservice.exception.BadRequestException;
import org.ni.recruitservice.model.Application;
import org.ni.recruitservice.model.Offer;
import org.ni.recruitservice.service.ApplicationService;
import org.ni.recruitservice.service.OfferService;
import org.ni.recruitservice.utils.Commons;
import org.ni.recruitservice.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by nazmul on 10/6/2018.
 */
@Api(value = "Offer Controller", tags = "Offer Controller", description = "Offer Mgnt APIs")
@RestController
@RequestMapping(path = "/offers")
public class OfferController {

    private OfferService offerService;

    private ApplicationService applicationService;

    @Autowired
    public OfferController(OfferService offerService, ApplicationService applicationService) {
        this.offerService = offerService;
        this.applicationService = applicationService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(position = 1, value = "This endpoint is called to get offer list.", notes = "This endpoint is called to get offer list.", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Constants.OFFER_GET_200),
            @ApiResponse(code = 204, message = Constants.OFFER_GET_204),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity getOfferList() {
        List<Offer> offerList = offerService.getAllOffers();
        if (Commons.isEmpty(offerList)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(Commons.transformObjectList(offerList, OfferGetDto.class));
        }
    }

    @GetMapping(path = "/{offerId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(position = 1, value = "This endpoint is called to get a single offer.", notes = "This endpoint is called to get a single offer.", response = OfferWithCountGetDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Constants.OFFER_GET_200),
            @ApiResponse(code = 204, message = Constants.OFFER_GET_204),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity getOffer(
            @ApiParam(value = "Offer Id") @PathVariable("offerId") Long offerId
    ) {
        Offer offer = offerService.getOfferByOfferId(offerId);
        List<Application> applicationList = applicationService.getApplicationListByOfferId(offerId);
        if(!Commons.isEmpty(applicationList)){
            offer.setNumberOfApplications(applicationList.size());
        }
        if (offer==null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(Commons.transformObject(offer, OfferWithCountGetDto.class));
        }
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(position = 1, value = "This endpoint is called to add a offer.", notes = "This endpoint is called to add a offer.", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Constants.OFFER_POST_201),
            @ApiResponse(code = 400, message = Constants.OFFER_POST_400),
            @ApiResponse(code = 409, message = Constants.OFFER_POST_409),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity postOffer(
            @ApiParam(value = "Offer Object") @RequestBody @Valid OfferPostDto offerPostDto
    ) {
        Offer offer = Commons.transformObject(offerPostDto, Offer.class);
        offer = offerService.saveOrUpdateOffer(offer);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(Commons.transformObject(offer, OfferGetDto.class));
    }

    @PostMapping(path = "/{offerId}/applications", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(position = 1, value = "This endpoint is called to add a application under a offer.", notes = "This endpoint is called to add a application under a offer.", response = ApplicationGetDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Constants.OFFER_POST_201),
            @ApiResponse(code = 400, message = Constants.OFFER_POST_400),
            @ApiResponse(code = 400, message = Constants.APPLICATION_POST_400),
            @ApiResponse(code = 409, message = Constants.APPLICATION_POST_409),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity postApplicationForOffer(
            @ApiParam(value = "Offer Id") @PathVariable("offerId") Long offerId,
            @ApiParam(value = "Application Object") @RequestBody @Valid ApplicationPostDto applicationPostDto
    ) {
        Application application = applicationService.saveOrUpdateApplication(applicationPostDto, offerId);
        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(Commons.transformObject(application, ApplicationGetDto.class));
    }

    @GetMapping(path = "/{offerId}/applications", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(position = 1, value = "This endpoint is called to get application list a offer.", notes = "This endpoint is called to get application list  a offer.", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Constants.APPLICATION_GET_200),
            @ApiResponse(code = 204, message = Constants.APPLICATION_GET_204),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity getApplicationListForAOffer(
            @ApiParam(value = "Offer Id") @PathVariable("offerId") Long offerId
    ) {
        List<Application> applicationList = applicationService.getApplicationListByOfferId(offerId);
        if (Commons.isEmpty(applicationList)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(Commons.transformObjectList(applicationList, ApplicationGetDto.class));
        }
    }

    @GetMapping(path = "/{offerId}/applications/{applicationId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(position = 1, value = "This endpoint is called to get a application a offer.", notes = "This endpoint is called to get a application a offer.", response = ApplicationGetDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Constants.APPLICATION_GET_200),
            @ApiResponse(code = 204, message = Constants.APPLICATION_GET_204),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity getAnApplicationForAOffer(
            @ApiParam(value = "Offer Id") @PathVariable("offerId") Long offerId,
            @ApiParam(value = "Application Id") @PathVariable("applicationId") Long applicationId
    ) {
        Application application = applicationService.getApplicationListByApplicationIdAndOfferId(offerId,applicationId);
        if (application==null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(Commons.transformObject(application, ApplicationGetDto.class));
        }
    }

    @PatchMapping(path = "/applications/{applicationId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(position = 1, value = "This endpoint is called to update a application.", notes = "This endpoint is called to update a application.", response = ApplicationGetDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = Constants.APPLICATION_PATCH_200),
            @ApiResponse(code = 204, message = Constants.APPLICATION_GET_204),
            @ApiResponse(code = 400, message = Constants.APPLICATION_POST_400),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity patchAnApplication(
            @ApiParam(value = "Application Id") @PathVariable("applicationId") Long applicationId,
            @ApiParam(value = "Application Object") @RequestBody ApplicationPatchDto applicationPatchDto
    ) {
        Application application = applicationService.updateApplicationByApplicationId(applicationId,applicationPatchDto);
        if (application==null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).build();
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).contentType(MediaType.APPLICATION_JSON).body(Commons.transformObject(application, ApplicationGetDto.class));
        }
    }

}
