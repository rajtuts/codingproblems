package com.example.graphqlbff.graphql;

import com.example.graphqlbff.dto.UserConnectionDto;
import com.example.graphqlbff.dto.UserDto;
import com.example.graphqlbff.service.UserService;
import graphql.schema.DataFetchingEnvironment;
import java.util.concurrent.CompletableFuture;
import org.dataloader.DataLoader;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class QueryResolver {

  private final UserService userService;

  public QueryResolver(UserService userService) {
    this.userService = userService;
  }

  @QueryMapping
  public String hello(@Argument String name) {
    String effective = (name == null || name.isBlank()) ? "world" : name.trim();
    return "Hello, " + effective;
  }

  @QueryMapping
  public CompletableFuture<UserDto> userById(@Argument String id, DataFetchingEnvironment env) {
    DataLoader<String, UserDto> loader = env.getDataLoader(UserDataLoaderConfig.USER_BY_ID_LOADER);
    return loader.load(id);
  }

  @QueryMapping
  public UserConnectionDto users(@Argument Integer first, @Argument String after) {
    int effectiveFirst = (first == null) ? 10 : first;
    return userService.list(effectiveFirst, after);
  }
}
