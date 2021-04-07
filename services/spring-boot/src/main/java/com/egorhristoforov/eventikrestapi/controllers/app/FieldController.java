package com.egorhristoforov.eventikrestapi.controllers.app;

import com.egorhristoforov.eventikrestapi.dtos.requests.field.CreateConfigurationFieldRequest;
import com.egorhristoforov.eventikrestapi.dtos.requests.field.UpdateConfigurationFieldRequest;
import com.egorhristoforov.eventikrestapi.dtos.responses.field.CreateFieldResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.field.FieldListResponse;
import com.egorhristoforov.eventikrestapi.dtos.responses.field.UpdateFieldResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/fields")
@Validated
@Api(value = "field-controller", tags = "/fields")
public class FieldController {

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get fields list")
    public ResponseEntity<List<FieldListResponse>> getConfigurationFields() {
        return null;
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Create field", authorizations = {@Authorization(value = "Access token")})
    public ResponseEntity<CreateFieldResponse> createConfigurationField(@Valid @RequestBody CreateConfigurationFieldRequest body) {
        return null;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Edit field by id", authorizations = {@Authorization(value = "Access token")})
    public ResponseEntity<UpdateFieldResponse> editConfigurationField(@PathVariable(value = "id") @Positive Long fieldId,
                                                                      @Valid @RequestBody UpdateConfigurationFieldRequest body) {
        return null;
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete field by id", authorizations = {@Authorization(value = "Access token")})
    public void deleteConfigurationField(@PathVariable(value = "id") @Positive Long fieldId) {

    }
}
