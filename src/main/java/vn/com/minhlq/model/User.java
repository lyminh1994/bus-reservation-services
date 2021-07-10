package vn.com.minhlq.model;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author minhlq
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    private String email;

    private DateTime birthday;

    private Integer gender;

    private Integer status;

    private DateTime createAt;

    private DateTime updateAt;

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
