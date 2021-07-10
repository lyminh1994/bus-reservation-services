package vn.com.minhlq.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.joda.time.DateTime;

import lombok.Data;

/**
 * @author minhlq
 */
@Data
@MappedSuperclass
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String createBy;

    private DateTime createAt = new DateTime();

    private String updateBy;

    private DateTime updateAt = new DateTime();

}
