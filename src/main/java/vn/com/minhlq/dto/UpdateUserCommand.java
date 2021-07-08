package vn.com.minhlq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.com.minhlq.model.User;
import vn.com.minhlq.validation.UpdateUserConstraint;

@Getter
@AllArgsConstructor
@UpdateUserConstraint
public class UpdateUserCommand {

    private User targetUser;

    private UpdateUserParam param;

}
