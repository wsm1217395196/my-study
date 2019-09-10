## 本项目为：spring cloud、zull/gateway、oauth2、jwt整合项目,持久层用的mybatis-plus。

请用idea打开parent父文件，他会自动加载其他子模块（服务）。

运行此项目请到DB文件夹导入sql文件到你的本地当中。

DB文件夹有postman测试接口示例文件，导入postman即可。

***如果解决了你的问题麻烦点个星星哟***😘😘😘

v2文件夹中时不时会更新一些示例。



### wsm-discovery是eureka注册中心



### wsm-gateway（网关）

1、用的spring-cloud-starter-gateway依赖，有gateway网关的一些配置方式，hystrix 熔断器。

2、**wsm-gateway**、**wsm-zull**随便用哪种都可以。



### wsm-zull（网关）

1、有zull网关的配置方式，熔断器配置。

2、**wsm-gateway**、**wsm-zull**随便用哪种都可以。



### wsm-oauth（oauth2认证中心，集成了jwt)

1、spirng security, oauth2认证中心服务，集成了jwt。

2、可以用oauth2方式生成token，也可以用jwt（对称加密，非对称加密都有）的方式生成token，jwt可以自定义token生成携带的信息，根据代码里的注释用哪种即可。

3、token的存储方式可以是：内存、数据库、redis、jwt等。根据代码里的注释用哪种即可。

4、有自定义token异常信息,用于token校验失败返回信息。有资源授权异常处理。

5、根据/oauth/token接口获取的token，携带该token可以请求各服务有security权限的接口。



### wsm-upms（通用用户权限系统、资源服务）

1、配置了资源服务，根据项目code从数据库读取出来的角色，资源url信息（即接口地址）给接口使用security权限。

2、有注册，登录，退出，各控制器等接口。。。

3、mybatis-plus代码生成器。



### wsm-work（资源服务）

1、配置了资源服务，根据项目code从**wsm-upms服务**调用feign读取出来的角色，资源url信息（即接口地址）给接口使用security权限。

2、运行此服务请先运行**wsm-upms服务**。


