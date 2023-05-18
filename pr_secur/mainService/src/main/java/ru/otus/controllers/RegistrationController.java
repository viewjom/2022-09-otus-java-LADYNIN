package ru.otus.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.auth.entity.Role;
import ru.otus.auth.entity.User;
import ru.otus.auth.service.RoleService;
import ru.otus.auth.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class RegistrationController {


    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {

        System.out.println("Get userForm.getPassword()");

        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {

        Set<Role> roles = new HashSet<>();
        User user = new User();
        roles.add(roleService.getRoleByName("ROLE_USER"));
        System.out.println("post userForm.getUsername() = " + userForm.getUsername());
        System.out.println("post userForm.getPassword() = " + userForm.getPassword());
        System.out.println("post userForm.getPasswordConfirm() = " + userForm.getPasswordConfirm());


        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        user.setRoles(roles);
        userService.addUser(userForm);

            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
        //    return "registration";


        return "redirect:/";
    }
}