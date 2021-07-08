package vn.com.minhlq.api;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import vn.com.minhlq.service.TagsQueryService;

@Tag(name = "Tags")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagsApi {

    private TagsQueryService tagsQueryService;

    @GetMapping
    public ResponseEntity<?> getTags() {
        return ResponseEntity.ok(new HashMap<String, Object>() {
            {
                put("tags", tagsQueryService.allTags());
            }
        });
    }
}
