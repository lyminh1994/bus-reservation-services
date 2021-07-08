package vn.com.minhlq.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.minhlq.common.CursorPager.Direction;

@Data
@NoArgsConstructor
public class CursorPageParameter<T> {

    private static final int MAX_LIMIT = 1000;

    private int limit = 20;

    private T cursor;

    private Direction direction;

    public CursorPageParameter(T cursor, int limit, Direction direction) {
        setLimit(limit);
        setCursor(cursor);
        setDirection(direction);
    }

    public boolean isNext() {
        return direction == Direction.NEXT;
    }

    public int getQueryLimit() {
        return limit + 1;
    }

    private void setLimit(int limit) {
        if (limit > MAX_LIMIT) {
            this.limit = MAX_LIMIT;
        } else if (limit > 0) {
            this.limit = limit;
        }
    }

}
