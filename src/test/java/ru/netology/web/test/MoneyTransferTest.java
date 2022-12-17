package ru.netology.web.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.getCardsNumberFirst;
import static ru.netology.web.data.DataHelper.getCardsNumberSecond;


public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var dashboardPage = new DashboardPage();
        var firstCard = getCardsNumberFirst();
        var secondCard = getCardsNumberSecond();
        int amountInput = 10000;
        var firstCardBalanceStart = dashboardPage.getFirstCardBalance() - amountInput;
        var secondCardBalanceStart = dashboardPage.getSecondCardBalance() + amountInput;
        var transferPage = dashboardPage.selectCardButton(secondCard.getCardId());
        dashboardPage = transferPage.cardReplenishment(Integer.parseInt(String.valueOf(amountInput)), String.valueOf(firstCard));
        var firstCardBalanceFinish = dashboardPage.getFirstCardBalance();
        var secondCardBalanceFinish = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalanceStart, firstCardBalanceFinish);
        Assertions.assertEquals(secondCardBalanceStart, secondCardBalanceFinish);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirst() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
        var dashboardPage = new DashboardPage();
        var firstCard = getCardsNumberFirst();
        var secondCard = getCardsNumberSecond();
        int amountInput = 500;
        var firstCardBalanceStart = dashboardPage.getFirstCardBalance() + amountInput;
        var secondCardBalanceStart = dashboardPage.getSecondCardBalance() - amountInput;
        var transferPage = dashboardPage.selectCardButton(firstCard.getCardId());
        dashboardPage = transferPage.cardReplenishment(Integer.parseInt(String.valueOf(amountInput)), String.valueOf(secondCard));
        var firstCardBalanceFinish = dashboardPage.getFirstCardBalance();
        var secondCardBalanceFinish = dashboardPage.getSecondCardBalance();
        Assertions.assertEquals(firstCardBalanceStart, firstCardBalanceFinish);
        Assertions.assertEquals(secondCardBalanceStart, secondCardBalanceFinish);
    }
}



