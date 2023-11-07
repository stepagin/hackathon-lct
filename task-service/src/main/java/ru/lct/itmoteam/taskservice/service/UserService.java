package ru.lct.itmoteam.taskservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.lct.itmoteam.taskservice.model.UserEntity;
import ru.lct.itmoteam.taskservice.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void registerUser(UserEntity user) {
        // Добавить шифрование
        userRepository.save(user);
    }

    public UserEntity findUserByUsername(String username) {
        // Реализуйте метод поиска пользователя по имени
        return userRepository.findByUsername(username);
    }

    public void deleteUser(Long userId) {
        // Реализуйте метод удаления пользователя по его идентификатору
        userRepository.deleteById(userId);
    }
}
