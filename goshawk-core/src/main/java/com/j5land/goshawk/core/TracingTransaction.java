package com.j5land.goshawk.core;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import com.j5land.goshawk.core.bean.TracingContext;
import com.j5land.goshawk.core.constants.SpanConstant;
import org.apache.commons.lang3.StringUtils;

/**
 * 提供的便捷的打点工具
 */
public class TracingTransaction {

    /**
     * 必须开启新的事务
     * 注:仅仅在开始的地方
     *
     * @param name
     * @return
     */
    public static Span beginTransaction(String name) {
        return createSpan(name, true);
    }

    /**
     * 开启事务
     *
     * @param name
     */
    public static Span continueTransaction(String name) {
        return createSpan(name, false);
    }

    /**
     * create new span
     *
     * @param name
     * @param forceNewOne
     * @return
     */
    private static Span createSpan(String name, boolean forceNewOne) {
        try {
            if (StringUtils.isBlank(name)) {
                return null;
            }
            Tracer tracer = Tracing.currentTracer();
            if (tracer == null) {
                return null;
            }
            Span parent = TracingContext.get();
            Span span;
            if (parent == null || forceNewOne) {
                span = tracer.newTrace();
                if (span == null || span.isNoop()) {
                    return null;
                }
                span.annotate(SpanConstant.SERVER_RECV);
                TracingContext.set(span);
            } else {
                span = tracer.newChild(parent.context());
                if (span == null || span.isNoop()) {
                    return null;
                }
                span.annotate(SpanConstant.CLIENT_SEND);
            }
            span.name(name);
            span.start();
            return span;
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * 完成span
     *
     * @param span
     */
    public static void commit(Span span) {
        try {
            if (span != null) {
                span.finish();
            }
        } catch (Exception e) {
        }
    }
}
