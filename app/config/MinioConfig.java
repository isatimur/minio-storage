package config;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.typesafe.config.Config;

import java.util.UUID;

/**
 * MinioConfig. Created at 7/27/2018 11:38 AM by @author Timur Isachenko
 * @isatimur | † be patient and test it! †
 */
@Singleton
public class MinioConfig {
    @Inject
    private Config config;

    public static String getRandomText() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String getMsHost() {
        return config.getString("minioConfig.api");
    }

    public String getMsUri() {
        return config.getString("minioConfig.base");
    }

    public String getMsSecret() {
        return config.getString("minioConfig.miSecrete");
    }

    public String getMsAccessKey() {
        return config.getString("minioConfig.miAccessToken");
    }
}
