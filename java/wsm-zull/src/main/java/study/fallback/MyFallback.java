package study.fallback;

import com.study.result.ResultEnum;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 网关熔断配置
 */
@Component
public class MyFallback implements FallbackProvider {

    Logger logger = LoggerFactory.getLogger(MyFallback.class);

    @Override
    public String getRoute() {
//        return "wsm-upms";//实现对wsm-upms服务的熔断
        return "*";  //实现对所有的路由服务的熔断
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                ResultEnum resultEnum = ResultEnum.CODE_504;
                String data = "";
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code", resultEnum.getCode());
                jsonObject.put("msg", resultEnum.getMsg());
                jsonObject.put("data", data);
                data = jsonObject.toString();
                logger.error(data);
                return new ByteArrayInputStream(data.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
