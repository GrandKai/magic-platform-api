import java.io.IOException;
import org.springframework.util.AntPathMatcher;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-06-26 09:09
 * @Modified By:
 */
public class T {

  public static void main(String[] args) throws IOException, ClassNotFoundException {
//    LoadPackageClasses loadPackageClasses = new LoadPackageClasses(new String[]{"com.magic"},
//        ApiOperation.class);
//
//    Set<Class<?>> classSet = loadPackageClasses.getClassSet();
//
//    System.out.println(classSet);

//    ServletContext servletContext = new SpringBootMockServletContext("/auth/refresh/token");
//    HttpServletRequest request = new MockHttpServletRequest(servletContext,"POST", "/auth/refresh/token");
//
//    AntPathRequestMatcher matcher = new AntPathRequestMatcher("/auth/**");
//    boolean flag = matcher.matches(request);


    AntPathMatcher antPathMatcher = new AntPathMatcher();
    boolean flag = antPathMatcher.match("/auth/refresh/token", "/auth/refresh/token/");

    System.out.println(flag);

  }
}
