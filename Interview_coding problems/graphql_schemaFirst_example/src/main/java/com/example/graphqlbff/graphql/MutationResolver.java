package com.example.graphqlbff.graphql;

import com.example.graphqlbff.dto.CreateUserInputDto;
import com.example.graphqlbff.dto.UserDto;
import com.example.graphqlbff.error.BadRequestException;
import com.example.graphqlbff.service.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MutationResolver {

  private final UserService userService;

  public MutationResolver(UserService userService) {
    this.userService = userService;
  }

  @MutationMapping
  public UserDto createUser(@Argument("input") CreateUserInputDto input) {
    if (input == null) {
      throw new BadRequestException("input is required");
    }
    if (input.email() == null || input.email().isBlank()) {
      throw new BadRequestException("email is required");
    }
    if (input.displayName() == null || input.displayName().isBlank()) {
      throw new BadRequestException("displayName is required");
    }
    return userService.create(input);
  }
}
