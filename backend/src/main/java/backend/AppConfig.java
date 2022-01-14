package backend;

import com.mitchellbosecke.pebble.PebbleEngine;
import com.mitchellbosecke.pebble.loader.ServletLoader;
import com.mitchellbosecke.pebble.spring.extension.SpringExtension;
import com.mitchellbosecke.pebble.spring.servlet.PebbleViewResolver;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import javax.servlet.ServletContext;
import javax.sql.DataSource;
import java.io.File;
import java.util.Properties;

@Configuration
@ComponentScan
@EnableWebMvc
@EnableSwagger2
@EnableTransactionManagement
@PropertySource("classpath:/jdbc.properties")
public class AppConfig {
    // start tomcat
    public static void main(String[] args) throws Exception{
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Integer.getInteger("port",8080));
        tomcat.getConnector();
        //create web application
        Context ctx = tomcat.addWebapp("",new File("src/main/webapp").getAbsolutePath());
        WebResourceRoot resourceRoot = new StandardRoot(ctx);
        resourceRoot.addPreResources(new DirResourceSet(resourceRoot,"/WEB-INF/classes",
                                                        new File("target/classes").getAbsolutePath(),"/"));
        ctx.setResources(resourceRoot);
        tomcat.start();
        tomcat.getServer().await();
    }

    // mvc configuration, static files, register interceptors
    @Bean
    WebMvcConfigurer createMvcConfiguer(@Autowired HandlerInterceptor[] interceptors){
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").
                        addResourceLocations("/static/");
                registry.addResourceHandler("swagger-ui.html").
                        addResourceLocations("classpath:/META-INF/resources/");
                registry.addResourceHandler("/webjars/**").
                        addResourceLocations("classpath:/META-INF/resources/webjars/");
                registry.addResourceHandler("doc.html").
                        addResourceLocations("classpath:/META-INF/resources/");
            }
            @Override
            public void addInterceptors(InterceptorRegistry registry){
                for(var interceptor:interceptors)
                    registry.addInterceptor(interceptor);
            }
        };
    }

    //Config swagger-ui
    @Bean
    public Docket createRestAPI(){
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("backend.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("Swagger Interface Test")
                        .description("Swagger Interface Test")
                        .version("9.0")
                        .contact(new Contact("Myblog","***","***"))
                        .license("The Apache License")
                        .licenseUrl("***")
                        .build());
    }

    //pebble view configuration
    @Bean
    ViewResolver createViewResolver(@Autowired ServletContext servletContext){
        PebbleEngine engine = new PebbleEngine.Builder().autoEscaping(true)
                .cacheActive(false)
                .loader(new ServletLoader(servletContext))
                .extension(new SpringExtension())
                .build();
        PebbleViewResolver viewResolver = new PebbleViewResolver();
        viewResolver.setPrefix("/WEB-INF/templates/");
        viewResolver.setSuffix("");
        viewResolver.setPebbleEngine(engine);
        return viewResolver;
    }

    //jdbc configuration
    @Value("${jdbc.url}")
    String jdbcUrl;
    @Value("${jdbc.username}")
    String jdbcUsername;
    @Value("${jdbc.password}")
    String jdbcPassword;
    @Value("${jdbc.driver}")
    String jdbcDriver;

    @Bean
    DataSource createDataSource(){
        // jdbc connection pool
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(jdbcUsername);
        config.setPassword(jdbcPassword);
        config.setDriverClassName(jdbcDriver);
        config.addDataSourceProperty("autoCommit","false");
        config.addDataSourceProperty("connectionTimeout","5");
        config.addDataSourceProperty("idleTimeout","60");
        return new HikariDataSource(config);
    }
    //jdbc template
    @Bean
    JdbcTemplate createJdbcTemplate(@Autowired DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    // local session factory
    @Bean
    LocalSessionFactoryBean createSessionFactory(@Autowired DataSource dataSource){
        var props = new Properties();
        props.setProperty("hibernate.hbm2ddl.auto", "update"); // 生产环境不要使用
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        props.setProperty("hibernate.show_sql", "true");
        var sessionFactoryBean = new LocalSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        sessionFactoryBean.setPackagesToScan("backend.entity");
        sessionFactoryBean.setHibernateProperties(props);
        return sessionFactoryBean;
    }

    //hibernate template
    @Bean
    HibernateTemplate createHibernateTemplate(@Autowired SessionFactory sessionFactory) {
        return new HibernateTemplate(sessionFactory);
    }
    //db transaction
//    @Bean
//    PlatformTransactionManager createTxManager(@Autowired DataSource dataSource){
//        return new JdbcTransactionManager(dataSource);
//    }

    @Bean
    PlatformTransactionManager createTxManager(@Autowired SessionFactory sessionFactory){
        return new HibernateTransactionManager(sessionFactory);
    }



}
