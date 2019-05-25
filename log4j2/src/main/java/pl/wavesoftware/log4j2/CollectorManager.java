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

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 1.0.0
 */
public interface CollectorManager {
  /**
   * Gets collector manager by it's name
   *
   * @param name a name of collector manager
   * @return a collector manager
   */
  static CollectorManager getCollectorManager(String name) {
    return CollectorManagerImpl.MANAGERS.computeIfAbsent(
      name, ignored -> new CollectorManagerImpl()
    );
  }

  /**
   * Gets traversable of all collected events
   *
   * @return a traversable of events
   */
  Traversable<CollectedEvent> collected();

  /**
   * Clears all collected events
   */
  void clear();
}
