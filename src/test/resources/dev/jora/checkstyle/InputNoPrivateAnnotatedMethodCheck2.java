import org.junit.Test;

public class InputNoPrivateAnnotatedMethodCheck1 {

    @Test
    private void testPrivate() { }

    @PostConstruct
    private void init() { }

    @Transactional
    private void save() { }

    @Transactional
    private void delete() { }

    @AllowedAnnotation
    private void function() { }
}
