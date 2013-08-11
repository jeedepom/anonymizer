package fr.jeedepom.anonymizer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Someone
 */
public class AnonymizerConfig {

    static Properties properties;

    public AnonymizerConfig(String pathname) throws IOException {

        if(StringUtils.isEmpty(pathname)){
            pathname="./";
        }
        Path path = FileSystems.getDefault().getPath(pathname, "anonymizer.properties");
        properties=new Properties();
        properties.load( Files.newInputStream(path, StandardOpenOption.READ));
        
     {
            
        }
    }

    public static Map<String, String> getConfig(boolean invert) {
        Map<String, String> result = new HashMap<String, String>();

        for (Object keyObject : properties.keySet()) {
            String key = (String) keyObject;
            result.put(key, properties.getProperty(key));
        }

        if (invert) {
            return reverse(result);
        }
        return result;
    }

    public static <K, V> HashMap<V, K> reverse(Map<K, V> map) {
        HashMap<V, K> rev = new HashMap<V, K>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            rev.put(entry.getValue(), entry.getKey());
        }
        return rev;
    }
}