package vn.com.minhlq.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Minh Ly Quang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T extends Serializable> {

    private List<T> rows;

    private Long total;

    public static <T extends Serializable> PageResult of(List<T> rows, Long total) {
        return new PageResult<>(rows, total);
    }

}
