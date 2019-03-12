package com.lzl.config;


import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.codahale.metrics.servlets.MetricsServlet;
import com.lzl.metric.MyMetric;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerClientInterceptor;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lizanle
 * @Date 2019/3/12 13:56
 */
@Configuration
public class MyInterceptor implements WebMvcConfigurer{
    private final static ThreadLocal<Timer.Context> tl = new ThreadLocal<Timer.Context>();
    @Bean
    public HandlerInterceptor interceptor(){

       return new HandlerInterceptor() {
           public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
               if(handler instanceof HandlerMethod){
                   HandlerMethod handlerMethod = (HandlerMethod) handler;
                   Class<?> beanType = handlerMethod.getBeanType();
                   Method method = handlerMethod.getMethod();
                   Timer time = MyMetric.registry.timer(MetricRegistry.name(beanType.getName(), method.getName()));
                   Timer.Context context = time.time();
                   tl.set(context);
                   return true;
               }else{
                   return false;
               }
           }

           public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

           }

           public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
               Timer.Context context = null;
               if((context = tl.get()) != null){
                   context.close();
               }
           }
       };
    }

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor()).addPathPatterns("/*");
    }

    @Bean
    public ServletRegistrationBean metricsServlet(){
        ServletRegistrationBean<MetricsServlet> registrationBean = new ServletRegistrationBean<MetricsServlet>(new MetricsServlet(MyMetric.registry), "/metrics");
        registrationBean.setLoadOnStartup(0);
        return registrationBean;
    }
}
