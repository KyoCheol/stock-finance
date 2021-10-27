package com.board.My.Board.controller;

import com.board.My.Board.dto.UserForm;
import com.board.My.Board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@ResponseBody
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void signUpUser(@Valid @RequestBody UserForm userForm) throws Exception {
        userService.signUpUser(userForm);
    }
}
