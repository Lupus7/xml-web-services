package team10.admin.util;

import team10.admin.models.Privilege;
import team10.admin.models.dto.PrivilegeDTO;

public class PrivilegeMapper {
    public static PrivilegeDTO toActiveDTO(Privilege privilege) {
        PrivilegeDTO retVal = new PrivilegeDTO();
        retVal.setActive(true);
        retVal.setDef(false);
        retVal.setName(privilege.getName());

        return retVal;
    }

    public static PrivilegeDTO toInactiveDTO(Privilege privilege) {
        PrivilegeDTO retVal = new PrivilegeDTO();
        retVal.setActive(false);
        retVal.setDef(false);
        retVal.setName(privilege.getName());

        return retVal;
    }
}
