package vn.com.minhlq.exception;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;

@Getter
@JsonRootName("errors")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(using = ErrorResourceSerializer.class)
public class ErrorResource {

    private List<FieldErrorResource> fieldErrors;

    public ErrorResource(List<FieldErrorResource> fieldErrorResources) {
        this.fieldErrors = fieldErrorResources;
    }

}
