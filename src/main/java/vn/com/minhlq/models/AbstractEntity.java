package vn.com.minhlq.models;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author MinhLQ
 */
@Data
@MappedSuperclass
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String createBy;

    private Long createAt = new Date().getTime();

    private String updateBy;

    private Long updateAt = new Date().getTime();

}
