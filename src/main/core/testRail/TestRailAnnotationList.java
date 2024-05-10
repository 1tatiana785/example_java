package core.testRail;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface TestRailAnnotationList {
    int[] TestCaseIds();
}
