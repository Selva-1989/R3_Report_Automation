package com.r3.listeners;

import com.r3.datareader.PropertiesFileReader;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class RunMultipleTimesListeners implements IAnnotationTransformer  {

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if(testMethod.getName().contains("keywordEngine")){
           annotation.setInvocationCount(Integer.parseInt(PropertiesFileReader.getProperty("ExecutionCount")));
       }
    }
}
