package com.sv.mc;


import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
//@ComponentScan
//@ImportResource(locations={"classpath:spring-redis.xml"})
@EnableCaching
//@EnableAutoConfiguration
@EnableScheduling
public class MCApplication {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(MCApplication.class, args);

    }

//    @Bean
//    public ServletWebServerFactory servletContainer(){
//        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
//        tomcat.addAdditionalTomcatConnectors(createSslConnector());
//        return tomcat;
//    }
//
//    private Connector createSslConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
//        try {
////            File keystore = new ClassPathResource("sample.jks").getFile();
//            connector.setScheme("https");
//            connector.setSecure(true);
//            connector.setPort(8443);
//            protocol.setSSLEnabled(true);
//            protocol.setKeystoreFile("/usr/local/mcProject/sample.jks");
//            protocol.setKeystorePass("secret");
//            protocol.setKeyPass("password");
//            return connector;
//        }
////        catch (IOException ex) {
////            throw new IllegalStateException(" can't access keystore: [" + "keystore"
////                    + "] or truststore: [" + "keystore" + "]", ex);
////        }
//        catch (Exception ex) {
//            throw new IllegalStateException("can't access keystore: [" + "keystore"
//                    + "] or truststore: [" + "keystore" + "]", ex);
//        }
//    }

//    @Bean
//    public EmbeddedServletContainerFactory servletContainer() {
//        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint constraint = new SecurityConstraint();
//                constraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                constraint.addCollection(collection);
//                context.addConstraint(constraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(httpConnector());
//        return tomcat;
//    }

    @Bean
    public Connector httpConnector(){
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(true);
        connector.setRedirectPort(443);
        return connector;
    }

}
