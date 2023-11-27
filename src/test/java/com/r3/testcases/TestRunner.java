package com.r3.testcases;

import com.r3.listeners.RunMultipleTimesListeners;
import org.testng.TestNG;

public class TestRunner {
    static TestNG testNG;
    public static void main(String[] args) {
        testNG = new TestNG();
        RunMultipleTimesListeners  listeners = new RunMultipleTimesListeners();
        testNG.setTestClasses(new Class[]{ActionsKeywords.class});
        testNG.addListener(listeners);
        testNG.run();
    }
}
