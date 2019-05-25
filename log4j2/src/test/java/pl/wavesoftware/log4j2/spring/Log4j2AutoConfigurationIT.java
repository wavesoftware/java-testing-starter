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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.wavesoftware.log4j2.CollectedEvent;
import pl.wavesoftware.log4j2.CollectorManager;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 1.0.0
 */
@EnableAutoConfiguration
class Log4j2AutoConfigurationIT {

  private final CollectorManager manager =
    CollectorManager.getCollectorManager("PseudoConsole");

  @AfterEach
  @BeforeEach
  void after() {
    manager.clear();
  }

  @Test
  void operation() {
    // when
    try (ConfigurableApplicationContext ctx =
           new AnnotationConfigApplicationContext(getClass())) {
      assertThat(ctx.getBeanDefinitionNames())
        .contains("pl.wavesoftware.log4j2.spring.Log4j2AutoConfiguration");
    }

    // then
    assertThat(manager.collected())
      .extracting(this::formattedMessageOf)
      .containsExactly(
        "\u001B[32m INFO\u001B[0;39m \u001B[33m[      main]\u001B[0;39m " +
          "\u001B[36mp.w.l.s.Log4j2AutoConfiguration         \u001B[0;39m" +
          " \u001B[37;2m:\u001B[0;39m Initializing Log4j2 for Spring...\n",
        "\u001B[32m INFO\u001B[0;39m \u001B[33m[      main]\u001B[0;39m " +
          "\u001B[36mp.w.l.s.Log4j2AutoConfiguration         \u001B[0;39m" +
          " \u001B[37;2m:\u001B[0;39m Shutting down Log4j2 for Spring...\n"
      );
  }

  private String formattedMessageOf(CollectedEvent event) {
    return event.formattedMessage().replace("\r\n", "\n");
  }
}
