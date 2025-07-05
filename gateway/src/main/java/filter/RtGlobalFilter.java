package filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class RtGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("请求路径: {},时间{}", exchange.getRequest().getPath(), System.currentTimeMillis());
        //放行
        return chain.filter(exchange)//放行
                .doFinally((result) -> {
                    long end = System.currentTimeMillis();
                    log.info("响应状态码: {},结束时间: {}", exchange.getResponse().getStatusCode(),end);
                });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
