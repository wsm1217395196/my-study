## 本项目为：spring cloud、spring security、oauth2、jwt整合项目,持久层用的mybatis-plus。

请用idea打开parent父文件，他会自动加载其他字模块（服务）。

运行此项目请到DB文件夹导入sql文件到你的本地当中。

DB文件夹有postman测试接口示例文件，导入postman即可。



### wsm-discovery服务是eureka注册中心



### wsm-gateway网关服务

1、用的spring-cloud-starter-gateway依赖，有gateway网关的一些配置方式，hystrix 熔断器。

2、没有集成spring secutity、oauth2 等，所以请用**wsm-zull服务**。



### wsm-zull网关服务

1、有zull网关的配置方式，熔断器配置。

2、集成了spring secutity、oauth2 等。



### wsm-oauth服务（oauth2认证中心，集成了jwt)

1、spirng security, oauth2认证中心服务，集成了jwt。

2、可以用oauth2方式生成token，也可以用jwt（对称加密，非对称加密都有）的方式生成token，jwt可以自定义token生成携带的信息，根据代码里的注释用哪种即可。

3、token的存储方式可以是：内存、数据库、redis、jwt等。根据代码里的注释用哪种即可。

4、有自定义token异常信息,用于token校验失败返回信息。有资源授权异常处理。

5、根据/oauth/token接口获取的token，携带该token可以请求各服务有security权限的接口。



### wsm-upms服务（通用用户权限系统、资源服务）

1、配置了资源服务，根据项目code从数据库读取出来的角色，资源url信息（即接口地址）给接口使用security权限。

2、有注册，登录，退出，各控制器等接口。。。

3、mybatis-plus代码生成器。



### wsm-work服务（资源服务）

1、配置了资源服务，根据项目code从**wsm-upms服务**调用feign读取出来的角色，资源url信息（即接口地址）给接口使用security权限。

2、运行此服务请先运行**wsm-upms服务**。



### wsm-demo服务

1、有整合spring websocket（消息推送）的示例。

2、hystrix 熔断器，hystrix dashboard 熔断器仪表盘页面。



### wsm-config-server服务（config-server）

1、高可用配置中心，从远程git读取配置文件供其他服务使用。

2、Spring Cloud Bus刷新配置（使用的Rabbitmq），git上的配置有变化不用重启项目就能读取供其他服务使用，**wsm-public服务**(config-client)有读取值的示例。



### wsm-public服务

1、有spring-boot-starter-mail发送邮件示例。

2、阿里短信服务发送示例（没有开通阿里短信服务，暂未测试过。）

3、结合**wsm-config-server服务**读取git配置中的值示例。



