package vn.com.minhlq.entity.unionkey;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Minh Ly Quang
 */
@Data
@Embeddable
public class RolePermissionKey implements Serializable {

    private static final long serialVersionUID = 6850974328279713855L;

    private Long roleId;

    private Long permissionId;

}
