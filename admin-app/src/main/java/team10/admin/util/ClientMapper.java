package team10.admin.util;

import team10.admin.models.User;
import team10.admin.models.dto.ClientDTO;

public class ClientMapper {
    public static ClientDTO toDTO(User user) {
        ClientDTO retVal = new ClientDTO();
        retVal.setEmail(user.getEmail());
        retVal.setBlocked(user.getBlocked());
        return retVal;
    }
}
