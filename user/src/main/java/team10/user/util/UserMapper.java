package team10.user.util;

import com.car_rent.agent_api.ObjectFactory;
import com.car_rent.agent_api.UserDetails;
import team10.user.models.User;
import team10.user.models.dto.NewAgentDTO;
import team10.user.models.dto.NewCompanyDTO;
import team10.user.models.dto.RegistrationRequest;

import java.util.ArrayList;

public class UserMapper {
    public static User toEntity(NewAgentDTO newAgentDTO) {
        User retVal = new User();
        retVal.setAddress(newAgentDTO.getAddress());
        retVal.setApproved(true);
        retVal.setAuthorities("ROLE_AGENT");
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

    public static User toEntity(RegistrationRequest registrationRequest) {
        User retVal = new User();
        retVal.setAddress(registrationRequest.getAddress());
        retVal.setApproved(true);
        retVal.setAuthorities("ROLE_CLIENT;DEFAULT");
        retVal.setBlocked(false);
        retVal.setBookings(new ArrayList<>());
        retVal.setCars(new ArrayList<>());
        //retVal.setCart(new Cart());
        retVal.setCompanyName("");
        retVal.setEmail(registrationRequest.getEmail());
        retVal.setFirstName(registrationRequest.getFirstName());
        retVal.setLastName(registrationRequest.getLastName());
        retVal.setPassword(registrationRequest.getPassword());
        return retVal;
    }

    public static User toEntity(NewCompanyDTO newCompanyDTO) {
        User retVal = new User();
        retVal.setAddress(newCompanyDTO.getAddress());
        retVal.setApproved(true);
        retVal.setAuthorities("ROLE_COMPANY");
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

    public static UserDetails toDetails(User user) {
        UserDetails userDetails = new ObjectFactory().createUserDetails();
        userDetails.setId(user.getId());
        userDetails.setAddress(user.getAddress());
        userDetails.setApproved(user.getApproved());
        userDetails.setBlocked(user.getBlocked());
        userDetails.getBookings().addAll(user.getBookings());
        if (user.getBusinessNumber() != null)
            userDetails.setBuisinessNumber(user.getBusinessNumber());
        else
            userDetails.setBuisinessNumber("");
        userDetails.getCars().addAll(user.getCars());
        if (user.getCart() != null)
            userDetails.setCart(user.getCart());
        else
            userDetails.setCart(-1);
        userDetails.setCompanyName(user.getCompanyName());
        userDetails.setEmail(user.getEmail());
        userDetails.setFirstName(user.getFirstName());
        userDetails.setLastName(user.getLastName());
        userDetails.setPassword(user.getPassword());
        if (user.getRoles().isEmpty())
            userDetails.setRole("ROLE_AGENT");
        else
            userDetails.setRole(user.getRoles().get(0));
        return userDetails;
    }
}
