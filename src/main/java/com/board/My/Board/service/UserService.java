package com.board.My.Board.service;

import com.board.My.Board.domain.User;
import com.board.My.Board.dto.UserForm;
import com.board.My.Board.exception.DuplicateEmailException;
import com.board.My.Board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signUpUser(UserForm userForm) throws Exception {
        validateDuplicateEmail(userForm.getEmail());
        userRepository.save(User.createUser(userForm));
    }

    private void validateDuplicateEmail(String email) throws Exception {
        if(userRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}
