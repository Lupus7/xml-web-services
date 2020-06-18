package team10.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team10.user.models.Privilege;
import team10.user.models.Role;
import team10.user.models.User;
import team10.user.models.dto.CarDTO;
import team10.user.models.dto.ClientDTO;
import team10.user.models.dto.NewAgentDTO;
import team10.user.models.dto.NewCompanyDTO;
import team10.user.proxy.CarsAdsProxy;
import team10.user.proxy.RentProxy;
import team10.user.repositories.RoleRepository;
import team10.user.repositories.UserRepository;
import team10.user.util.ClientMapper;
import team10.user.util.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CarsAdsProxy carsAdsProxy;

    @Autowired
    private RentProxy rentProxy;

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

        ResponseEntity<List<CarDTO>> cars = carsAdsProxy.getClientCars(email + ";MASTER");

        if (cars.getStatusCode().isError() || cars.getBody() == null)
            return false;

        for (CarDTO car : cars.getBody()) {
            carsAdsProxy.deleteCar(car.getCarId(), email + ";MASTER");
        }

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

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newCompanyDTO.getPassword());
        //System.out.println(encodedPassword);
        newCompanyDTO.setPassword(encodedPassword);

        user = UserMapper.toEntity(newCompanyDTO);

        return finalizeRegistration(user);
    }

    public boolean registerAgent(NewAgentDTO newAgentDTO) {
        User user = userRepository.findByEmail(newAgentDTO.getEmail());
        if (user != null)
            return false;

        user = userRepository.findByBusinessNumber(newAgentDTO.getBusinessNumber());
        if (user != null)
            return false;

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(newAgentDTO.getPassword());
        //System.out.println(encodedPassword);
        newAgentDTO.setPassword(encodedPassword);

        user = UserMapper.toEntity(newAgentDTO);

        return finalizeRegistration(user);
    }

    private boolean finalizeRegistration(User user) {
        Role role = roleRepository.findByName(user.getRoles().get(0));

        if (role == null)
            return false;

        for (Privilege p : role.getPrivileges()) {
            user.setAuthorities(user.getAuthorities() + ";" + p.getName());
        }

        // TODO: 400 no body error
//        ResponseEntity<String> responseEntity = rentProxy.createCart(user.getEmail() + ";MASTER", "0");
//
//        if (responseEntity.getStatusCode().isError())
//            return false;

        userRepository.save(user);
        return true;
    }

    public Long getUserId(String email) {

        User user = userRepository.findByEmail(email);
        if (user != null)
            return user.getId();
        return null;
    }

    public String getUserEmail(Long id) {

        Optional<User> user = userRepository.findById(id);
        if (user.isPresent())
            return user.get().getEmail();
        return null;
    }
}
