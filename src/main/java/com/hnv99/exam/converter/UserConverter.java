package com.hnv99.exam.converter;


import com.hnv99.exam.model.entity.User;
import com.hnv99.exam.model.form.UserForm;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface UserConverter {

    User fromToEntity(UserForm userForm);

    List<User> listFromToEntity(List<UserForm> list);

}
