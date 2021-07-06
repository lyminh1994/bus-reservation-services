package vn.com.minhlq.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import vn.com.minhlq.common.R;

/**
 * @author minhlq
 */
@Slf4j
@RestController
@Tag(name = "Test Controller")
public class TestController {

    @GetMapping("/test")
    public R list() {
        log.info("Test list query");
        return R.ofSuccess("Test list");
    }

    @GetMapping("/test/{id}")
    public R get(@PathVariable String id) {
        log.info("Test query");
        return R.ofSuccess("Test single " + id);
    }

    @PostMapping("/test")
    public R add() {
        log.info("Test list added");
        return R.ofSuccess("Test add");
    }

    @PutMapping("/test/{id}")
    public R update(@PathVariable Long id) {
        log.info("Test modification");
        return R.ofSuccess("Test update " + id);
    }

    @DeleteMapping("/test/{id}")
    public R delete(@PathVariable Long id) {
        log.info("Test remove");
        return R.ofSuccess("Test delete " + id);
    }

}
