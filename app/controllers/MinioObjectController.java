package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import config.MinioConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.util.MimeTypeUtils;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.MinioObjectService;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * MinioObjectController. Created at 7/27/2018 11:38 AM by @author Timur Isachenko
 * @isatimur | † be patient and test it! †
 */
@Api(value = "MinioObjectController. Делает много важного и интересного", consumes = "application/json")
public class MinioObjectController extends Controller {

    private String TAG = this.getClass().getSimpleName();

    @Inject
    private MinioObjectService minioObjectServiceImpl;

    // обязательная заглушка для Docker-а, должна быть привязана к GET / в файле routes
    @ApiOperation(value = "заглушка для docker", hidden = true)
    public Result index() {
        return ok("OK");
    }

    // Получить файл из Minio
    @ApiOperation(value = "получить файл из minio", produces = MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE)
    @ApiResponse(code = 200, message = "result as a stream", response = File.class)
    public Result getObject(String bucketName, String objectName) {
        try {
            byte[] bytes = IOUtils.toByteArray(minioObjectServiceImpl.getObject(bucketName, objectName));
            if (bytes != null) {
                return ok(bytes)
                        .as("application/x-download")
                        .withHeader("Content-disposition", "attachment; filename=" + objectName);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug(TAG + " : " + ex.getMessage());
        }
        return notFound("Content " + objectName + " not found in " + bucketName + ".");
    }

    // Получить файл из Minio
    @ApiOperation(value = "получить файл из minio")
    public Result getObjects(String bucketName) {
        return ok(Json.toJson(minioObjectServiceImpl.getObjects(bucketName)));

    }

    public JsonNode getUnknownErrorResult() {
        return Json.newObject()
                .put("code", 500)
                .put("response", "Something went wrong !!");
    }

    public JsonNode getUnknownErrorResult(String message) {
        return Json.newObject()
                .put("code", 500)
                .put("response", "Something went wrong !!")
                .put("message", message);
    }

    // Сохранить файл в Minio
    @ApiOperation(value = "Cохранить файл в minio", consumes = "multipart/form-data")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "file",
                    name = "file",
                    paramType = "formData", required = true)
    })
    public Result newObject(String bucketName) {
        try {
            Http.MultipartFormData<File> requestedData = request().body().asMultipartFormData();

            for (Http.MultipartFormData.FilePart<File> requestedFile : requestedData.getFiles()) {
                String objectName = MinioConfig.getRandomText() + "-" + requestedFile.getFilename();
                System.out.println(objectName);

                try (BufferedInputStream objectInputStream = new BufferedInputStream(new FileInputStream(requestedFile.getFile()))) {
                    String objectContentType = requestedFile.getContentType();
                    System.out.println(objectContentType);
                    return ok(minioObjectServiceImpl.createObject(bucketName, objectName,
                            objectInputStream, objectContentType));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.debug(TAG + " : " + ex.getMessage());
        }

        return ok(getUnknownErrorResult().toString());
    }

}
