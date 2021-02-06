package vn.com.minhlq.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Minh Ly Quang
 */
@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Long createTime;

    private Long updateTime;

}
