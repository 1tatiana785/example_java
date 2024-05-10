package core.testRail;

import org.testng.ITestResult;

import java.lang.reflect.Method;

public class ReadAnnotation {

    public int readTC(ITestResult est) {
        for (Method m : est.getInstance().getClass().getMethods()) {
            TestRailId produce = m.getAnnotation(TestRailId.class);
            if (produce != null && (est.getName().equals(m.getName())))
                return produce.TestCaseId();
        }
        return 0;
    }

    public int[] readTCs(ITestResult est) {
        for (Method m : est.getInstance().getClass().getMethods()) {
            TestRailAnnotationList produce = m.getAnnotation(TestRailAnnotationList.class);
            if (produce != null && (est.getName().equals(m.getName())))
                return produce.TestCaseIds();
        }
        return null;
    }
}