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

package pl.wavesoftware.log4j2;

import io.vavr.collection.Traversable;
import org.apache.logging.log4j.core.LogEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 1.0.0
 */
final class CollectorManagerImpl implements CollectorManager, Collector {
  static final Map<String, CollectorManager> MANAGERS = new HashMap<>();

  private final List<CollectedEvent> events = new ArrayList<>();

  @Override
  public Traversable<CollectedEvent> collected() {
    return io.vavr.collection.List.ofAll(events);
  }

  @Override
  public void collect(LogEvent event, String formattedMessage) {
    events.add(new CollectedEvent(event, formattedMessage));
  }

  @Override
  public void clear() {
    events.clear();
  }

}
