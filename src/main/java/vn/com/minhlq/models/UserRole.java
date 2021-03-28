package vn.com.minhlq.models;

import lombok.Data;
import vn.com.minhlq.models.unionkey.UserRoleKey;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

/**
 * @author MinhLQ
 */
@Data
@Entity
public class UserRole {

    @EmbeddedId
    private UserRoleKey id;

}
