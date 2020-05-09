package team10.admin.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team10.admin.models.User;
import team10.admin.models.dto.ClientDTO;
import team10.admin.repositories.UserRepository;
import team10.admin.util.ClientMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    @Autowired
    UserRepository userRepository;

    public List<ClientDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRoles().contains("ROLE_USER") && user.getRoles().size() == 1)
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
}
