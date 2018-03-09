package com.j5land.goshawk.core.constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by zhudongquan on 17/6/27.
 */
public class SpanConstant {
    public static final String SAMPLED_NAME = "X-B3-Sampled";
    public static final String PROCESS_ID_NAME = "X-Process-Id";
    public static final String PARENT_ID_NAME = "X-B3-ParentSpanId";
    public static final String TRACE_ID_NAME = "X-B3-TraceId";
    public static final String SPAN_NAME_NAME = "X-Span-Name";
    public static final String SPAN_ID_NAME = "X-B3-SpanId";
    public static final String SPAN_EXPORT_NAME = "X-Span-Export";
    public static final String SPAN_FLAGS = "X-B3-Flags";
    public static final String SPAN_BAGGAGE_HEADER_PREFIX = "baggage";
    public static final Set<String> SPAN_HEADERS = new HashSet<>(
            Arrays.asList(SAMPLED_NAME, PROCESS_ID_NAME, PARENT_ID_NAME, TRACE_ID_NAME,
                    SPAN_ID_NAME, SPAN_NAME_NAME, SPAN_EXPORT_NAME));

    public static final String SPAN_SAMPLED = "1";
    public static final String SPAN_NOT_SAMPLED = "0";

    public static final String SPAN_LOCAL_COMPONENT_TAG_NAME = "lc";
    public static final String SPAN_ERROR_TAG_NAME = "error";

    public static final long SPAN_OVERTIME_LIMIE = 200;

    public static final String METHOD_NAME_APPEND = "appendmethod";

    /**
     * <b>cr</b> - Client Receive. Signifies the end of the span. The client has
     * successfully received the response from the server side. If one subtracts the cs
     * timestamp from this timestamp one will receive the whole time needed by the client
     * to receive the response from the server.
     */
    public static final String CLIENT_RECV = "cr";

    /**
     * <b>cs</b> - Client Sent. The client has made a request (a client can be e.g.
     * start of the span.
     */
    // For an outbound RPC call, it should log a "cs" annotation.
    // If possible, it should log a binary annotation of "sa", indicating the
    // destination address.
    public static final String CLIENT_SEND = "cs";

    /**
     * <b>sr</b> - Server Receive. The server side got the request and will start
     * processing it. If one subtracts the cs timestamp from this timestamp one will
     * receive the network latency.
     */
    // If an inbound RPC call, it should log a "sr" annotation.
    // If possible, it should log a binary annotation of "ca", indicating the
    // caller's address (ex X-Forwarded-For header)
    public static final String SERVER_RECV = "sr";

    /**
     * <b>ss</b> - Server Send. Annotated upon completion of request processing (when the
     * response got sent back to the client). If one subtracts the sr timestamp from this
     * timestamp one will receive the time needed by the server side to process the
     * request.
     */
    public static final String SERVER_SEND = "ss";

    /**
     * <a href="https://github.com/opentracing/opentracing-go/blob/master/ext/tags.go">As
     * in Open Tracing</a>
     */
    public static final String SPAN_PEER_SERVICE_TAG_NAME = "peer.service";

    /**
     * ID of the instance from which the span was originated.
     */
    public static final String INSTANCEID = "spring.instance_id";

    /**
     * Redis
     */
    public static final String REDIS_TAG_NAME_PREFIX = "R|";

    /**
     * Mongo
     */
    public static final String MONGO_TAG_NAME_PREFIX = "M|";

    /**
     * JSON_RPC_CALL
     */
    public static final String JSONRPC_CALL_TAG_NAME_PREFIX = "C|";

    /**
     * database
     */
    public static final String DATABASE_TAG_NAME_PREFIX = "D|";

    /**
     * 忽略的报警
     */
    public static final String IGNORE_WARNNING_SUFFIX = "###";


}
