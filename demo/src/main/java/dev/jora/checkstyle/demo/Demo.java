package dev.jora.checkstyle.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class Demo {

    @MyAnnotation
    private void privateMethod() {
        // This should be flagged by checkstyle
    }

    @MyAnnotation
    public void publicMethod() {
        // This is fine
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface MyAnnotation {
}

