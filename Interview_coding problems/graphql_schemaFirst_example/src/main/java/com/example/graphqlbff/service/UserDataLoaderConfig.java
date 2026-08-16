package com.example.graphqlbff.graphql;

import com.example.graphqlbff.dto.UserDto;
import com.example.graphqlbff.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.dataloader.MappedBatchLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDataLoaderConfig {

  public static final String USER_BY_ID_LOADER = "userByIdLoader";

  @Bean
  public DataLoaderRegistry dataLoaderRegistry(UserService userService) {
    DataLoaderRegistry registry = new DataLoaderRegistry();

    MappedBatchLoader<String, UserDto> batchLoader = ids -> CompletableFuture.supplyAsync(() -> {
      List<String> idList = List.copyOf(ids);
      List<UserDto> users = userService.findByIds(idList);
      Map<String, UserDto> mapped = new HashMap<>(idList.size());
      for (int i = 0; i < idList.size(); i++) {
        mapped.put(idList.get(i), users.get(i));
      }
      return mapped;
    });

    registry.register(USER_BY_ID_LOADER, DataLoader.newMappedDataLoader(batchLoader));
    return registry;
  }
}
