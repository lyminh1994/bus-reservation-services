package vn.com.minhlq.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Minh Ly Quang
 */
@Data
@Entity
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

}
