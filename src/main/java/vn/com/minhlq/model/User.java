package vn.com.minhlq.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author minhlq
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String phone;

    private String email;

    private Long birthday;

    private Integer gender;

    private Integer status;

    private Long createTime;

    private Long updateTime;

    public User(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public void update(String email, String username, String password) {
        if (!StringUtils.isEmpty(email)) {
            this.email = email;
        }

        if (!StringUtils.isEmpty(username)) {
            this.username = username;
        }

        if (!StringUtils.isEmpty(password)) {
            this.password = password;
        }
    }

}
