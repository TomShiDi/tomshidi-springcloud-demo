package com.tomshidi.jpa.configuration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

/**
 * @author tomshidi
 * @since 2024/4/25 9:50
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Api接口文档标题",
                description = "项目描述",
                version = "1.0.0",
                termsOfService = "https://github.com/TomShiDi",
                contact = @Contact(
                        name = "tomshidi",                            // 作者名称
                        email = "z",                  // 作者邮箱
                        url = "https://github.com/TomShiDi"  // 介绍作者的URL地址
                ),
                license = @License(name = "Apache 2.0",
                        url = "http://www.apache.org/licenses",
                        extensions = @Extension(name = "test", properties = @ExtensionProperty(name = "test", value = "1111")))
        ),
//        security = @SecurityRequirement(name = "JWT认证"), //全部加上认证
        servers = {
//                @Server(url = "http://localhost:8081", description = "服务1"),
//                @Server(url = "http://localhost:8080", description = "服务2")
        },
        externalDocs = @ExternalDocumentation(description = "对外说明", url = "https://github.com/TomShiDi")
)
//@SecurityScheme(
//        name = "JWT认证",                   // 认证方案名称
//        type = SecuritySchemeType.HTTP,      // 认证类型，当前为http认证
//        description = "这是一个认证的描述详细",  // 描述信息
//        in = SecuritySchemeIn.HEADER,        // 代表在http请求头部
//        scheme = "bearer",                   // 认证方案，如：Authorization: bearer token信息
//        bearerFormat = "JWT")
public class SpringDocConfiguration {
    //    @Bean
//    public GroupedOpenApi productApi() {
//        return GroupedOpenApi.builder()
//                .group("product-service")
//                .pathsToMatch("/product/**")
//                .build();
//    }
//
//    @Bean
//    public GroupedOpenApi orderApi() {
//        return GroupedOpenApi.builder()
//                .group("order-service")
//                .pathsToMatch("/order/**")
//                .build();
//    }

//    @Bean
//    public OpenAPI openAPI(){
//        // 联系人信息(contact)，构建API的联系人信息，用于描述API开发者的联系信息，包括名称、URL、邮箱等
//        Contact contact = new Contact()
//                .name("tomshidi")  // 作者名称
//                .email("1341109792@qq.com") // 作者邮箱
//                .url("https://github.com/TomShiDi") // 介绍作者的URL地址
//                .extensions(new HashMap<String,Object>());// 使用Map配置信息（如key为"name","email","url"）
//
//        License license = new License()
//                .name("Apache 2.0")                         // 授权名称
//                .url("https://www.apache.org/licenses/LICENSE-2.0.html")    // 授权信息
//                .identifier("Apache-2.0")                   // 标识授权许可
//                .extensions(new HashMap<String, Object>());// 使用Map配置信息（如key为"name","url","identifier"）
//
//        //创建Api帮助文档的描述信息、联系人信息(contact)、授权许可信息(license)
//        Info info = new Info()
//                .title("Api接口文档标题")      // Api接口文档标题（必填）
//                .description("项目描述")     // Api接口文档描述
//                .version("1.0.0")                                  // Api接口版本
//                .termsOfService("https://github.com/TomShiDi")    // Api接口的服务条款地址
//                .license(license)  //   授权名称
//                .contact(contact); // 设置联系人信息
//
//        List<Server> servers = new ArrayList<>(); //多服务
//        // 表示服务器地址或者URL模板列表，多个服务地址随时切换（只不过是有多台IP有当前的服务API）
//        servers.add(new Server().url("http://localhost:8080").description("服务1"));
//        servers.add(new Server().url("http://localhost:8081").description("服务2"));
//
//        // // 设置 spring security apikey accessToken 认证的请求头 X-Token: xxx.xxx.xxx
//        SecurityScheme securityScheme = new SecurityScheme()
//                .name("x-token")
//                .type(SecurityScheme.Type.APIKEY)
//                .description("APIKEY认证描述")
//                .in(SecurityScheme.In.HEADER);
//
//        // 设置 spring security jwt accessToken 认证的请求头 Authorization: Bearer xxx.xxx.xxx
//        SecurityScheme securityScheme1 = new SecurityScheme()
//                .name("JWT认证")
//                .scheme("bearer") //如果是Http类型，Scheme是必填
//                .type(SecurityScheme.Type.HTTP)
//                .description("认证描述")
//                .in(SecurityScheme.In.HEADER)
//                .bearerFormat("JWT");
//
//        List<SecurityRequirement> securityRequirements = new ArrayList<>();
//
//        SecurityRequirement securityRequirement = new SecurityRequirement();
//        securityRequirement.addList("authScheme");
//
//        securityRequirements.add(securityRequirement);
//
//        // 返回信息
//        return new OpenAPI()
//                //.openapi("3.0.1")  // Open API 3.0.1(默认)
//                .info(info)
//                .servers(servers)
////                .components(new Components().addSecuritySchemes("authScheme",securityScheme1)) //添加鉴权组件
//                .security(securityRequirements) //全部添加鉴权小锁
//                .externalDocs(new ExternalDocumentation()
//                        .description("对外说明") //对外说明
//                        .url("https://github.com/TomShiDi"));     // 配置Swagger3.0描述信息
//    }
}
