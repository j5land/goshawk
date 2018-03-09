package com.j5land.goshawk.core.bean;

import brave.Span;

public class TracingContext {

    /**
     * There's no attribute namespace shared across request and response. Hence, we need to save off
     * a reference to the span in scope, so that we can close it in the response.
     */
    private static final ThreadLocal<Span> currentContext = new ThreadLocal<>();


    public static Span get() {
        return currentContext.get();
    }


    public static void set(Span span) {
        currentContext.set(span);
    }
}
