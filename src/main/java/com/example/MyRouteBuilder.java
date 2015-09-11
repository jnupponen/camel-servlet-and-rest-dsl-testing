package com.example;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.main.Main;
import org.apache.camel.spi.HeaderFilterStrategy;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.enableHangupSupport();
        main.addRouteBuilder(new MyRouteBuilder());
        main.run(args);
    }

    public void configure() {
        JndiRegistry registry = getContext().getRegistry(JndiRegistry.class);
        registry.bind("filter", new HeaderFilter());
        
        // Configure the Camel Rest Component.
        restConfiguration()
        .host("localhost")
        .endpointProperty("headerFilterStrategy","#filter")
        .setPort("10000");

        // Camel Rest Component way of defining endpoints.
        from("rest:get:hello")
        .to("http://localhost:20000?bridgeEndpoint=true")
        // Test that setting custom headers work with custom filter.
        .setHeader("Cache-Control",constant("private, max-age=0,no-store"));
        
        // Camel Rest DSL way of defining endpoints.
        rest("/hi")
        .get().route().transform().constant("Hi World");
        
        // Actual backend service for /hello endpoint proxy.
        from("netty-http:http://localhost:20000")
        .setBody(constant("ok"));
    }

    public class HeaderFilter implements HeaderFilterStrategy {

        @Override
        public boolean applyFilterToCamelHeaders(String arg0, Object arg1, Exchange arg2) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean applyFilterToExternalHeaders(String arg0, Object arg1, Exchange arg2) {
            // TODO Auto-generated method stub
            return false;
        }
        
    }
}
