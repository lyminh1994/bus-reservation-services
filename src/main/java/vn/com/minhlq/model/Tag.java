package vn.com.minhlq.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Tag {

    private Long id;

    private String name;

    public Tag(String name) {
        this.name = name;
    }

}
