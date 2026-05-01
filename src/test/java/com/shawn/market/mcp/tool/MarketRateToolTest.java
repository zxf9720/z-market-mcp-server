package com.shawn.market.mcp.tool;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class MarketRateToolTest {

    private final MarketRateTool marketRateTool = new MarketRateTool();

    @ParameterizedTest
    @CsvSource({
            "TFSA, TFSA promotional interest rate is 4.50%.",
            "high interest tfsa savings, TFSA promotional interest rate is 4.50%.",
            "RRSP, RRSP savings interest rate is 4.10%.",
            "fixed mortgage, Mortgage rate starts from 5.25%.",
            "personal loan, Personal loan interest rate starts from 6.20%."
    })
    void getInterestRateReturnsProductSpecificRate(String product, String expectedRate) {
        assertThat(marketRateTool.getInterestRate(product)).isEqualTo(expectedRate);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void getInterestRateReturnsGeneralRateForMissingProduct(String product) {
        assertThat(marketRateTool.getInterestRate(product)).isEqualTo("General interest rate is 5.00%.");
    }

    @Test
    void getInterestRateReturnsGeneralRateForUnknownProduct() {
        assertThat(marketRateTool.getInterestRate("chequing account")).isEqualTo("General interest rate is 5.00%.");
    }

    @Test
    void getInterestRateMatchesProductNamesCaseInsensitively() {
        assertThat(marketRateTool.getInterestRate("TfSa account"))
                .isEqualTo("TFSA promotional interest rate is 4.50%.");
    }
}
