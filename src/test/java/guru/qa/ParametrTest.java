package guru.qa;

import guru.qa.data.Locale;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class ParametrTest {

    @ValueSource(strings = {"Doctor Who", "The Mummy"})
    @ParameterizedTest(name = "Search {0}")
    void filmSearchTest(String testData){
        open("https://www.kinopoisk.ru/");
        $("input[name='kp_query']").setValue(testData);
        $("button[type='submit']").click();
        $x("//*[@id=\"block_left_pad\"]/div/div[2]/div/div[2]/span[1]")
                .shouldHave(text(testData));
    }

    @CsvSource({
            "Doctor Who, Доктор Кто",
            "The Mummy, Мумия"
                })
    @ParameterizedTest(name = "Search {0}")
    void filmSearchWithDiffResultTest(String testData, String expectedText){
        open("https://www.kinopoisk.ru/");
        $("input[name='kp_query']").setValue(testData);
        $("button[type='submit']").click();
        $x("//*[@id=\"block_left_pad\"]/div/div[2]/div/div[2]/p/a")
                .shouldHave(text(expectedText));
    }

    @EnumSource(Locale.class)
    @ParameterizedTest
    void siteButtonText(Locale locale){
        open("https://ru.selenide.org/");
        $$("#languages a").find(text(locale.name())).shouldBe(visible);
    }

    @Disabled
    @DisplayName("Search Doctor Who")
    @Test
    void filmSearchDoctorWhoTest(){
        open("https://www.kinopoisk.ru/");
        $("input[name='kp_query']").setValue("Doctor Who");
        $("button[type='submit']").click();
        $x("//*[@id=\"block_left_pad\"]/div/div[2]/div/div[2]/span[1]")
                .shouldHave(text("Doctor Who"));
    }
}
