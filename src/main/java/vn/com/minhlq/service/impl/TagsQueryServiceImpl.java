package vn.com.minhlq.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import vn.com.minhlq.mybatis.service.TagService;
import vn.com.minhlq.service.TagsQueryService;

@Service
@AllArgsConstructor
public class TagsQueryServiceImpl implements TagsQueryService {

    private TagService tagService;

    @Override
    public List<String> allTags() {
        return tagService.all();
    }

}
