package com.blazer.repl.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GroovyShell;
import groovy.ui.SystemOutputInterceptor;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** Most of implementation came from http://naleid.com/blog/2013/10/17/embedding-a-groovy-web-console-in-a-java-spring-app */
@Slf4j
@Singleton
public class GroovyService {

    private final Provider<EntityManager> entityManagerProvider;

    @Inject
    public GroovyService(Provider<EntityManager> entityManagerProvider) {
        this.entityManagerProvider = entityManagerProvider;
    }

    public Map executeScript(String script) {
        GroovyService.log.warn("Executing Script:\n" + script);

        try {
            return eval(script);
        } catch (Throwable t) {
            GroovyService.log.error("", t);
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("error", t.getMessage());
            System.out.println(t);
            return resultMap;
        }
    }

    protected Map<String, String> eval(String script) {
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("script", script);
        resultMap.put("startTime", new Date().toString());

        SystemOutputInterceptorClosure outputCollector = new SystemOutputInterceptorClosure(null);
        SystemOutputInterceptor systemOutputInterceptor = new SystemOutputInterceptor(outputCollector);
        systemOutputInterceptor.start();

        try {
            Map<String, Object> bindingValues = new HashMap<>();
            resultMap.put("result", eval(script, bindingValues));
        } catch (Throwable t) {
            GroovyService.log.error("", t);
            resultMap.put("error", t.getMessage());
        } finally {
            systemOutputInterceptor.stop();
        }

        resultMap.put("output", outputCollector.getStringBuffer().toString().trim());
        resultMap.put("endTime", new Date().toString());
        return resultMap;
    }

    public String eval(String script, Map<String, Object> bindingValues) {
        GroovyShell shell = createShell(bindingValues);
        Object result = shell.evaluate(script);
        String resultString = result != null ? result.toString() : "null";
        GroovyService.log.trace("eval() result: {}", resultString);
        return resultString;
    }

    private GroovyShell createShell(Map<String, Object> bindingValues) {
        bindingValues.put("emp", entityManagerProvider);
        bindingValues.put("log", GroovyService.log);
        return new GroovyShell(this.getClass().getClassLoader(), new Binding(bindingValues));
    }


    // here's where the magic happens allowing the service to capture the output while the script runs
    class SystemOutputInterceptorClosure extends Closure {

        StringBuffer stringBuffer = new StringBuffer();

        public SystemOutputInterceptorClosure(Object owner) {
            super(owner);
        }

        @Override
        public Object call(Object params) {
            stringBuffer.append(params);
            return false;
        }

        @Override
        public Object call(Object... args) {
            stringBuffer.append(args);
            return false;
        }

        public StringBuffer getStringBuffer() {
            return this.stringBuffer;
        }
    }
}
