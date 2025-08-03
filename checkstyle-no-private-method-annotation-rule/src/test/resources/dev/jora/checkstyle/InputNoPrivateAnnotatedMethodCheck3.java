import org.junit.Test;
import com.example.TestAnnotation;
import com.example.SecondAnnotation;

public class InputNoPrivateAnnotatedMethodCheck1 {

    @TestAnnotation
    private void testPrivate() { }

    @com.example.TestAnnotation
    private void testFQCN() { }

    @SecondAnnotation
    private void testSecondPrivate() { }

    @com.example.SecondAnnotation
    private void testSecondFQCN() { }
}
