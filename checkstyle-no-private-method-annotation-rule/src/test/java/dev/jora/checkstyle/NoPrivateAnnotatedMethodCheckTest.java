package dev.jora.checkstyle;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.jupiter.api.Test;

import static dev.jora.checkstyle.NoPrivateAnnotatedMethodCheck.MSG_KEY;

/**
 * @author dyadyaJora on 01.08.2025
 */
class NoPrivateAnnotatedMethodCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "dev/jora/checkstyle";
    }

    @Test
    public void testSingleForbiddenAnnotationsWithoutErrors() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoPrivateAnnotatedMethodCheck.class);

        checkConfig.addProperty("forbiddenAnnotations", "NotTest");

        final String[] expected = {
        };

        verify(checkConfig, getPath("InputNoPrivateAnnotatedMethodCheck1.java"), expected);
    }

    @Test
    public void testSingleForbiddenAnnotationsWithSingleError() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoPrivateAnnotatedMethodCheck.class);

        checkConfig.addProperty("forbiddenAnnotations", "Test");

        final String[] expected = {
                "5:5: " + getCheckMessage(MSG_KEY, "Test"),
        };

        verify(checkConfig, getPath("InputNoPrivateAnnotatedMethodCheck1.java"), expected);
    }

    @Test
    public void testMultipleForbiddenAnnotationsWithMultipleErrors() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoPrivateAnnotatedMethodCheck.class);

        checkConfig.addProperty("forbiddenAnnotations", "Test");
        checkConfig.addProperty("forbiddenAnnotations", "PostConstruct");
        checkConfig.addProperty("forbiddenAnnotations", "Transactional");

        final String[] expected = {
                "5:5: " + getCheckMessage(MSG_KEY, "Test"),
                "9:5: " + getCheckMessage(MSG_KEY, "PostConstruct"),
                "13:5: " + getCheckMessage(MSG_KEY, "Transactional"),
                "16:5: " + getCheckMessage(MSG_KEY, "Transactional")
        };

        verify(checkConfig, getPath("InputNoPrivateAnnotatedMethodCheck2.java"), expected);
    }

    @Test
    public void testByFQCNErrors() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(NoPrivateAnnotatedMethodCheck.class);

        checkConfig.addProperty("forbiddenAnnotations", "com.example.TestAnnotation");
        checkConfig.addProperty("forbiddenAnnotations", "SecondAnnotation");

        final String[] expected = {
                "7:5: " + getCheckMessage(MSG_KEY, "TestAnnotation"),
                "10:5: " + getCheckMessage(MSG_KEY, "com.example.TestAnnotation"),
                "13:5: " + getCheckMessage(MSG_KEY, "SecondAnnotation"),
                "16:5: " + getCheckMessage(MSG_KEY, "com.example.SecondAnnotation"),
        };

        verify(checkConfig, getPath("InputNoPrivateAnnotatedMethodCheck3.java"), expected);
    }
}