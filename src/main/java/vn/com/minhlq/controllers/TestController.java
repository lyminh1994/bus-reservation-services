package vn.com.minhlq.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.com.minhlq.commons.ApiResponse;

/**
 * @author MinhLQ
 */
@Slf4j
@RestController
public class TestController {

    @GetMapping("/test")
    public ApiResponse list() {
        log.info("Test list query");
        return ApiResponse.ofSuccess("Test list");
    }

    @GetMapping("/test/{id}")
    public ApiResponse get(@PathVariable String id) {
        log.info("Test query");
        return ApiResponse.ofSuccess("Test single " + id);
    }

    @PostMapping("/test")
    public ApiResponse add() {
        log.info("Test list added");
        return ApiResponse.ofSuccess("Test add");
    }

    @PutMapping("/test/{id}")
    public ApiResponse update(@PathVariable Long id) {
        log.info("Test modification");
        return ApiResponse.ofSuccess("Test update " + id);
    }

    @DeleteMapping("/test/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        log.info("Test remove");
        return ApiResponse.ofSuccess("Test delete " + id);
    }

}
