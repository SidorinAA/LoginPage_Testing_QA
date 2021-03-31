package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;


import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class AccountPage {

    private SelenideElement titleAccount = $("h2[data-test-id=\"dashboard\"]");
    private SelenideElement titleError = $(withText("Ошибка"));


    public void checkIfVisible() {
        titleAccount.shouldBe(Condition.visible);
    }

    public void checkError() {
        titleError.shouldBe(Condition.visible);
    }


}
