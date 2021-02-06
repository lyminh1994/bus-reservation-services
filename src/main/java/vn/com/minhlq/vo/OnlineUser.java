package vn.com.minhlq.vo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import vn.com.minhlq.common.CommonConst;
import vn.com.minhlq.entity.User;
import vn.com.minhlq.utils.core.StringUtil;

import java.io.Serializable;

/**
 * @author Minh Ly Quang
 */
@Data
public class OnlineUser implements Serializable {

    private Long id;

    private String username;

    private String nickname;

    private String phone;

    private String email;

    private Long birthday;

    private Integer sex;

    public static OnlineUser create(User user) {
        OnlineUser onlineUser = new OnlineUser();
        BeanUtils.copyProperties(user, onlineUser);
        // Desensitization
        onlineUser.setPhone(StringUtil.hide(user.getPhone(), 3, 7));
        onlineUser.setEmail(StringUtil.hide(user.getEmail(), 1, StringUtils.indexOfIgnoreCase(user.getEmail(), CommonConst.SYMBOL_EMAIL)));
        return onlineUser;
    }

}
