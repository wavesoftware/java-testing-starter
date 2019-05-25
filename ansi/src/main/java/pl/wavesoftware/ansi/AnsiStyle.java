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
 * {@link AnsiElement Ansi} styles.
 *
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @author Phillip Webb (Spring Boot project)
 * @since 1.0.0
 * @see org.springframework.boot.ansi.AnsiStyle
 */
@API(since = "1.0.0", status = API.Status.STABLE)
public enum AnsiStyle implements AnsiElement {

  NORMAL("0"),
  BOLD("1"),
  FAINT("2"),
  ITALIC("3"),
  UNDERLINE("4"),
  BLINK("5");

  private final String code;

  AnsiStyle(String code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return this.code;
  }

}
