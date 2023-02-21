package com.example.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.RequestDataContext;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class SpringLoadBalancerApplication {

	@Autowired
	private WebClient.Builder loadBalancedBuilder;

	@PostConstruct
	private void init() throws InterruptedException {
		WebClient loadBalancedClient = loadBalancedBuilder.build();
		List<String> countries = Arrays.asList("IND", "UK", "US");
		Random random = new Random();
		for (int i = 1; i <= 10; i++) {
			String country = countries.get(random.nextInt(3));
			System.out.println(country);
			String response = loadBalancedClient.get().uri("http://example-service/hello").header("country", country)
					.retrieve().toEntity(String.class).block().getBody();
			System.out.println(response);
			Thread.sleep(1000);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringLoadBalancerApplication.class, args);
	}

}

@Configuration
@LoadBalancerClient(name = "example-service", configuration = CountryBasedLoadBalancerConfig.class)
class LoadBalancerConfig {
	@LoadBalanced
	@Bean
	WebClient.Builder webClientBuilder() {
		return WebClient.builder();
	}
}

//@Configuration
class CountryBasedLoadBalancerConfig{
	@Bean
	public ReactorServiceInstanceLoadBalancer reactorServiceInstanceLoadBalancer() {
		ReactorServiceInstanceLoadBalancer loadBalancer = new ReactorServiceInstanceLoadBalancer() {

			@Override
			public Mono<Response<ServiceInstance>> choose(Request request) {
				RequestDataContext context = (RequestDataContext) request.getContext();
				HttpHeaders headers = context.getClientRequest().getHeaders();
				System.out.println(headers);
				DefaultServiceInstance instance;
				if (headers.get("country").contains("IND")) {
					instance = new DefaultServiceInstance("ind-instance", "example-service", "localhost", 8080, false);
				} else if (headers.get("country").contains("UK")) {
					instance = new DefaultServiceInstance("uk-instance", "example-service", "localhost", 8081, false);
				} else {
					instance = new DefaultServiceInstance("us-instance", "example-service", "localhost", 8082, false);
				}
				return Mono.fromSupplier(new Supplier<Response<ServiceInstance>>() {

					@Override
					public Response<ServiceInstance> get() {
						DefaultResponse response = new DefaultResponse(instance);
						return response;
					}

				});
			}
		};
		return loadBalancer;
	}
}

//@Configuration
class RoundRobinLoadBalancerConfig {
	@Bean
	public ServiceInstanceListSupplier serviceInstanceListSupplier() {
		return new DemoServiceInstanceListSupplier("example-service");
	}
}

class DemoServiceInstanceListSupplier implements ServiceInstanceListSupplier {
	private final String serviceId;

	public DemoServiceInstanceListSupplier(String serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public String getServiceId() {
		return serviceId;
	}

	@Override
	public Flux<List<ServiceInstance>> get() {
		return Flux.just(Arrays.asList(
				new DefaultServiceInstance(serviceId + "1", serviceId, "localhost", 8080, false),
				new DefaultServiceInstance(serviceId + "2", serviceId, "localhost", 8081, false),
				new DefaultServiceInstance(serviceId + "3", serviceId, "localhost", 8082, false)
				));
	}
}