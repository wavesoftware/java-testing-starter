/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package pl.wavesoftware.ansi;

import org.apiguardian.api.API;

/**
 * An ANSI encodable element.
 *
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @author Phillip Webb (Spring Boot project)
 * @since 1.0.0
 * @see org.springframework.boot.ansi.AnsiElement
 */
@API(since = "1.0.0", status = API.Status.STABLE)
public interface AnsiElement {

  /**
   * @return the ANSI escape code
   */
  @Override
  String toString();

}
