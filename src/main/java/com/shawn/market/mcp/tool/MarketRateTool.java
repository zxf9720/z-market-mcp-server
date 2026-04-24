package com.shawn.market.mcp.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

/**
 * MCP tool for market rate lookup.
 */
@Component
public class MarketRateTool {

    @Tool(description = "Get the current interest rate for a banking or wealth product")
    public String getInterestRate(
            @ToolParam(description = "Product name, for example TFSA, RRSP, mortgage, or loan")
            String product
    ) {
        if (product == null || product.isBlank()) {
            return "General interest rate is 5.00%.";
        }

        String normalized = product.toLowerCase();

        if (normalized.contains("tfsa")) {
            return "TFSA promotional interest rate is 4.50%.";
        }

        if (normalized.contains("rrsp")) {
            return "RRSP savings interest rate is 4.10%.";
        }

        if (normalized.contains("mortgage")) {
            return "Mortgage rate starts from 5.25%.";
        }

        if (normalized.contains("loan")) {
            return "Personal loan interest rate starts from 6.20%.";
        }

        return "General interest rate is 5.00%.";
    }
}