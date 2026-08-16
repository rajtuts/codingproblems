package com.example.graphqlservice.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
class HelloController {

  @QueryMapping
  String hello(@Argument String name) {
    if (name == null || name.isBlank()) {
      return "Hello";
    }
    return "Hello, " + name;
  }
}
