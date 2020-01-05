//package com.example.demo.advice;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
///**
// * Web MVC 配置适配器
// * @ClassName: WebAppConfigurer 
// * @Description: 
// * @author OnlyMate
// * @Date 2018年8月28日 下午2:39:31  
// * 
// * WebAppConfigurer extends WebMvcConfigurerAdapter 在Spring Boot2.0版本已过时了，用官网说的新的类替换
// *
// */
//@Configuration
//public class WebAppConfigurer implements WebMvcConfigurer {
//
//    /**
//     * springmvc视图解析
//     * @Title: viewResolver 
//     * @Description: TODO
//     * @Date 2018年8月28日 下午4:46:07 
//     * @author OnlyMate
//     * @return
//     */
//    @Bean
//    public InternalResourceViewResolver viewResolver(){
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/views/");
//        viewResolver.setSuffix(".jsp");
//        // viewResolver.setViewClass(JstlView.class); // 这个属性通常并不需要手动配置，高版本的Spring会自动检测
//        return viewResolver;
//    }
//
//    /**
//     * SpringBoot设置首页
//     */
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        WebMvcConfigurer.super.addViewControllers(registry);
//        registry.addViewController("/").setViewName("index");
//        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
//    }
//    
//}
