# checkstyle-no-private-method-annotation-rule
[![Maven Central Version](https://img.shields.io/maven-central/v/dev.jora.checkstyle/checkstyle-no-private-method-annotation-rule)](https://central.sonatype.com/artifact/dev.jora.checkstyle/checkstyle-no-private-method-annotation-rule)
[![Build & Tests](https://github.com/dyadyaJora/checkstyle-no-private-method-annotation-rule/actions/workflows/gradle.yml/badge.svg)](https://github.com/dyadyaJora/checkstyle-no-private-method-annotation-rule/actions/workflows/gradle.yml)

A custom [Checkstyle](https://checkstyle.sourceforge.io/) rule that prohibits the use of annotations on private methods. This helps enforce code quality by ensuring that annotations are not mistakenly applied to methods that are not accessible outside their class.

## Features

- Fails Checkstyle if a private method is annotated.
- Easily pluggable into your existing Checkstyle configuration.

## Installation

### Gradle

Extend yours gradle checkstyle plugin with this custom rule, required updates in `build.gradle`

```gradle
plugins {
    // ...
    id 'checkstyle'
    // ...
}

dependencies {
    // add dependencies to checkstyle plugin context

    // add checkstyle tools
    checkstyle "com.puppycrawl.tools:checkstyle:${checkstyle.toolVersion}"
    // add new custom rule
    checkstyle 'dev.jora.checkstyle:checkstyle-no-private-method-annotation-rule:1.0.0'
}

checkstyle {
    // yours checkstyle configuration here
}
```

## Usage

1. **Update your Checkstyle configuration (XML):**

   Add the custom rule to your `checkstyle.xml`:

   ```xml
   <module name="Checker">
     ...
     <module name="TreeWalker">
       ...
       <module name="NoPrivateMethodAnnotation">
            <property name="severity" value="error"/>
            <property name="forbiddenAnnotations" value="MyAnnotation"/>
            <property name="forbiddenAnnotations" value="MyAnnotation2"/>
            <property name="forbiddenAnnotations" value="Transactional"/>
       </module>
     </module>
     ...
   </module>
   ```

2. **Run Checkstyle:**

   With Gradle:

   ```bash
   gradle checkstyleMain
   ```

## Example

```java
public class Example {
    @Transactional // <-- This will trigger a Checkstyle violation
    private void doSomething() { 
    }

    public void validMethod() {
    }
}
```

## License

The Apache License, Version 2.0
