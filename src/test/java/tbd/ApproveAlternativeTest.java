package tbd;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

import tbd.http.ApproveAlternativeRequest;
import tbd.http.GenericResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class ApproveAlternativeTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = null;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
    public void testApproveAlternative() {
        ApproveAlternative handler = new ApproveAlternative();
        Context ctx = createContext();
        ApproveAlternativeRequest input = new ApproveAlternativeRequest("b1cea", 4);
        GenericResponse output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals(output.statusCode, 200);
    }
    
    @Test
    public void testApproveAlternativeLockedChoice() {
        ApproveAlternative handler = new ApproveAlternative();
        Context ctx = createContext();
        ApproveAlternativeRequest input = new ApproveAlternativeRequest("2009a", 5);
        GenericResponse output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals(output.statusCode, 300);
    }
}
