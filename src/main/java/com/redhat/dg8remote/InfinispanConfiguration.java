package com.redhat.dg8remote;

import org.infinispan.spring.starter.remote.InfinispanRemoteCacheCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.redhat.dg8remote.controller.RemoteQueryInitializerImpl;

@Configuration
public class InfinispanConfiguration {

   @Bean
   @Order(Ordered.HIGHEST_PRECEDENCE)
   public InfinispanRemoteCacheCustomizer caches() {
      return b -> {
         // Add marshaller in the client, the class is generated from the interface in compile time
         b.addContextInitializer(new RemoteQueryInitializerImpl());
      };
   }
}
