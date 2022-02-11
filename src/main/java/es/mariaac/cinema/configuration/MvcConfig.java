package es.mariaac.cinema.configuration;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.krazo.Properties;

import java.util.HashMap;
import java.util.Map;

@ApplicationPath("mvc")
public class MvcConfig extends Application {

    @Override
    public Map<String, Object> getProperties() {
        Map<String,Object> viewExtension = new HashMap<>();
        viewExtension.put(Properties.DEFAULT_VIEW_FILE_EXTENSION, "jsp");

        viewExtension.put(Properties.REDIRECT_SCOPE_QUERY_PARAM_NAME, "ScopeId");

        return viewExtension;
    }
}
