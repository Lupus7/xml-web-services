package agentbackend.agentback.service;


import agentbackend.agentback.controller.dto.ClientDTO;
import agentbackend.agentback.controller.dto.NewAgentDTO;
import agentbackend.agentback.controller.dto.NewCompanyDTO;
import agentbackend.agentback.model.User;
import agentbackend.agentback.repository.UserRepository;
import agentbackend.agentback.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private RoleRepository roleRepository;

//    public List<ClientDTO> getAll() {
//        return userRepository.findAll().;
////                .stream()
////                .filter(user -> user.getRoles().contains("ROLE_CLIENT") && user.getRoles().size() == 1)
////                .map(ClientMapper::toDTO)
////                .collect(Collectors.toList());
//    }

    public boolean block(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null)
            return false;
        user.setBlocked(true);
        System.out.println("blocked " + user.getBlocked());
        userRepository.save(user);
        return true;
    }

    public boolean userExists(String username) {
        return userRepository.findByEmail(username) != null;
    }

    public User byEmail(String email){
        return userRepository.findByEmail(email);
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
        userRepository.save(user);
//        finalizeRegistration(user);

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
        userRepository.save(user);
//        finalizeRegistration(user);

        return true;
    }

//    private void finalizeRegistration(User user) {
//        Role role = roleRepository.findByName(user.getRoles().get(0));
//
//        for (Privilege p : role.getPrivileges()) {
//            user.setAuthorities(user.getAuthorities() + ";" + p.getName());
//        }
//
//        //TODO 2: add new cart
//        //user.getCart().setUser(user);
//        //user.getCart().setAds(new ArrayList<>());
//
//        //cartRepository.save(user.getCart());
//        userRepository.save(user);
//    }

    public Long getUserId(String email) {

        User user = userRepository.findByEmail(email);
        if (user != null)
            return user.getId();
        return null;
    }
}
