package com.example.graphqlbff.error;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import java.util.Map;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class GraphQlExceptionHandler extends DataFetcherExceptionResolverAdapter {

  @Override
  protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
    if (ex instanceof BadRequestException) {
      return GraphqlErrorBuilder.newError(env)
          .message(ex.getMessage())
          .errorType(ErrorType.BAD_REQUEST)
          .extensions(Map.of("code", "BAD_REQUEST"))
          .build();
    }

    if (ex instanceof NotFoundException) {
      return GraphqlErrorBuilder.newError(env)
          .message(ex.getMessage())
          .errorType(ErrorType.NOT_FOUND)
          .extensions(Map.of("code", "NOT_FOUND"))
          .build();
    }

    return GraphqlErrorBuilder.newError(env)
        .message("Internal error")
        .errorType(ErrorType.INTERNAL_ERROR)
        .extensions(Map.of("code", "INTERNAL"))
        .build();
  }
}
