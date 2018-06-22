package com.healthpartners.atlas.plugins.servlet;

import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.healthpartners.atlas.plugins.manager.MetricCollector;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.exporter.common.TextFormat;
import io.prometheus.client.hotspot.DefaultExports;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Scanned
public class PrometheusExporter extends HttpServlet{
    private final CollectorRegistry registry;

    public PrometheusExporter(MetricCollector metricCollector) {
        this.registry = CollectorRegistry.defaultRegistry;
        this.registry.register(metricCollector.getCollector());
        DefaultExports.initialize();
    }

    @Override
    protected void doGet(
            final HttpServletRequest httpServletRequest,
            final HttpServletResponse httpServletResponse) throws IOException {

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType(TextFormat.CONTENT_TYPE_004);

        try (Writer writer = httpServletResponse.getWriter()) {
            TextFormat.write004(writer, registry.filteredMetricFamilySamples(parse(httpServletRequest)));
            writer.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        doGet(httpServletRequest, httpServletResponse);
    }

    private Set<String> parse(HttpServletRequest httpServletRequest) {
        String[] includedParam = httpServletRequest.getParameterValues("name[]");
        if (includedParam == null) {
            return Collections.emptySet();
        } else {
            return new HashSet<>(Arrays.asList(includedParam));
        }
    }

}