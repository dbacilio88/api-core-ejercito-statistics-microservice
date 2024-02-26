package pe.mil.ejercito.microservice.components.interceptor;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.server.PathContainer;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * LoggerInterceptor
 * <p>
 * LoggerInterceptor class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 20/02/2024
 */

@Log4j2
@Component
public class LoggerInterceptor implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        log.info("filter {}", exchange);
        log.info("chain {}", chain.filter(exchange));
        PathContainer pathContainer = PathContainer.parsePath(exchange.getRequest().getPath().pathWithinApplication().value());

        log.info("pathContainer {}", pathContainer.value());

        return chain.filter(
                exchange.mutate()
                        .request(exchange.getRequest())
                        .response(exchange.getResponse())
                        .build()).contextWrite(context -> context.put("request", exchange.getRequest()));
    }
}