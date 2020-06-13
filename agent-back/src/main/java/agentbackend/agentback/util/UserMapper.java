package agentbackend.agentback.util;

import agentbackend.agentback.controller.dto.NewAgentDTO;
import agentbackend.agentback.controller.dto.NewCompanyDTO;
import agentbackend.agentback.model.User;

import java.util.ArrayList;

public class UserMapper {
    public static User toEntity(NewAgentDTO newAgentDTO) {
        User retVal = new User();
        retVal.setAddress(newAgentDTO.getAddress());
        retVal.setApproved(true);
        //retVal.setAuthorities("ROLE_AGENT");
        retVal.setBlocked(false);
        retVal.setBookings(new ArrayList<>());
        retVal.setBusinessNumber(newAgentDTO.getBusinessNumber());
        retVal.setCars(new ArrayList<>());
        //retVal.setCart(new Cart());
        retVal.setCompanyName("");
        retVal.setEmail(newAgentDTO.getEmail());
        retVal.setFirstName(newAgentDTO.getFirstName());
        retVal.setLastName(newAgentDTO.getLastName());
        retVal.setPassword(newAgentDTO.getPassword());
        return retVal;
    }

    public static User toEntity(NewCompanyDTO newCompanyDTO) {
        User retVal = new User();
        retVal.setAddress(newCompanyDTO.getAddress());
        retVal.setApproved(true);
      //  retVal.setAuthorities("ROLE_COMPANY");
        retVal.setBlocked(false);
        retVal.setBookings(new ArrayList<>());
        retVal.setBusinessNumber(newCompanyDTO.getBusinessNumber());
        retVal.setCars(new ArrayList<>());
        //retVal.setCart(new Cart());
        retVal.setCompanyName(newCompanyDTO.getName());
        retVal.setEmail(newCompanyDTO.getEmail());
        retVal.setFirstName("");
        retVal.setLastName("");
        retVal.setPassword(newCompanyDTO.getPassword());
        return retVal;
    }
}
