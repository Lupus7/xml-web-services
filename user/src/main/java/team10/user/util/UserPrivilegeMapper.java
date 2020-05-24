package team10.user.util;

import org.apache.commons.lang3.StringUtils;
import team10.user.models.User;
import team10.user.models.dto.UserPrivilegeDTO;

import java.util.stream.Collectors;

public class UserPrivilegeMapper {
    public static UserPrivilegeDTO toDTO(User user) {
        UserPrivilegeDTO retVal = new UserPrivilegeDTO();

        if (user.getRoles().contains("ROLE_COMPANY"))
            retVal.setName(user.getCompanyName());
        else
            retVal.setName(user.getFirstName() + " " + user.getLastName());
        retVal.setAddress(user.getAddress());
        retVal.setEmail(user.getEmail());
        retVal.setRole(StringUtils
                .join(user.getRoles()
                        .stream()
                        .map(UserPrivilegeMapper::removeRolePrefixAndUnderscore)
                        .collect(Collectors.toList()), ", "));

        return retVal;
    }

    private static String removeRolePrefixAndUnderscore(String role) {
        return role.replace("ROLE_", "").replace("_", " ");
    }
}
