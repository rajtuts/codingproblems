package com.example.day6;
import jakarta.validation.Valid; import org.springframework.data.domain.*; import org.springframework.http.HttpStatus; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException; import java.util.Map;
@RestController public class ApiController {
 private final UserRepository repo; private final JwtService jwt; public ApiController(UserRepository r,JwtService j){repo=r;jwt=j;}
 record Login(String username,String password){} record Token(String token,String username,String role){}
 @PostMapping("/api/auth/login") Token login(@RequestBody Login l){String role;if("admin".equals(l.username())&&"password".equals(l.password()))role="ADMIN";else if("user".equals(l.username())&&"password".equals(l.password()))role="USER";else throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid credentials");return new Token(jwt.create(l.username(),role),l.username(),role);}
 @GetMapping("/api/auth/me") Map<String,String> me(Authentication a){return Map.of("username",a.getName(),"role",a.getAuthorities().iterator().next().getAuthority().replace("ROLE_",""));}
 @GetMapping("/api/users") Page<AppUser> all(@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="6")int size,@RequestParam(defaultValue="name,asc")String sort){String[] p=sort.split(",");String f=p[0].equals("email")?"email":"name";Sort.Direction d=p.length>1&&p[1].equalsIgnoreCase("desc")?Sort.Direction.DESC:Sort.Direction.ASC;return repo.findAll(PageRequest.of(Math.max(0,page),Math.min(Math.max(1,size),50),Sort.by(d,f)));}
 @GetMapping("/api/users/{id}") AppUser one(@PathVariable Long id){return find(id);}
 @PostMapping("/api/users") @ResponseStatus(HttpStatus.CREATED) AppUser create(@Valid @RequestBody AppUser u){u.setId(null);return repo.save(u);}
 @PutMapping("/api/users/{id}") AppUser update(@PathVariable Long id,@Valid @RequestBody AppUser x){AppUser u=find(id);u.setName(x.getName());u.setEmail(x.getEmail());u.setRole(x.getRole());return repo.save(u);}
 @DeleteMapping("/api/users/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) void delete(@PathVariable Long id){repo.delete(find(id));}
 AppUser find(Long id){return repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));}
}
