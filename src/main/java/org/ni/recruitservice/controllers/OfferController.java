package org.ni.recruitservice.controllers;

import io.swagger.annotations.*;
import org.ni.recruitservice.dto.ApplicationGetDto;
import org.ni.recruitservice.dto.ApplicationPostDto;
import org.ni.recruitservice.dto.OfferGetDto;
import org.ni.recruitservice.dto.OfferPostDto;
import org.ni.recruitservice.model.Offer;
import org.ni.recruitservice.utils.Constants;
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

    @GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
    @ApiOperation(position = 1, value = "This endpoint is called to get offer list.", notes = "This endpoint is called to get offer list.", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Constants.OFFER_GET_200),
            @ApiResponse(code = 204, message = Constants.OFFER_GET_204),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity getOfferList() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).build();
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
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity postOffer(
            @ApiParam(value = "Offer Object") @RequestBody @Valid OfferPostDto offerPostDto
    ) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).contentType(MediaType.APPLICATION_JSON).build();
    }

    @PostMapping(path = "/{offerId}/applications", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(position = 1, value = "This endpoint is called to add a offer.", notes = "This endpoint is called to add a offer.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = Constants.OFFER_POST_201),
            @ApiResponse(code = 400, message = Constants.OFFER_POST_400),
            @ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR)
    })
    public ResponseEntity postApplicationForOffer(
            @ApiParam(value = "Application Object") @RequestBody @Valid ApplicationPostDto applicationPostDto
    ) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).build();
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
