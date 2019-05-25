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

import javax.annotation.Nullable;
import java.util.Locale;

import static pl.wavesoftware.eid.utils.EidPreconditions.checkNotNull;

/**
 * Generates ANSI encoded output, automatically attempting to detect if the
 * terminal supports ANSI.
 *
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @author Phillip Webb (Spring Boot project)
 * @since 1.0.0
 * @see org.springframework.boot.ansi.AnsiOutput
 */
@API(since = "1.0.0", status = API.Status.STABLE)
public final class AnsiOutput {

  private static Enabled enabled = Enabled.DETECT;
  @Nullable
  private static Boolean consoleAvailable = true;
  @Nullable
  private static Boolean ansiCapable;
  private static boolean skipWindowsCheck = true;

  private static final String ENCODE_JOIN = ";";
  private static final String ENCODE_START = "\033[";
  private static final String ENCODE_END = "m";
  private static final String RESET = "0;" + AnsiColor.DEFAULT;

  private static final String OPERATING_SYSTEM_NAME = System
    .getProperty("os.name")
    .toLowerCase(Locale.ENGLISH);

  private AnsiOutput() {
    // nothing here
  }

  /**
   * Sets if ANSI output should be skipped on windows os
   *
   * @param skipWindowsCheck true, if output should be skipped on windows
   */
  public static void setSkipWindowsCheck(boolean skipWindowsCheck) {
    AnsiOutput.skipWindowsCheck = skipWindowsCheck;
  }

  /**
   * Sets if ANSI output is enabled.
   *
   * @param enabled if ANSI is enabled, disabled or detected
   */
  public static void setEnabled(Enabled enabled) {
    checkNotNull(enabled, "20181220:155934", "Enabled must not be null");
    AnsiOutput.enabled = enabled;
  }

  /**
   * Sets if the System.console() is known to be available.
   *
   * @param consoleAvailable if the console is known to be available or
   *                         {@code null} to use standard detection logic.
   */
  public static void setConsoleAvailable(@Nullable Boolean consoleAvailable) {
    AnsiOutput.consoleAvailable = consoleAvailable;
  }

  /**
   * Create a new ANSI string from the specified elements. Any
   * {@link AnsiElement}s will be encoded as required.
   *
   * @param elements the elements to encode
   * @return a string of the encoded elements
   */
  public static String encode(Object... elements) {
    StringBuilder sb = new StringBuilder();
    if (isEnabled()) {
      buildEnabled(sb, elements);
    } else {
      buildDisabled(sb, elements);
    }
    return sb.toString();
  }

  private static void buildEnabled(StringBuilder sb, Object[] elements) {
    boolean writingAnsi = false;
    boolean containsEncoding = false;
    for (Object element : elements) {
      if (element instanceof AnsiElement) {
        containsEncoding = true;
        if (!writingAnsi) {
          sb.append(ENCODE_START);
          writingAnsi = true;
        } else {
          sb.append(ENCODE_JOIN);
        }
      } else {
        if (writingAnsi) {
          sb.append(ENCODE_END);
          writingAnsi = false;
        }
      }
      sb.append(element);
    }
    if (containsEncoding) {
      sb.append(writingAnsi ? ENCODE_JOIN : ENCODE_START);
      sb.append(RESET);
      sb.append(ENCODE_END);
    }
  }

  private static void buildDisabled(StringBuilder sb, Object[] elements) {
    for (Object element : elements) {
      if (!(element instanceof AnsiElement) && element != null) {
        sb.append(element);
      }
    }
  }

  private static boolean isEnabled() {
    if (enabled == Enabled.DETECT) {
      if (ansiCapable == null) {
        ansiCapable = detectIfAnsiCapable();
      }
      return ansiCapable;
    }
    return enabled == Enabled.ALWAYS;
  }

  private static boolean detectIfAnsiCapable() {
    if (Boolean.FALSE.equals(consoleAvailable)
      || ((consoleAvailable == null) && (System.console() == null))) {
      return false;
    }
    return skipWindowsCheck || !(OPERATING_SYSTEM_NAME.contains("win"));
  }

  /**
   * Possible values to pass to {@link AnsiOutput#setEnabled}. Determines when
   * to output ANSI escape sequences for coloring application output.
   */
  public enum Enabled {

    /**
     * Try to detect whether ANSI coloring capabilities are available. The
     * default value for {@link AnsiOutput}.
     */
    DETECT,

    /**
     * Enable ANSI-colored output.
     */
    ALWAYS,

    /**
     * Disable ANSI-colored output.
     */
    NEVER
  }
}
