package com.example.clientconfig.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.core5.util.Timeout;
import org.slf4j.MDC;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableConfigurationProperties(ClientProps.class)
class HttpClientConfig {
  static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
  static final String CORRELATION_ID_MDC_KEY = "correlationId";

  @Bean
  RestTemplate restTemplate(ClientProps props) {
    ClientProps.RestTemplateProps p = props.getRestTemplate();

    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout(toTimeout(p.getConnectTimeout()))
        .setResponseTimeout(toTimeout(p.getReadTimeout()))
        .setConnectionRequestTimeout(toTimeout(p.getConnectionRequestTimeout()))
        .build();

    PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
        .setMaxConnTotal(100)
        .setMaxConnPerRoute(20)
        .build();

    CloseableHttpClient httpClient = HttpClients.custom()
        .setConnectionManager(connectionManager)
        .setDefaultRequestConfig(requestConfig)
        .evictExpiredConnections()
        .build();

    HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
    factory.setConnectTimeout(p.getConnectTimeout());
    factory.setReadTimeout(p.getReadTimeout());

    RestTemplate restTemplate = new RestTemplate(factory);
    restTemplate.getInterceptors().add(correlationIdInterceptor());
    return restTemplate;
  }

  @Bean
  WebClient webClient(ClientProps props) {
    ClientProps.WebClientProps p = props.getWebClient();

    HttpClient httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, p.getConnectTimeoutMillis())
        .responseTimeout(p.getResponseTimeout())
        .doOnConnected(conn -> {
          conn.addHandlerLast(new ReadTimeoutHandler(p.getReadTimeout().toMillis(), TimeUnit.MILLISECONDS));
          conn.addHandlerLast(new WriteTimeoutHandler(p.getWriteTimeout().toMillis(), TimeUnit.MILLISECONDS));
        });

    ReactorClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

    return WebClient.builder()
        .baseUrl(props.getBaseUrl())
        .clientConnector(connector)
        .defaultHeader(HttpHeaders.ACCEPT, "application/json")
        .filter(addCorrelationIdFilter())
        .build();
  }

  private ClientHttpRequestInterceptor correlationIdInterceptor() {
    return (request, body, execution) -> {
      String cid = correlationId();
      request.getHeaders().set(CORRELATION_ID_HEADER, cid);
      return execution.execute(request, body);
    };
  }

  private ExchangeFilterFunction addCorrelationIdFilter() {
    return (request, next) -> {
      String cid = correlationId();
      ClientRequest mutated = ClientRequest.from(request)
          .header(CORRELATION_ID_HEADER, cid)
          .build();
      return next.exchange(mutated);
    };
  }

  private String correlationId() {
    return Optional.ofNullable(MDC.get(CORRELATION_ID_MDC_KEY)).filter(s -> !s.isBlank()).orElseGet(() -> {
      String generated = UUID.randomUUID().toString();
      MDC.put(CORRELATION_ID_MDC_KEY, generated);
      return generated;
    });
  }

  private static Timeout toTimeout(Duration d) {
    return Timeout.ofMilliseconds(d.toMillis());
  }
}

@ConfigurationProperties(prefix = "client")
class ClientProps {
  private String baseUrl;
  private RestTemplateProps restTemplate = new RestTemplateProps();
  private WebClientProps webClient = new WebClientProps();

  String getBaseUrl() {
    return baseUrl;
  }

  void setBaseUrl(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  RestTemplateProps getRestTemplate() {
    return restTemplate;
  }

  void setRestTemplate(RestTemplateProps restTemplate) {
    this.restTemplate = restTemplate;
  }

  WebClientProps getWebClient() {
    return webClient;
  }

  void setWebClient(WebClientProps webClient) {
    this.webClient = webClient;
  }

  static class RestTemplateProps {
    private Duration connectTimeout = Duration.ofMillis(500);
    private Duration readTimeout = Duration.ofMillis(1500);
    private Duration connectionRequestTimeout = Duration.ofMillis(200);

    Duration getConnectTimeout() {
      return connectTimeout;
    }

    void setConnectTimeout(Duration connectTimeout) {
      this.connectTimeout = connectTimeout;
    }

    Duration getReadTimeout() {
      return readTimeout;
    }

    void setReadTimeout(Duration readTimeout) {
      this.readTimeout = readTimeout;
    }

    Duration getConnectionRequestTimeout() {
      return connectionRequestTimeout;
    }

    void setConnectionRequestTimeout(Duration connectionRequestTimeout) {
      this.connectionRequestTimeout = connectionRequestTimeout;
    }
  }

  static class WebClientProps {
    private int connectTimeoutMillis = 500;
    private Duration responseTimeout = Duration.ofMillis(1500);
    private Duration readTimeout = Duration.ofMillis(1500);
    private Duration writeTimeout = Duration.ofMillis(1500);

    int getConnectTimeoutMillis() {
      return connectTimeoutMillis;
    }

    void setConnectTimeoutMillis(int connectTimeoutMillis) {
      this.connectTimeoutMillis = connectTimeoutMillis;
    }

    Duration getResponseTimeout() {
      return responseTimeout;
    }

    void setResponseTimeout(Duration responseTimeout) {
      this.responseTimeout = responseTimeout;
    }

    Duration getReadTimeout() {
      return readTimeout;
    }

    void setReadTimeout(Duration readTimeout) {
      this.readTimeout = readTimeout;
    }

    Duration getWriteTimeout() {
      return writeTimeout;
    }

    void setWriteTimeout(Duration writeTimeout) {
      this.writeTimeout = writeTimeout;
    }
  }
}
