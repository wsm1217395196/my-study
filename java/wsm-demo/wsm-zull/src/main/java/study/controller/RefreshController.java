package study.controller;

import com.study.result.ResultView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.service.RefreshRouteService;

import java.util.Map;
import java.util.Set;

@RestController
public class RefreshController {

    @Autowired
    RefreshRouteService refreshRouteService;
    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;


    /**
     * @author:XingWL
     * @description:刷新映射
     * @date: 2019/4/27 18:30
     */
    @GetMapping("/refreshRoute")
    public ResultView refresh() {
        refreshRouteService.refreshRoute();
        return ResultView.success();
    }

    /**
     * @author:XingWL
     * @description:查看映射关系
     * @date: 2019/4/27 18:30
     */
    @RequestMapping("/watchRoute")
    public ResultView watchNowRoute() {
        //可以用debug模式看里面具体是什么
        Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
        return ResultView.success(handlerMap.keySet());
    }

}
