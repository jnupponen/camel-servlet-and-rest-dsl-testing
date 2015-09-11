# Camel Servlet and Rest DSL testing

As as solution to Stackoverflow.com [question](http://stackoverflow.com/questions/32483178/how-to-set-cache-control-headers-in-apache-camel/). The code exposes simple rest interface GET /hello which will response with text "ok". Endpoint /hello is just a proxy to real implementation that is exposed as netty-http-endpoint. The code uses custom HeaderFilterStrategy because otherwise Camel will strip custom headers from the response as was the problem in the Stackoverflow question.

## Endpoints
- GET http://localhost:10000/hello
- GET http://localhost:10000/hi
- GET http://localhost:**20000**/


## Running this example
```
$ mvn compile exec:java
```

## Testing this example
```
$ curl -D - http://localhost:10000/hello
```
The command above should print out the custom Cache-Control header that we've set in /hello endpoint as well as response text "ok".

## Note this!
There must be **jndi.properties** present in **src/main/resources** otherwise you get exception "**javax.naming.NoInitialContextException**: Need to specify class name in environment or system property, or as an applet parameter, or in an application resource file:  java.naming.factory.initial". For more information check Camel [site](http://camel.apache.org/exception-javaxnamingnoinitialcontextexception.html).