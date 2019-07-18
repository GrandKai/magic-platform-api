package com.magic.platform;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: zyn
 * @Description: 富文本上传图片，可以访问本机磁盘目录
 * @Date: Created in 2018-11-15 10:10
 * @Modified By:
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/images/**").addResourceLocations("file:///f:/upload/images/");
  }
}
