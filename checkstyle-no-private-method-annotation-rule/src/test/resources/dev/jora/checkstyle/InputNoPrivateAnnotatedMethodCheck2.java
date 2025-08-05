import org.junit.Test;

public class InputNoPrivateAnnotatedMethodCheck1 {

    @Test
    private void testPrivate() { }

    @ValidAnnotation
    @PostConstruct
    @AnotherValidAnnotation
    private void init() { }

    @Transactional
    private void save() { }

    @Transactional(readOnly = true)
    private void delete() { }

    @AllowedAnnotation
    private void function() { }
}
