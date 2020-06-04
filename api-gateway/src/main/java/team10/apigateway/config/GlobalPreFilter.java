package team10.apigateway.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import team10.apigateway.model.dto.UserAuthInfo;

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
            String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(header.replace(JwtProperties.TOKEN_PREFIX, ""))
                    .getSubject();

            String userServiceIp = discoveryClient.getInstances("user").get(0).getHost();
            UserAuthInfo userAuthInfo = new RestTemplate().getForObject("http://" + userServiceIp + ":8080/util/auth/" + username, UserAuthInfo.class);

            mutatedRequest = exchange.getRequest().mutate().header(JwtProperties.HEADER_INTERNAL, username + ";" + userAuthInfo.getAuthorities()).build();
        } catch (Exception e) {
            mutatedRequest = exchange.getRequest().mutate().header(JwtProperties.HEADER_INTERNAL, "NONE;NONE").build();
        }

        ServerWebExchange mutatedExchange = exchange.mutate().request(mutatedRequest).build();
        return chain.filter(mutatedExchange);
    }
}
