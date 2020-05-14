package team10.admin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team10.admin.models.Privilege;
import team10.admin.models.Role;
import team10.admin.models.User;
import team10.admin.models.dto.ClientDTO;
import team10.admin.models.dto.NewAgentDTO;
import team10.admin.models.dto.NewCompanyDTO;
import team10.admin.repositories.CartRepository;
import team10.admin.repositories.RoleRepository;
import team10.admin.repositories.UserRepository;
import team10.admin.util.ClientMapper;
import team10.admin.util.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CartRepository cartRepository;

    public List<ClientDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRoles().contains("ROLE_CLIENT") && user.getRoles().size() == 1)
                .map(ClientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public boolean block(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;
        user.setBlocked(true);
        if(userRepository.save(user) == null)
            return false;
        return true;
    }

    public boolean activate(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;
        user.setBlocked(false);
        if(userRepository.save(user) == null)
            return false;
        return true;
    }

    public boolean delete(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null)
            return false;
        userRepository.delete(user);
        return true;
    }

    public boolean registerCompany(NewCompanyDTO newCompanyDTO) {
        User user = userRepository.findByEmail(newCompanyDTO.getEmail());
        if (user != null)
            return false;

        user = userRepository.findByBusinessNumber(newCompanyDTO.getBusinessNumber());
        if (user != null)
            return false;

        user = UserMapper.toEntity(newCompanyDTO);
        finalizeRegistration(user);

        return true;
    }

    public boolean registerAgent(NewAgentDTO newAgentDTO) {
        User user = userRepository.findByEmail(newAgentDTO.getEmail());
        if (user != null)
            return false;

        user = userRepository.findByBusinessNumber(newAgentDTO.getBusinessNumber());
        if (user != null)
            return false;

        user = UserMapper.toEntity(newAgentDTO);
        finalizeRegistration(user);

        return true;
    }

    private void finalizeRegistration(User user) {
        Role role = roleRepository.findByName(user.getRoles().get(0));

        for (Privilege p : role.getPrivileges()) {
            user.setAuthorities(user.getAuthorities() + ";" + p.getName());
        }

        user.getCart().setUser(user);
        user.getCart().setAds(new ArrayList<>());

        cartRepository.save(user.getCart());
        userRepository.save(user);
    }
}
