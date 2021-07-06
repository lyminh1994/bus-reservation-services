package vn.com.minhlq.model;

import lombok.Data;
import vn.com.minhlq.model.unionkey.RolePermissionKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * @author minhlq
 */
@Data
@Entity
public class RolePermission {

    @EmbeddedId
    private RolePermissionKey id;

}
