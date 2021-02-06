package vn.com.minhlq.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author Minh Ly Quang
 */
@Data
@MappedSuperclass
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String createBy;

    private Long createTime = new Date().getTime();

    private String updateBy;

    private Long updateTime = new Date().getTime();

}
