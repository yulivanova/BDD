package ru.netology.web.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.getCardsNumberSecond;


public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyFromTwoToOne() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var dashboardPage = new DashboardPage();
        var firstCardBalanceStart = dashboardPage.getFirstCardBalance();
        var secondCardBalanceStart = dashboardPage.getSecondCardBalance();
        var amountInput = 10000;
        var firstCard = DataHelper.getCardsNumberFirst();
        var transferPage = dashboardPage.selectCardButton(firstCard.getCardId());
        dashboardPage = transferPage.cardReplenishment(amountInput, getCardsNumberSecond().getNumber());
        var firstCardBalanceFinish = dashboardPage.getFirstCardBalance();
        var secondCardBalanceFinish = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalanceStart + amountInput, firstCardBalanceFinish);
        Assertions.assertEquals(secondCardBalanceStart - amountInput, secondCardBalanceFinish);
    }
}



