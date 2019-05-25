package pl.wavesoftware.ansi;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import pl.wavesoftware.ansi.AnsiOutput.Enabled;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 1.0.0
 */
class AnsiOutputTest {
  @BeforeEach
  @AfterEach
  void reset() throws NoSuchFieldException, IllegalAccessException {
    AnsiOutput.setEnabled(Enabled.DETECT);
    AnsiOutput.setConsoleAvailable(true);
    AnsiOutput.setSkipWindowsCheck(true);
    Field field = AnsiOutput.class.getDeclaredField("ansiCapable");
    field.setAccessible(true);
    field.set(null, null);
  }

  @Test
  void always() {
    // given
    AnsiOutput.setEnabled(Enabled.ALWAYS);
    String text = "Colorful";

    // when
    String encoded = AnsiOutput.encode(
      AnsiStyle.ITALIC,
      AnsiColor.BLACK,
      AnsiBackground.MAGENTA,
      text
    );

    // then
    assertThat(encoded).isEqualTo("\u001B[3;30;45mColorful\u001B[0;39m");
  }

  @ParameterizedTest
  @ArgumentsSource(EncodeArgumentSource.class)
  void encode(Seq<Object> styling, String expectedResult) {
    // given
    String text = "Colorful";
    Object[] toBeEncoded = styling.append(text).toJavaArray();

    // when
    String encoded = AnsiOutput.encode(toBeEncoded);

    // then
    assertThat(encoded).isEqualTo(expectedResult);
  }

  @ParameterizedTest
  @ArgumentsSource(EncodeArgumentSource.class)
  void encodeOnWindows(Seq<Object> styling) {
    // given
    AnsiOutput.setSkipWindowsCheck(false);
    AnsiOutput.setConsoleAvailable(null);
    String text = "Non-Color";
    Object[] toBeEncoded = styling.append(text).toJavaArray();

    // when
    String encoded = AnsiOutput.encode(toBeEncoded);

    // then
    assertThat(encoded).isEqualTo(text);
  }

  private static final class EncodeArgumentSource implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
      return Stream.of(
        arguments(List.of(AnsiColor.BLUE), "\u001B[34mColorful\u001B[0;39m"),
        arguments(
          List.of(AnsiColor.BRIGHT_YELLOW, AnsiStyle.BLINK),
          "\u001B[93;5mColorful\u001B[0;39m"
        ),
        arguments(
          List.of(AnsiComposite.of(
            AnsiStyle.BOLD,
            AnsiColor.BLACK,
            AnsiBackground.BRIGHT_YELLOW
          )),
          "\u001B[1;30;103mColorful\u001B[0;39m"
        )
      );
    }
  }
}
