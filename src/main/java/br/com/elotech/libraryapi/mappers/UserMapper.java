package br.com.elotech.libraryapi.mappers;

import br.com.elotech.libraryapi.dtos.UserRequest;
import br.com.elotech.libraryapi.dtos.UserResponse;
import br.com.elotech.libraryapi.models.User;
import org.springframework.beans.BeanUtils;

public class UserMapper {

    public static User toEntity(UserRequest request){
        var user = new User();
        BeanUtils.copyProperties(request, user);
        return user;
    }

    public static UserResponse toResponse(User user){
        var response = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
        return response;
    }
}
