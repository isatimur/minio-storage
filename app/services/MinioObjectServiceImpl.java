package services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import config.MinioConfig;
import io.minio.Result;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidArgumentException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.NoResponseException;
import io.minio.messages.Item;
import modules.MinioClientInjection;
import org.xmlpull.v1.XmlPullParserException;
import play.Logger;
import play.libs.Json;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * MinioObjectServiceImpl. Created at 7/27/2018 11:40 AM by @author Timur Isachenko
 * @isatimur | † be patient and test it! †
 */
@Singleton
public class MinioObjectServiceImpl implements MinioObjectService {
    private String TAG = this.getClass().getSimpleName();

    @Inject
    private MinioClientInjection minioClient;

    @Inject
    private MinioConfig minioConfig;

    @Override
    public ObjectNode createObject(String bucketName, String objectName, InputStream inputStream, String contentType) {
        ObjectNode result = Json.newObject();
        try {
            minioClient.getInstance().putObject(bucketName, objectName, inputStream, contentType);
            ObjectNode data = Json.newObject();
            data.put("object_name", objectName);
            data.put("object_url", minioConfig.getMsHost() +
                    File.separator + "bucket" + File.separator + bucketName +
                    File.separator + "object" + File.separator + objectName);

            result.put("code", 200);
            result.put("response", "success");
            result.put("message", "Object " + objectName + " has been created.");
            result.put("data", data);
        } catch (InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException | IOException |
                InvalidKeyException | NoResponseException | XmlPullParserException | ErrorResponseException |
                InternalException | InvalidArgumentException e) {
            e.printStackTrace();
            Logger.debug(TAG + " : " + e.getMessage());

            result.put("code", 201);
            result.put("response", "failed");
            result.put("message", e.getMessage());
        }
        return result;
    }

    @Override
    public InputStream getObject(String bucketName, String objectName) {
        try {
            return minioClient.getInstance().getObject(bucketName, objectName);
        } catch (InvalidBucketNameException | NoSuchAlgorithmException | InsufficientDataException | InvalidKeyException | IOException | XmlPullParserException | NoResponseException | InternalException | ErrorResponseException | InvalidArgumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterable<Result<Item>> getObjects(String bucketName) {
        try {
            return minioClient.getInstance().listObjects(bucketName);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ObjectNode newObject(String bucketName, String objectName, InputStream inputStream, String contentType) {
        return createObject(bucketName, objectName, inputStream, contentType);
    }


}
