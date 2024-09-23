package eu.phaf.wiremockfixtures;

import com.github.tomakehurst.wiremock.client.WireMock;
import eu.phaf.FileUtils;
import eu.phaf.WiremockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;

public final class ImageFixture {
    private ImageFixture() {
        // nothing here
    }

    public static void success() {
        WiremockServer.getWireMock().stubFor(get(urlPathMatching("/image"))
                .willReturn(WireMock.status(200)
                        .withBody(FileUtils.readFile("wiremock/responses/img.png"))
                        .withHeader("content-type", "image/jpeg"))
        );
    }
}
