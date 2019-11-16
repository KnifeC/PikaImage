package moe.keshane.PikaImage.Config;

import moe.keshane.PikaImage.Util.FileUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String jarPath = FileUtils.getJarPath();
        registry.addResourceHandler("/image/**").addResourceLocations("file:"+jarPath);
    }
}
