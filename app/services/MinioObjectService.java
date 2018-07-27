package services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.ImplementedBy;
import io.minio.Result;
import io.minio.messages.Item;

import java.io.InputStream;


/**
 * MinioObjectService. Created at 7/27/2018 11:40 AM by @author Timur Isachenko
 * @isatimur | † be patient and test it! †
 */
@ImplementedBy(MinioObjectServiceImpl.class)
public interface MinioObjectService {
    ObjectNode createObject(String bucketName, String objectName, InputStream inputStream, String contentType);

    InputStream getObject(String bucketName, String objectName);

    Iterable<Result<Item>> getObjects(String bucketName);

    ObjectNode newObject(String bucketName, String objectName, InputStream inputStream, String contentType);

}
