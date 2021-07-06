package vn.com.minhlq.model;

import lombok.Data;
import vn.com.minhlq.model.unionkey.UserRoleKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * @author minhlq
 */
@Data
@Entity
public class UserRole {

    @EmbeddedId
    private UserRoleKey id;

}
