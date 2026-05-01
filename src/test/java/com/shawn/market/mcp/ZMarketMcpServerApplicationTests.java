package com.shawn.market.mcp;

import static org.assertj.core.api.Assertions.assertThat;

import com.shawn.market.mcp.config.ToolConfig;
import com.shawn.market.mcp.tool.MarketRateTool;
import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class ZMarketMcpServerApplicationTests {

	@Test
	void registersMarketRateToolBean() {
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext()) {
			context.register(MarketRateTool.class, ToolConfig.class);
			context.refresh();

			assertThat(context.getBean(MarketRateTool.class)).isNotNull();
			assertThat(context.getBean(ToolCallbackProvider.class)).isNotNull();
		}
	}

	@Test
	void registersMarketRateToolCallback() {
		ToolCallbackProvider toolCallbackProvider = new ToolConfig()
				.marketToolCallbackProvider(new MarketRateTool());

		assertThat(toolCallbackProvider.getToolCallbacks())
			.extracting(ToolCallback::getToolDefinition)
			.anySatisfy(toolDefinition -> {
				assertThat(toolDefinition.name()).isEqualTo("getInterestRate");
				assertThat(toolDefinition.description())
					.isEqualTo("Get the current interest rate for a banking or wealth product");
				assertThat(toolDefinition.inputSchema()).contains("product");
			});
	}

}
