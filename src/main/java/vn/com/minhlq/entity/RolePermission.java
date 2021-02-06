package vn.com.minhlq.entity;

import lombok.Data;
import vn.com.minhlq.entity.unionkey.RolePermissionKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * @author Minh Ly Quang
 */
@Data
@Entity
public class RolePermission {

    @EmbeddedId
    private RolePermissionKey id;

}
