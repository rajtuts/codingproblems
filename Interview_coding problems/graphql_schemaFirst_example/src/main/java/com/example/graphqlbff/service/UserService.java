package com.example.graphqlbff.service;

import com.example.graphqlbff.dto.CreateUserInputDto;
import com.example.graphqlbff.dto.PageInfoDto;
import com.example.graphqlbff.dto.UserConnectionDto;
import com.example.graphqlbff.dto.UserDto;
import com.example.graphqlbff.dto.UserEdgeDto;
import com.example.graphqlbff.error.BadRequestException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final Map<String, UserDto> usersById = new ConcurrentHashMap<>();
  private final List<String> orderedIds = new CopyOnWriteArrayList<>();

  public UserService() {
    // Seed a couple of users for quick manual testing.
    create(new CreateUserInputDto("ada@example.com", "Ada Lovelace"));
    create(new CreateUserInputDto("grace@example.com", "Grace Hopper"));
  }

  public UserDto create(CreateUserInputDto input) {
    Objects.requireNonNull(input, "input");

    String email = normalize(input.email());
    String displayName = normalize(input.displayName());

    if (email == null || email.isBlank()) {
      throw new BadRequestException("email is required");
    }
    if (displayName == null || displayName.isBlank()) {
      throw new BadRequestException("displayName is required");
    }

    boolean emailExists = usersById.values().stream().anyMatch(u -> u.email().equalsIgnoreCase(email));
    if (emailExists) {
      throw new BadRequestException("email must be unique");
    }

    String id = UUID.randomUUID().toString();
    UserDto user = new UserDto(id, email, displayName);
    usersById.put(id, user);
    orderedIds.add(id);
    return user;
  }

  public Optional<UserDto> findById(String id) {
    if (id == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(usersById.get(id));
  }

  public List<UserDto> findByIds(List<String> ids) {
    if (ids == null || ids.isEmpty()) {
      return Collections.emptyList();
    }
    List<UserDto> result = new ArrayList<>(ids.size());
    for (String id : ids) {
      result.add(usersById.get(id)); // keep positional alignment for DataLoader
    }
    return result;
  }

  public UserConnectionDto list(int first, String afterCursor) {
    int safeFirst = Math.max(0, Math.min(first, 100));
    int startIndex = decodeCursorToIndex(afterCursor);

    int from = Math.min(startIndex, orderedIds.size());
    int to = Math.min(from + safeFirst, orderedIds.size());

    List<UserEdgeDto> edges = new ArrayList<>(Math.max(0, to - from));
    for (int i = from; i < to; i++) {
      String id = orderedIds.get(i);
      UserDto user = usersById.get(id);
      if (user == null) {
        continue;
      }
      String cursor = encodeIndexToCursor(i + 1); // cursor points to the next index
      edges.add(new UserEdgeDto(cursor, user));
    }

    String endCursor = edges.isEmpty() ? null : edges.get(edges.size() - 1).cursor();
    boolean hasNextPage = to < orderedIds.size();

    return new UserConnectionDto(edges, new PageInfoDto(endCursor, hasNextPage));
  }

  private static String normalize(String s) {
    return s == null ? null : s.trim();
  }

  private static String encodeIndexToCursor(int index) {
    String raw = "idx:" + index;
    return Base64.getUrlEncoder().withoutPadding().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
  }

  private static int decodeCursorToIndex(String cursor) {
    if (cursor == null || cursor.isBlank()) {
      return 0;
    }
    try {
      byte[] decoded = Base64.getUrlDecoder().decode(cursor);
      String raw = new String(decoded, StandardCharsets.UTF_8);
      if (!raw.startsWith("idx:")) {
        return 0;
      }
      int idx = Integer.parseInt(raw.substring("idx:".length()));
      return Math.max(0, idx);
    } catch (IllegalArgumentException | NumberFormatException ex) {
      return 0;
    }
  }
}
