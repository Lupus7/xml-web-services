package team10.apigateway.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import team10.apigateway.dto.UserAuthInfo;

import java.util.List;

@Component
public class GlobalPreFilter implements GlobalFilter {

    @Autowired
    DiscoveryClient discoveryClient;

    @Override
    public Mono<Void> filter(
            ServerWebExchange exchange,
            GatewayFilterChain chain) {

        ServerHttpRequest mutatedRequest;

        try {
            String header = exchange.getRequest().getHeaders().get(JwtProperties.HEADER).get(0);

            if (header.contains("MASTER")) {
                List<String> services = discoveryClient.getServices();
                for (String service : services) {
                    if (exchange.getRequest().getRemoteAddress().getAddress().getHostAddress().equals(discoveryClient.getInstances(service).get(0).getHost())) {
                        return chain.filter(exchange);
                    }
                }
            }

            String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(header.replace(JwtProperties.TOKEN_PREFIX, ""))
                    .getSubject();

            String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();

            HttpHeaders headers = new HttpHeaders();
            headers.add(JwtProperties.HEADER, "NONE;MASTER");

            HttpEntity<String> entity = new HttpEntity<>("body", headers);

            ResponseEntity<UserAuthInfo> userAuthInfo = new RestTemplate().exchange("http://" + userServiceIp + ":8080/util/auth/" + username, HttpMethod.GET, entity, UserAuthInfo.class, new Object());

            mutatedRequest = exchange.getRequest().mutate().header(JwtProperties.HEADER, username + ";" + userAuthInfo.getBody().getAuthorities()).build();
        } catch (Exception e) {
            mutatedRequest = exchange.getRequest().mutate().header(JwtProperties.HEADER, "NONE;NONE").build();
        }

        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
        return chain.filter(mutatedExchange);
    }
}
