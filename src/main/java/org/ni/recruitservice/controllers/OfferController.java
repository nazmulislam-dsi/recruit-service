package org.ni.recruitservice.controllers;

import io.swagger.annotations.*;
import org.hibernate.exception.ConstraintViolationException;
import org.ni.recruitservice.dto.ApplicationGetDto;
import org.ni.recruitservice.dto.ApplicationPostDto;
import org.ni.recruitservice.dto.OfferGetDto;
import org.ni.recruitservice.dto.OfferPostDto;
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
@RequestMapping(path ="/offers")
public class OfferController {

    private OfferService offerService;

    private ApplicationService applicationService;

    @Autowired
    public OfferController(OfferService offerService, ApplicationService applicationService) {
        this.offerService = offerService;
        this.applicationService = applicationService;
    }

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiOperation(position = 1, value = "This endpoint is called to get offer list.", notes = "This endpoint is called to get offer list.", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Constants.OFFER_GET_200),
            @ApiResponse(code = 204, message = Constants.OFFER_GET_204),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity getOfferList() {
        try {
            List<Offer> offerList = offerService.getAllOffers();
            if (Commons.isEmpty(offerList)) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).build();
            } else {
                return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(Commons.transformObjectList(offerList, OfferGetDto.class));
            }
        }catch (ApiException ex) {
            return ResponseEntity.status(ex.getCode()).contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(ex.getMessage());
        }
    }

    @GetMapping(path = "/{offerId}", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(position = 1, value = "This endpoint is called to get a single offer.", notes = "This endpoint is called to get a single offer.", response = OfferGetDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Constants.OFFER_GET_200),
            @ApiResponse(code = 204, message = Constants.OFFER_GET_204),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity getOffer(
            @ApiParam(value = "Offer Id") @PathVariable("offerId") Long offerId
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).build();
    }

    @PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
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
        try {
            Offer offer = Commons.transformObject(offerPostDto,Offer.class);
            offer = offerService.saveOrUpdateOffer(offer);
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(Commons.transformObject(offer,OfferGetDto.class));
        }catch (ConstraintViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).build();
        }catch (ApiException ex) {
            return ResponseEntity.status(ex.getCode()).contentType(MediaType.APPLICATION_JSON).build();
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).build();
        }
    }

    @PostMapping(path = "/{offerId}/applications", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE})
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
        try{
            Application application = applicationService.saveOrUpdateApplication(applicationPostDto,offerId);
            return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(Commons.transformObject(application,ApplicationGetDto.class));
        }catch (ConstraintViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON).build();
        }catch (ApiException ex) {
            return ResponseEntity.status(ex.getCode()).contentType(MediaType.APPLICATION_JSON).build();
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).build();
        }
    }

    @GetMapping(path = "/{offerId}/applications", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiOperation(position = 1, value = "This endpoint is called to add a offer.", notes = "This endpoint is called to add a offer.", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Constants.APPLICATION_GET_200),
            @ApiResponse(code = 204, message = Constants.APPLICATION_GET_204),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity getApplicationListForAOffer() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).build();
    }

    @GetMapping(path = "/{offerId}/applications/{applicationsId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiOperation(position = 1, value = "This endpoint is called to add a offer.", notes = "This endpoint is called to add a offer.", response = ApplicationGetDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Constants.APPLICATION_GET_200),
            @ApiResponse(code = 204, message = Constants.APPLICATION_GET_204),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity getAnApplicationForAOffer() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).build();
    }

}
