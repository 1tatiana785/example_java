package core.testRail;

import static core.testRail.DataTestRail.runId;
import static core.testRail.DataTestRail.testCasesIds;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import core.helper.Base;

public class RetryListenerClass extends Base implements IAnnotationTransformer {


    @Override
    public void transform(ITestAnnotation testAnnotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (runId == 0)
            return;
        if (testCasesIds.get(0).equals("0")) {
            return;
        }
        if (getCaseIds(testMethod, testClass) != null) {
            for (int id : getCaseIds(testMethod, testClass)) {
                if (testCasesIds.stream().anyMatch(i -> i.equals(String.valueOf(id)))) {
                    testAnnotation.setEnabled(
                            testCasesIds.stream().anyMatch(i -> i.equals(String.valueOf(id))));
                    break;
                }
            }
        } else {
            testAnnotation.setEnabled(
                    testCasesIds.stream().anyMatch(str -> str.equals(getCaseId(testMethod, testClass))));
        }
    }

    private int[] getCaseIds(Method method, Class testClass) {
        //In case annotation will be placed on test method
        if (method != null) {
            if (method.isAnnotationPresent(TestRailAnnotationList.class)) {
                return method.getAnnotation(TestRailAnnotationList.class).TestCaseIds();
            }
        }
        //In case annotation will be placed on test class
        if (testClass != null) {
            if (testClass.isAnnotationPresent(TestRailAnnotationList.class)) {
                return ((TestRailAnnotationList) testClass.getAnnotation(TestRailAnnotationList.class)).TestCaseIds();
            }
        }
        return null;
    }

    @Override
    public String getCaseId(Method method, Class testClass) {
        return super.getCaseId(method, testClass);
    }
}