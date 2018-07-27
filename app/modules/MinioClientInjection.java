package modules;

import com.google.inject.Inject;
import config.MinioConfig;
import io.minio.MinioClient;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import play.Logger;


/**
 * MinioClientInjection. Created at 7/27/2018 11:40 AM by @author Timur Isachenko
 * @isatimur | † be patient and test it! †
 */
public class MinioClientInjection {
    private MinioClient minioClient;

    @Inject
    public MinioClientInjection(MinioConfig minioConfig) {
        try {
            minioClient = new MinioClient(minioConfig.getMsUri(),
                    minioConfig.getMsAccessKey(), minioConfig.getMsSecret());
        } catch (InvalidEndpointException | InvalidPortException e) {
            e.printStackTrace();
            Logger.debug(MinioClientInjection.class.getSimpleName() + " : " + e.getMessage());
        }
    }

    public MinioClient getInstance() {
        return minioClient;
    }
}
