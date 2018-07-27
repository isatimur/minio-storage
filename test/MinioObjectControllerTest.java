import controllers.MinioObjectController;
import org.junit.Test;
import play.mvc.Result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.contentAsString;


public class MinioObjectControllerTest {

    @Test
    public void testIndex() {
        Result result = new MinioObjectController().index();
        assertEquals(OK, result.status());
        assertEquals("text/plain", result.contentType().get());
        assertEquals("utf-8", result.charset().get());
        assertTrue(contentAsString(result).contains("OK"));
    }

}