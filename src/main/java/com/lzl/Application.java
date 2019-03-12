package com.lzl;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.ganglia.GangliaReporter;
import com.lzl.metric.MyMetric;
import info.ganglia.gmetric4j.gmetric.GMetric;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.util.concurrent.TimeUnit;

/**
 * @author lizanle
 * @Date 2019/3/12 13:53
 */
@SpringBootApplication
@ServletComponentScan
public class Application implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    public void run(String... args) throws Exception {
//        final GMetric ganglia = new GMetric("ganglia.example.com", 8649, GMetric.UDPAddressingMode.MULTICAST, 1);
//        final GangliaReporter reporter = GangliaReporter.forRegistry(MyMetric.registry)
//                .convertRatesTo(TimeUnit.SECONDS)
//                .convertDurationsTo(TimeUnit.MILLISECONDS)
//                .build(ganglia);
//        reporter.start(1, TimeUnit.MINUTES);
//        final ConsoleReporter reporter = ConsoleReporter.forRegistry(MyMetric.registry)
//                .convertRatesTo(TimeUnit.SECONDS)
//                .convertDurationsTo(TimeUnit.MILLISECONDS)
//                .build();
//        reporter.start(5, TimeUnit.SECONDS);
    }
}
