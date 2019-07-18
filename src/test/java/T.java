import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.Set;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-06-26 09:09
 * @Modified By:
 */
public class T {

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    LoadPackageClasses loadPackageClasses = new LoadPackageClasses(new String[]{"com.magic"},
        ApiOperation.class);

    Set<Class<?>> classSet = loadPackageClasses.getClassSet();

    System.out.println(classSet);

  }
}
