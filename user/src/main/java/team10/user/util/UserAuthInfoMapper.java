package team10.user.util;

import team10.user.models.User;
import team10.user.models.dto.UserAuthInfo;

public class UserAuthInfoMapper {
    public static UserAuthInfo toDTO(User user) {
        UserAuthInfo retVal = new UserAuthInfo();
        retVal.setUsername(user.getEmail());
        retVal.setPassword(user.getPassword());
        retVal.setAuthorities(user.getAuthorities());
        retVal.setEnabled(!user.getBlocked() && user.getApproved());
        return retVal;
    }
}
