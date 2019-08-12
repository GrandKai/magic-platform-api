import java.io.IOException;

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


//    AntPathMatcher antPathMatcher = new AntPathMatcher();
//    boolean flag = antPathMatcher.match("/auth/refresh/token", "/auth/refresh/token/");
//
//    System.out.println(flag);

    short s = 1;

    s = (short) (s + 1);

//    t1();
//    t2();
    t3();
//    t4();

  }


  static void t1() {
    String str2 = new String("str01");
//    str2.intern();
    String str1 = "str01";
    System.out.println(str2==str1);
  }



  static void t2() {
    String str2 = new String("str01");
    String str3 = str2.intern();
    String str1 = "str01";
    System.out.println(str2==str1);
    System.out.println(str3==str1);
  }

  static void t3() {
    String str2 = new String("str")+new String("01");
    String str3 = str2.intern();
    String str1 = "str01";
//    str2.intern();
    System.out.println(str2==str3);
    System.out.println(str2==str1);
    System.out.println(str3==str1);
  }


  static void t4() {
    String str2 = new String("str") + new String("01");
    String str3 = str2.intern();
    String str1 = "str01";
    System.out.println(str2==str1);
    System.out.println(str3==str1);
  }

}
