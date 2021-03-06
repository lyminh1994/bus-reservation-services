package vn.com.minhlq.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author minhlq
 */
@Data
@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    private Integer type;

    private String expression;

    private String method;

    private Integer sort;

    private Long parentId;

}
