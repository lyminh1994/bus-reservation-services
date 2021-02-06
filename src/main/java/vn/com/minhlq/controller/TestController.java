package vn.com.minhlq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Minh Ly Quang
 */
@Slf4j
@RestController
public class TestController {

    @GetMapping("/test")
    public ResponseEntity<String> list() {
        log.info("Test list query");
        return ResponseEntity.ok("Test list");
    }

    @GetMapping("/test/{id}")
    public ResponseEntity<String> get(@PathVariable String id) {
        log.info("Test query");
        return ResponseEntity.ok("Test single " + id);
    }

    @PostMapping("/test")
    public ResponseEntity<String> add() {
        log.info("Test list added");
        return ResponseEntity.ok("Test add");
    }

    @PutMapping("/test/{id}")
    public ResponseEntity<String> update(@PathVariable Long id) {
        log.info("Test modification");
        return ResponseEntity.ok("Test update " + id);
    }

    @DeleteMapping("/test/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("Test remove");
        return ResponseEntity.ok("Test delete " + id);
    }

}
