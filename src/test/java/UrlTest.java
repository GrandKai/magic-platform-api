import com.google.common.base.Joiner;
import com.google.common.collect.Maps;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author: Administrator
 * @Description:
 * @Date: Created in 2019-11-25 13:13
 * @Modified By:
 */
public class UrlTest {

    public static void main(String[] args) throws UnsupportedEncodingException {

        System.out.println(URLEncoder.encode("张", "UTF-8"));

        HashMap<String, Object> map = Maps.newHashMap();

        map.put("a", URLEncoder.encode("a", "UTF-8"));
        map.put("b", URLEncoder.encode("b", "UTF-8"));

        String s = Joiner.on("&").withKeyValueSeparator("=").join(map);

        System.out.println(s);
    }
}
