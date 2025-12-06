package eu.phaf;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class WiremockServer implements BeforeAllCallback, AfterAllCallback {

    private static WireMockServer wireMockServer;

    public static WireMockServer getWireMockServer() {
        WireMockServer wireMock = new WireMockServer(new WireMockConfiguration()
                .port(WireMockConfiguration.DYNAMIC_PORT)
                .notifier(new ConsoleNotifier(false)));
        wireMock.start();
        System.setProperty("wiremock.server.port", String.valueOf(wireMock.port()));
        WireMock.configureFor(wireMock.port());
        return wireMock;
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        wireMockServer = getWireMockServer();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        wireMockServer.stop();
    }

    public static WireMockServer wireMockServer(){
        return wireMockServer;
    }
}
