package com.example.resilience.api;

import com.example.resilience.service.DownstreamFacade;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
class DemoController {
  private final DownstreamFacade facade;

  DemoController(DownstreamFacade facade) {
    this.facade = facade;
  }

  @GetMapping("/sync")
  ResponseEntity<String> sync(@RequestParam(defaultValue = "ok") String mode) {
    return ResponseEntity.ok(facade.callSync(mode));
  }

  @GetMapping("/async")
  CompletableFuture<ResponseEntity<String>> async(@RequestParam(defaultValue = "ok") String mode) {
    return facade.callAsync(mode).thenApply(ResponseEntity::ok);
  }
}
