package com.blazer.mq.client;

import com.blazer.mq.api.Client;
import com.blazer.mq.api.Request;
import com.blazer.mq.api.Response;
import com.google.inject.Inject;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

/** @author Constantine Linnick <theaspect@gmail.com> */
public class ServiceProxy {
    private static final ReentrantLock lock = new ReentrantLock();
    protected Client client;

    @Inject
    public ServiceProxy(Client client) {
        this.client = client;
    }

    /** Workaround array covariance */
    Serializable[] serialize(Object... args) {
        if (args == null) {
            return new Serializable[0];
        } else {
            Serializable[] result = new Serializable[args.length];
            for (int i = 0; i < args.length; i++) {
                result[i] = (Serializable) args[i];
            }
            return result;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(final Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{clazz},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
                        Object result;
                        try {
                            result = remoteInvokeTyped(clazz.getSimpleName() + "." + method.getName(), serialize(args));
                        } catch (Exception e) {
                            for (Class c : method.getExceptionTypes()) {
                                if (c.isInstance(e)) {
                                    throw e;
                                }
                            }
                            throw new RuntimeException("Unhandled exception", e);
                        }
                        return (method.getReturnType() != null) ? result : null;
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public <T> T remoteInvokeTyped(String method, Serializable... args) throws Exception {
        return (T) remoteInvoke(method, args);
    }

    public Serializable remoteInvoke(String method, Serializable... args) throws Exception {
        lock.lock();
        try {
            String className = method.substring(0, method.lastIndexOf("."));
            String methodName = method.substring(method.lastIndexOf(".") + 1);

            Request request = new Request(UUID.randomUUID().toString(), className, methodName, args);

            client.sendRequest(request);

            Response response = client.getResponse();
            if (response.hasException()) {
                Exception ex;
                try {
                    @SuppressWarnings("unchecked")
                    Class<? extends Exception> c = (Class<? extends Exception>) Class.forName(response.getException());
                    Constructor<? extends Exception> e = c.getDeclaredConstructor(String.class);
                    // Explicit cast to avoid problems with null argument
                    ex = e.newInstance((Object) response.getMessage());
                } catch (Exception e) {
                    ex = new RuntimeException("Exception not found " +
                            response.getException() + " : " + response.getMessage());
                }
                throw ex;
            }

            return response.getResult();
        } finally {
            lock.unlock();
        }
    }
}
