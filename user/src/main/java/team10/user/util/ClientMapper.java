package team10.user.util;

import team10.user.models.User;
import team10.user.models.dto.ClientDTO;

public class ClientMapper {
    public static ClientDTO toDTO(User user) {
        ClientDTO retVal = new ClientDTO();
        retVal.setEmail(user.getEmail());
        retVal.setBlocked(user.getBlocked());
        return retVal;
    }
}
