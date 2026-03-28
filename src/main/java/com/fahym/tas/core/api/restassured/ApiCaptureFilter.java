package com.fahym.tas.core.api.restassured;

import com.fahym.tas.observability.api.ApiCallRecorder;
import com.fahym.tas.observability.api.ApiExchange;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import io.restassured.response.Response;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ApiCaptureFilter implements Filter {

    @Override
    public Response filter(FilterableRequestSpecification req,
                           FilterableResponseSpecification resSpec,
                           FilterContext ctx) {

        Response res = ctx.next(req, resSpec);

        String method = safe(req.getMethod());
        String uri = safe(req.getURI());

        Map<String, String> reqHeaders = headersToMap(req.getHeaders());
        String reqBody = req.getBody() == null ? "" : req.getBody().toString();

        Map<String, String> resHeaders = headersToMap(res.getHeaders());
        String resBody = res.getBody() == null ? "" : res.getBody().asString();

        ApiCallRecorder.record(new ApiExchange(
                method, uri, reqHeaders, reqBody,
                res.getStatusCode(), resHeaders, resBody
        ));

        return res;
    }

    private static Map<String, String> headersToMap(io.restassured.http.Headers headers) {
        Map<String, String> map = new LinkedHashMap<>();
        if (headers == null) return map;
        headers.asList().forEach(h -> map.put(h.getName(), h.getValue()));
        return map;
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }
}