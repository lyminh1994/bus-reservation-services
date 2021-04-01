package vn.com.minhlq.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author MinhLQ
 */
@Data
@AllArgsConstructor
public class PageResult<T> {

    private final Long total;

    private final Integer pageNumber;

    private final Integer pageSize;

    private final List<T> rows;

}
