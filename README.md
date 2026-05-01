# z-market-mcp-server

## Overview

z-market-mcp-server is a Spring Boot–based MCP (Model Context Protocol) server that exposes market-related tools to a GenAI system.

It acts as an external tool provider for the main application (z-wealth-knowledge-rag), allowing the LLM to call structured tools in a decoupled and scalable way.

This service registers a single MCP tool, `getInterestRate`, which returns sample banking and wealth product rates for supported product names:

| Product match | Response |
| --- | --- |
| `tfsa` | `TFSA promotional interest rate is 4.50%.` |
| `rrsp` | `RRSP savings interest rate is 4.10%.` |
| `mortgage` | `Mortgage rate starts from 5.25%.` |
| `loan` | `Personal loan interest rate starts from 6.20%.` |
| blank, null, or unknown | `General interest rate is 5.00%.` |

The product matching is case-insensitive and checks whether the supplied text contains one of the supported product keywords.

---

## Purpose

- Provide external tool capabilities to LLM
- Decouple tool logic from the main RAG system
- Demonstrate MCP-based tool integration
- Simulate real-world financial service tools

---

## Architecture

```text
z-wealth-knowledge-rag (Agent A)
        |
        | MCP Client
        v
z-market-mcp-server
        |
        v
Market Tools

## Tech Stack

- Java 21
- Spring Boot 3.5.14
- Spring AI 1.0.5
- Spring AI MCP Server WebMVC starter
- JUnit 5 and AssertJ
- JaCoCo test coverage reporting

## Project Structure

```text
src/main/java/com/shawn/market/mcp/
  ZMarketMcpServerApplication.java    Spring Boot entry point
  config/ToolConfig.java              Registers Java methods as MCP tools
  tool/MarketRateTool.java            Market rate lookup tool

src/main/resources/
  application.yaml                    Application and MCP server configuration

src/test/java/com/shawn/market/mcp/
  ZMarketMcpServerApplicationTests.java
  tool/MarketRateToolTest.java
```

## Configuration

The application is configured in `src/main/resources/application.yaml`.

```yaml
spring:
  application:
    name: z-market-mcp-server
  ai:
    mcp:
      server:
        name: z-market-mcp-server
        version: 1.0.0

server:
  port: 8084
```

By default, the HTTP server starts on port `8084`.

## Run

Start the server:

```bash
mvn spring-boot:run
```

Build the JAR:

```bash
mvn package
```

Run the packaged application:

```bash
java -jar target/z-market-mcp-server-0.0.1-SNAPSHOT.jar
```

## Test

Run the test suite:

```bash
mvn test
```

Run verification, including the configured JaCoCo coverage check:

```bash
mvn verify
```

Generate the JaCoCo report:

```bash
mvn test
```

Then open:

```text
target/site/jacoco/index.html
```

## Tool Registration

`MarketRateTool` exposes its method with Spring AI annotations: 

```java
@Tool(description = "Get the current interest rate for a banking or wealth product")
public String getInterestRate(@ToolParam(description = "Product name, for example TFSA, RRSP, mortgage, or loan") String product);
```

`ToolConfig` registers the tool object with `MethodToolCallbackProvider`, making it available to the MCP server.

## Current Tests

The test suite covers:

- Product-specific rate responses
- Case-insensitive product matching
- Null, empty, blank, and unknown product fallback behavior
- Spring bean registration for the market rate tool
- Spring AI tool callback metadata for `getInterestRate`
