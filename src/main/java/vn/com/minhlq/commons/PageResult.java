package vn.com.minhlq.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author MinhLQ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T extends Serializable> {
    private static final long serialVersionUID = 3420391142991247367L;

    private List<T> rows;

    private Long total;

    public static <T extends Serializable> PageResult of(List<T> rows, Long total) {
        return new PageResult<>(rows, total);
    }

}
