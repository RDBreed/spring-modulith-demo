package eu.phaf;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.common.Slf4jNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class WiremockServer {
    static WireMockServer wireMock = new WireMockServer(new WireMockConfiguration()
            .port(WireMockConfiguration.DYNAMIC_PORT)
            .notifier(new ConsoleNotifier(false)));

    static {
        wireMock.start();
        System.setProperty("wiremock.server.port", String.valueOf(wireMock.port()));
        WireMock.configureFor(wireMock.port());
        Runtime.getRuntime().addShutdownHook(new Thread(wireMock::stop));
    }

    public static WireMockServer getWireMock() {
        return wireMock;
    }
}
