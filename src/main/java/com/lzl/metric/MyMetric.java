package com.lzl.metric;

import com.codahale.metrics.MetricRegistry;

/**
 * @author lizanle
 * @Date 2019/3/12 14:05
 */
public interface MyMetric {
     final static MetricRegistry registry = new MetricRegistry();
}
