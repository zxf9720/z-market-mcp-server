package com.shawn.market.mcp.config;

import com.shawn.market.mcp.tool.MarketRateTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Registers Spring AI tools for MCP exposure.
 * ToolCallbackProvider is used to register tools that an LLM can call.
 * MethodToolCallbackProvider is a convenient way to expose Java methods as tools.
 */
@Configuration
public class ToolConfig {

    @Bean
    public ToolCallbackProvider marketToolCallbackProvider(MarketRateTool marketRateTool) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(marketRateTool)
                .build();
    }
}