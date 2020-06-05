package team10.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team10.user.models.Privilege;
import team10.user.models.Role;
import team10.user.models.User;
import team10.user.models.dto.CarDTO;
import team10.user.models.dto.ClientDTO;
import team10.user.models.dto.NewAgentDTO;
import team10.user.models.dto.NewCompanyDTO;
import team10.user.repositories.RoleRepository;
import team10.user.repositories.UserRepository;
import team10.user.util.ClientMapper;
import team10.user.util.UserMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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
        System.out.println("blocked " + user.getBlocked());
        userRepository.save(user);
        return true;
    }

    public boolean activate(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;
        user.setBlocked(false);
        System.out.println("unblocked " + user.getBlocked());
        userRepository.save(user);
        return true;
    }

    public boolean delete(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;
        //TODO 1: close all bookings

        //TODO 3: fix principal and auth stuff
        //ResponseEntity<CarDTO[]> cars = new RestTemplate().getForEntity("http://localhost:8080/cars-ads/cars/client", CarDTO[].class);

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

        //TODO 2: add new cart
        //user.getCart().setUser(user);
        //user.getCart().setAds(new ArrayList<>());

        //cartRepository.save(user.getCart());
        userRepository.save(user);
    }

    public Long getUserId(String email) {

        User user = userRepository.findByEmail(email);
        if (user != null)
            return user.getId();
        return null;
    }
}
