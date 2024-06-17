package imdb.config;

import java.util.ResourceBundle;

public class Configuration {

    private ResourceBundle bundle;

    public Configuration() {
        bundle = ResourceBundle.getBundle("Configuration"); 
    }

    public String get(String key) {
        return bundle.getString(key);
    }
}
