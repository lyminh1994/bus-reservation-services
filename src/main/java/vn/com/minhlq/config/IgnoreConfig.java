package vn.com.minhlq.config;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author Minh Ly Quang
 */
@Data
public class IgnoreConfig {

    private List<String> pattern = Lists.newArrayList();

    private List<String> get = Lists.newArrayList();

    private List<String> post = Lists.newArrayList();

    private List<String> delete = Lists.newArrayList();

    private List<String> put = Lists.newArrayList();

    private List<String> head = Lists.newArrayList();

    private List<String> patch = Lists.newArrayList();

    private List<String> options = Lists.newArrayList();

    private List<String> trace = Lists.newArrayList();

}