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


/**
 * RestAssured filter that intercepts API calls to capture request and response data.
 *
 * This filter records the full API exchange, including the request method,
 * URI, headers, body, response status code, response headers, and response body.
 * The captured data is forwarded to the ApiCallRecorder for storage and
 * observability purposes.
 *
 * The filter is automatically applied to all API requests through the
 * RequestSpecification created by RestAssuredSpecFactory.
 *
 * This mechanism allows the framework to collect API interaction evidence
 * for debugging, reporting, and test traceability without affecting the
 * execution of the HTTP request itself.
 *
 * Typical flow:
 *   RestAssuredClient -> ApiCaptureFilter -> HTTP execution -> ApiCallRecorder
 */
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