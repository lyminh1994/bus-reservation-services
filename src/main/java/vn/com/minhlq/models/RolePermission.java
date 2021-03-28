package vn.com.minhlq.models;

import lombok.Data;
import vn.com.minhlq.models.unionkey.RolePermissionKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * @author MinhLQ
 */
@Data
@Entity
public class RolePermission {

    @EmbeddedId
    private RolePermissionKey id;

}
