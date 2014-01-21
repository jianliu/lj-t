package support;

import java.io.IOException;

import loader.BoxLoader;
import loader.ILoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class XMLManager {

    private static final String location = "classpath:config/site*.xml";
    private static final String preLocation = "/config/";

    public void readFile() throws IOException {
        ILoader loader=new BoxLoader();
        ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
        Resource[] source = resourceLoader.getResources(location);
        for (int i = 0; i < source.length; i++) {
            Resource resource = source[i];
            String filename =preLocation+ resource.getFilename();
            Object map =loader.loadXml(filename);
            System.out.println(resource.getURL());

        }
    }

    public static void main(String[] args) {

        XMLManager m = new XMLManager();
        try {
            m.readFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}