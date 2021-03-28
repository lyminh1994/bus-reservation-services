package vn.com.minhlq.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import vn.com.minhlq.commons.Const;
import vn.com.minhlq.models.User;
import vn.com.minhlq.utils.core.StringUtil;

import java.io.Serializable;

/**
 * @author MinhLQ
 */
@Data
public class OnlineUserDto implements Serializable {

    private Long id;

    private String username;

    private String nickname;

    private String phone;

    private String email;

    private Long birthday;

    private Integer sex;

    public static OnlineUserDto create(User user) {
        OnlineUserDto onlineUserDto = new OnlineUserDto();
        BeanUtils.copyProperties(user, onlineUserDto);
        // Desensitization
        onlineUserDto.setPhone(StringUtil.hide(user.getPhone(), 3, 7));
        onlineUserDto.setEmail(StringUtil.hide(user.getEmail(), 1, StringUtils.indexOfIgnoreCase(user.getEmail(), Const.SYMBOL_EMAIL)));
        return onlineUserDto;
    }

}
