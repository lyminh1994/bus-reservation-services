package vn.com.minhlq.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.PageRequest;
import vn.com.minhlq.common.CommonConst;
import vn.com.minhlq.payload.PageCondition;

/**
 * @author Minh Ly Quang
 */
@UtilityClass
public class PageUtil {

    /**
     * Verify the paging parameter, which is NULL, set the default value of the paging parameter
     *
     * @param condition Query parameter
     * @param clazz     Class
     * @param <T>       {@link PageCondition}
     */
    public <T extends PageCondition> void checkPageCondition(T condition, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        if (ObjectUtils.isEmpty(condition)) {
            condition = clazz.newInstance();
        }
        // Verify paging parameters
        if (ObjectUtils.isEmpty(condition.getCurrentPage())) {
            condition.setCurrentPage(CommonConst.DEFAULT_CURRENT_PAGE);
        }
        if (ObjectUtils.isEmpty(condition.getPageSize())) {
            condition.setPageSize(CommonConst.DEFAULT_PAGE_SIZE);
        }
    }

    /**
     * Build based on paging parameters {@link PageRequest}
     *
     * @param condition Query parameter
     * @param <T>       {@link PageCondition}
     * @return {@link PageRequest}
     */
    public <T extends PageCondition> PageRequest ofPageRequest(T condition) {
        return PageRequest.of(condition.getCurrentPage(), condition.getPageSize());
    }

}
