/*
 * Copyright (c) 2019 Wave Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.wavesoftware.log4j2.spring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 1.0.0
 */
class Log4j2AutoConfiguration {

  private static final Logger LOGGER =
    LoggerFactory.getLogger(Log4j2AutoConfiguration.class);
  private final AtomicBoolean initialized = new AtomicBoolean(false);

  @EventListener(ContextRefreshedEvent.class)
  void onInitialize(ContextRefreshedEvent event) {
    if (!initialized.compareAndSet(false, true)) {
      return;
    }
    LOGGER.info("Initializing Log4j2 for Spring...");
    LoggerContext loggerContext = getLoggerContext();
    loggerContext.setExternalContext(Log4j2AutoConfiguration.this);
  }

  @EventListener(ContextClosedEvent.class)
  void onShutdown(ContextClosedEvent event) {
    if (!initialized.compareAndSet(true, false)) {
      return;
    }
    ifRootContext(event).then(() -> {
      LOGGER.info("Shutting down Log4j2 for Spring...");
      LoggerContext loggerContext = getLoggerContext();
      loggerContext.setExternalContext(null);
      loggerContext.stop();
    });
  }

  private LoggerContext getLoggerContext() {
    return (LoggerContext) LogManager.getContext(false);
  }

  private Then ifRootContext(ApplicationContextEvent event) {
    return code -> {
      if (event.getApplicationContext().getParent() == null) {
        code.run();
      }
    };
  }

  private interface Then {
    void then(Runnable code);
  }
}
