package com.zzbbc.spring.core.controllers.impl;

import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zzbbc.spring.core.controllers.BaseController;
import com.zzbbc.spring.core.dto.impl.UserDto;
import com.zzbbc.spring.core.models.impl.User;
import com.zzbbc.spring.core.services.impl.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController<UserService, UUID, UserDto> {

    public UserController(UserService service) {
        super(service);
    }

    @PostMapping
    public User add() {
        User model = new User();
        model.setToken("token");
        return this.service.saveModel(model);
    }

    @Override
    protected UUID toId(String id) {
        return UUID.fromString(id);
    }

}
