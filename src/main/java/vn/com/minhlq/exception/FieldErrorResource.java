package vn.com.minhlq.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldErrorResource {

    private String resource;

    private String field;

    private String code;

    private String message;

    public FieldErrorResource(String resource, String field, String code, String message) {
        this.resource = resource;
        this.field = field;
        this.code = code;
        this.message = message;
    }

}
