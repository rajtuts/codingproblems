package com.example.day6;
import jakarta.persistence.*; import jakarta.validation.constraints.*;
@Entity @Table(name="app_users") public class AppUser {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @NotBlank private String name; @Email @NotBlank @Column(unique=true) private String email; @NotBlank private String role;
 public AppUser(){} public AppUser(String n,String e,String r){name=n;email=e;role=r;}
 public Long getId(){return id;} public String getName(){return name;} public String getEmail(){return email;} public String getRole(){return role;}
 public void setId(Long v){id=v;} public void setName(String v){name=v;} public void setEmail(String v){email=v;} public void setRole(String v){role=v;}
}
