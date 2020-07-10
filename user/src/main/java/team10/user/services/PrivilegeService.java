package team10.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team10.user.models.Privilege;
import team10.user.models.Role;
import team10.user.models.User;
import team10.user.models.dto.PrivilegeDTO;
import team10.user.models.dto.UserPrivilegeDTO;
import team10.user.repositories.PrivilegeRepository;
import team10.user.repositories.RoleRepository;
import team10.user.repositories.UserRepository;
import team10.user.util.PrivilegeMapper;
import team10.user.util.UserPrivilegeMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrivilegeService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    public void addDefaultPrivileges(User user) {
        if (user.getAuthorities() != null && user.getAuthorities().contains(";DEFAULT")) {
            Role role = roleRepository.findByName(user.getRoles().get(0));
            if (role != null) {
                user.setAuthorities(role.getName() + ";" + role.getPrivileges().stream().map(Privilege::getName).collect(Collectors.joining(";")));
                userRepository.save(user);
            }
        }
    }

    public boolean updatePrivilege(String email, PrivilegeDTO privilegeDTO) {
        User user = userRepository.findByEmail(email);
        if (user == null) return false;

        Privilege privilege = privilegeRepository.findByName(privilegeDTO.getName());
        if (privilege == null) return false;

        if (user.getPrivileges().contains(privilegeDTO.getName()) && !privilegeDTO.isActive()) {
            user.setAuthorities(user.getAuthorities().replace(privilegeDTO.getName(), "").replace(";;", ";"));
            userRepository.save(user);
        } else if (!user.getPrivileges().contains(privilegeDTO.getName()) && privilegeDTO.isActive()) {
            user.setAuthorities(user.getAuthorities() + ";" + privilegeDTO.getName());
            userRepository.save(user);
        }

        return true;
    }

    public List<UserPrivilegeDTO> getAllUserPrivileges() {
        List<User> users = userRepository.findAll();
        if (users == null) return null;

        users.stream().forEach(this::addDefaultPrivileges);

        return users.stream().map(UserPrivilegeMapper::toDTO).collect(Collectors.toList());
    }

    public List<PrivilegeDTO> getByUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) return null;

        addDefaultPrivileges(user);

        return extractPrivileges(user);
    }

    public List<PrivilegeDTO> getAvailableByUser(String email) {
        List<PrivilegeDTO> used = getByUser(email);
        List<Privilege> all = privilegeRepository.findAll();
        if (all == null) return null;

        return all
                .stream()
                .filter(p -> {
                    for (PrivilegeDTO usedP : used)
                        if (p.getName().equals(usedP.getName()))
                            return false;
                    return true;
                })
                .map(PrivilegeMapper::toInactiveDTO)
                .collect(Collectors.toList());
    }

    private List<PrivilegeDTO> extractPrivileges(User user) {
        List<PrivilegeDTO> retVal = new ArrayList<>();

        for (String privilege : user.getPrivileges()) {
            Privilege p = privilegeRepository.findByName(privilege);
            if (p != null) {
                retVal.add(PrivilegeMapper.toActiveDTO(p));
            }
        }

        setDefaultPrivileges(user, retVal);

        return retVal;
    }

    private void setDefaultPrivileges(User user, List<PrivilegeDTO> privilegeDTOS) {
        List<Role> userRoles = new ArrayList<>();

        for (String role : user.getRoles()) {
            Role r = roleRepository.findByName(role);
            if (r != null) {
                userRoles.add(r);
            }
        }

        userRoles.forEach(role -> {
            privilegeDTOS.forEach(privilegeDTO -> {
                List<Privilege> temp = role
                        .getPrivileges()
                        .stream()
                        .filter(privilege -> privilege.getName() == privilegeDTO.getName())
                        .collect(Collectors.toList());

                if (!temp.isEmpty()) privilegeDTO.setDef(true);
            });
        });
    }
}
