/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.jraft.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jiachun.fjc
 */
public final class SignalHelper {

    private static final Logger LOG = LoggerFactory.getLogger(SignalHelper.class);
    private static final List<JRaftSignalHandler> handlers = new ArrayList<>();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.info("Handling shutdown signal.");
            for (final JRaftSignalHandler h : SignalHelper.handlers) {
                try {
                    h.handle();
                } catch (Exception e) {
                    LOG.error("Fail to handle shutdown signal: {}.", h.getClass().getSimpleName());
                }
            }
        }));
    }

    /**
     * Registers user signal handlers.
     *
     * @param handlers user signal handlers
     */
    public static void addSignal(final List<JRaftSignalHandler> handlers) {
        SignalHelper.handlers.addAll(handlers);
    }

    private SignalHelper() {
    }
}
