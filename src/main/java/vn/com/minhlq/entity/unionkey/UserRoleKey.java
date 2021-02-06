package vn.com.minhlq.entity.unionkey;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Minh Ly Quang
 */
@Data
@Embeddable
public class UserRoleKey implements Serializable {

    private static final long serialVersionUID = 5633412144183654743L;
    
    private Long userId;

    private Long roleId;

}
