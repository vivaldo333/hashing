package com.ks.hashing.api;

import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping
    public String showUserData(@AuthenticationPrincipal User user) {
        return "Welcome User: " + user.getUsername();
    }

    @GetMapping(path = "guest")
    public String showGuestUserData(@AuthenticationPrincipal User user) {
        return "Welcome Guest: " + user.getUsername();
    }
}
