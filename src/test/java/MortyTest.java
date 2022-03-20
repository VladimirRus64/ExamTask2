import Steps.ApiMorty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import Hooks.WebHooks;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({WebHooks.class})

public class MortyTest extends ApiMorty {
    @Tag("1api")
    @Test
    @DisplayName("Сравнение расы и местоположение 2х героев")
    public void test() {
        mortyInformation();
        lastEpisode();
        getLastCharacterNum();
        getLastCharacterInfo();
        assertions();
    }

}
