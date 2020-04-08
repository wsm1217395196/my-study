package com.study.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.HashMap;
import java.util.Map;

/**
 * @classDesc: 500统一异常处理,
 * @author: wsm
 */
public class JsonExceptionHandler implements ErrorWebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(JsonExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpRequest request = exchange.getRequest();
        Map<String, Object> result = new HashMap<>();
        // 按照异常类型进行处理
        int code;
        String msg;
        if (ex instanceof MyRuntimeException) {
            MyRuntimeException myRuntimeException = (MyRuntimeException) ex;
            code = myRuntimeException.getResultView().getCode();
            msg = ex.getMessage();
        } else if (ex instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) ex;
            code = responseStatusException.getStatus().value();
            msg = responseStatusException.getMessage();
            log.error("[全局异常处理]异常请求路径:{},记录异常信息:{}", request.getPath(), ex.getMessage());
        } else {
            code = HttpStatus.INTERNAL_SERVER_ERROR.value();
            msg = "Internal Server Error";
            log.error("[全局异常处理]异常请求路径:{},记录异常信息:{}", request.getPath(), ex.getMessage());
        }
        //封装响应体,此body可修改为自己的jsonBody
        result.put("code", code);
        result.put("msg", msg);

        ServerHttpResponse response = exchange.getResponse();
        //设置headers
        HttpHeaders httpHeaders = response.getHeaders();
        httpHeaders.add("Content-Type", "application/json; charset=UTF-8");
        httpHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        //设置body
        byte[] dataBytes = {};
        try {
            result.put("code", code);
            result.put("msg", msg);
            ObjectMapper mapper = new ObjectMapper();
            dataBytes = mapper.writeValueAsBytes(result);
        } catch (Exception e) {
            log.error("[全局异常处理]异常请求路径:{},记录异常信息:{}", request.getPath(), e.getMessage());
            e.printStackTrace();
        }
        DataBuffer bodyDataBuffer = response.bufferFactory().wrap(dataBytes);
        return response.writeWith(Mono.just(bodyDataBuffer));
    }

}