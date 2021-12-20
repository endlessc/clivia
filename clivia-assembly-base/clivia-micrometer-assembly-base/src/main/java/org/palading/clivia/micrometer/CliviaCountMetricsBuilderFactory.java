/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.palading.clivia.micrometer;

import java.util.function.Consumer;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.palading.clivia.cache.CliviaStandandCacheFactory;

/**
 * @author palading_cr
 * @title CliviaCountMetricsBuilderFactory
 * @project clivia
 */
public class CliviaCountMetricsBuilderFactory implements CliviaCounterMetrics<Counter.Builder> {

    private final MeterRegistry meterRegistry;
    private Counter counter;
    private String name;

    public CliviaCountMetricsBuilderFactory(MeterRegistry meterRegistry, String name) {
        this.meterRegistry = meterRegistry;
        this.name = name;
    }

    @Override
    public Counter create(Consumer<Counter.Builder> consumer) {
        CliviaStandandCacheFactory cliviaStandandCacheFactory = CliviaStandandCacheFactory.getCliviaStandandCacheFactory();
        if (null != cliviaStandandCacheFactory.get(name)) {
            return (Counter)cliviaStandandCacheFactory.get(name);
        }
        counter = new CliviaCounterBuilder(meterRegistry, name, consumer).build();
        cliviaStandandCacheFactory.putIfAbsent(name, counter);
        return counter;
    }

    @Override
    public void increment(Counter counter) {
        counter.increment();
    }
}
