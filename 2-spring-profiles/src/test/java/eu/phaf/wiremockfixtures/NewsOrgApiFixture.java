package eu.phaf.wiremockfixtures;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.havingExactly;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

public class NewsOrgApiFixture {
    private NewsOrgApiFixture() {
        // unused
    }

    public static void success(int wireMockPort, String newsApiKey, String countryCode) {
        stubFor(stubGetTopHeadlines200(newsApiKey, countryCode,
                new StringSubstitutor(Map.of("port", wireMockPort))
                        .replace(
                                getSource()
                        )
        ));
    }

    public static void successAnyCountry(String newsApiKey) {
        stubFor(stubGetTopHeadlines200(newsApiKey, null,
                """
                               {
                        "status": "ok",
                        "totalResults": 36,
                        "articles": [
                        {
                          "source": {
                            "id": "fake",
                            "name": "FAKE news"
                          },
                          "author": "Nothing",
                          "title": "This article is just empty",
                          "description": null,
                          "url": null,
                          "urlToImage": null,
                          "publishedAt": "2024-04-13T12:21:00Z",
                          "content": null
                        }
                        ]
                          }
                              """
        ));
    }

    private static MappingBuilder stubGetTopHeadlines200(@jakarta.annotation.Nonnull String apiKey, @jakarta.annotation.Nullable String country, String response) {
        MappingBuilder stub = get(urlPathEqualTo("/top-headlines"))
                .withHeader("Accept", havingExactly("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(response)
                );

        if (country != null) {
            stub = stub.withQueryParam("country", equalTo(country));
        }
        stub = stub.withQueryParam("apiKey", equalTo(apiKey));

        return stub;
    }

    private static String getSource() {
        return """
                {
                  "status": "ok",
                  "totalResults": 36,
                  "articles": [
                    {
                      "source": {
                        "id": "usa-today",
                        "name": "USA Today"
                      },
                      "author": "USA TODAY",
                      "title": "Roman art showing Helen of Troy discovered in Pompeii 2000 years after volcanic eruption - USA TODAY",
                      "description": null,
                      "url": "https://www.usatoday.com/story/news/world/2024/04/11/pompeii-discovery-roman-frescoes-paintings/73296713007/",
                      "urlToImage": null,
                      "publishedAt": "2024-04-13T12:21:00Z",
                      "content": null
                    },
                    {
                      "source": {
                        "id": "al-jazeera-english",
                        "name": "Al Jazeera English"
                      },
                      "author": "Maziar Motamedi",
                      "title": "Iran’s IRGC seizes ‘Israeli-linked’ ship near Strait of Hormuz - Al Jazeera English",
                      "description": "IRGC forces conducted an operation via helicopter to take control of the commercial vessel, state media reports.",
                      "url": "https://www.aljazeera.com/news/2024/4/13/irans-irgc-seizes-israeli-linked-ship-near-strait-of-hormuz",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T11:35:44Z",
                      "content": "Tehran, Iran Iranian armed forces have seized a container ship near the Strait of Hormuz amid rising tensions across the region after a deadly Israeli attack on Irans consulate in Syria.\\r\\nThe ship wa… [+3711 chars]"
                    },
                    {
                      "source": {
                        "id": "cbs-news",
                        "name": "CBS News"
                      },
                      "author": null,
                      "title": "6 dead, suspect killed after stabbing attack at shopping center in Sydney, Australia; multiple people injured - CBS News",
                      "description": "New South Wales Assistant Police Commissioner Anthony Cooke said that he believed that the suspect acted alone.",
                      "url": "https://www.cbsnews.com/news/sydney-australia-stabbing-attack-5-dead/",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T11:31:39Z",
                      "content": "SYDNEY (AP) — A man stabbed six people to death at a busy Sydney shopping center Saturday before he was fatally shot, police said. Eight people, including a 9-month-old, were injured in the attack.\\r\\n… [+1876 chars]"
                    },
                    {
                      "source": {
                        "id": null,
                        "name": "CNBC"
                      },
                      "author": "Jeff Cox",
                      "title": "Surging inflation fears sent markets tumbling and Fed officials scrambling - CNBC",
                      "description": "The early data is in for the path of inflation during the first three months of 2024, and the news so far is not good.",
                      "url": "https://www.cnbc.com/2024/04/13/surging-inflation-fears-sent-markets-tumbling-and-fed-officials-scrambling.html",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T11:23:16Z",
                      "content": "A sign advertising units for rent is displayed outside of a Manhattan building on April 11, 2024 in New York City.\\r\\nThe early data is in for the path of inflation during the first three months of 202… [+7490 chars]"
                    },
                    {
                      "source": {
                        "id": "politico",
                        "name": "Politico"
                      },
                      "author": "POLITICO",
                      "title": "The New Civil War Movie Is Eerily Right About How the Country Could Split Apart - POLITICO",
                      "description": null,
                      "url": "https://www.politico.com/news/magazine/2024/04/13/yes-the-new-civil-war-movie-is-terrifying-but-how-real-is-it-00152064",
                      "urlToImage": null,
                      "publishedAt": "2024-04-13T11:00:00Z",
                      "content": null
                    },
                    {
                      "source": {
                        "id": null,
                        "name": "CNBC"
                      },
                      "author": "Holly Ellyatt",
                      "title": "Situation on eastern front has 'significantly worsened' in recent days, Ukraine's army chief warns - CNBC",
                      "description": "Ukraine's top general warned that the battlefield situation in the east of the country, which is at the epicenter of the fiercest fighting, has deteriorated.",
                      "url": "https://www.cnbc.com/2024/04/13/situation-in-east-ukraine-has-significantly-worsened-army-commander.html",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T10:25:45Z",
                      "content": "Ukraine's top military general warned Saturday that the battlefield situation in the east of the country, which continues to be the epicenter of the fiercest fighting in Ukraine, has deteriorated sha… [+2955 chars]"
                    },
                    {
                      "source": {
                        "id": null,
                        "name": "Rolling Stone"
                      },
                      "author": "Tomás Mier, Suzy Exposito",
                      "title": "Lana Del Rey Cements Herself as a Cult Icon at Coachella - Rolling Stone",
                      "description": "Lana Del Rey closed Friday night at Coachella with a career-spanning celebration in the desert, featuring Jon Batiste, Billie Eilish and Jack Antonoff",
                      "url": "http://www.rollingstone.com/music/music-news/lana-del-rey-performs-coachella-2024-1235002460/",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T09:18:49Z",
                      "content": "Lana Del Rey brought her ethereal Venus energy to the Coachella main stage as she closed out Friday night at the festival. This marked her first Coachella performance since 2014.\\r\\nSeated on the back … [+3559 chars]"
                    },
                    {
                      "source": {
                        "id": "axios",
                        "name": "Axios"
                      },
                      "author": "Axios",
                      "title": "Trump says he stands with Johnson as Greene threatens ouster - Axios",
                      "description": null,
                      "url": "https://www.axios.com/2024/04/12/trump-mike-johnson-marjorie-taylor-greene-mar-a-lago",
                      "urlToImage": null,
                      "publishedAt": "2024-04-13T08:34:53Z",
                      "content": null
                    },
                    {
                      "source": {
                        "id": null,
                        "name": "BBC News"
                      },
                      "author": null,
                      "title": "'No easy task' - The hunt for an impartial Trump jury in Manhattan - BBC.com",
                      "description": "Prosecutors and Mr Trump's team will weed through hundreds of people in Manhattan as part of the hush money trial.",
                      "url": "https://www.bbc.com/news/world-us-canada-68671730",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T08:13:54Z",
                      "content": null
                    },
                    {
                      "source": {
                        "id": null,
                        "name": "New York Post"
                      },
                      "author": "Fox Business",
                      "title": "US drug shortages hit all-time high, pharmacists warn - New York Post ",
                      "description": "Data shows that there are 323 active drug shortages.",
                      "url": "https://nypost.com/2024/04/13/lifestyle/us-drug-shortages-hit-all-time-high-pharmacists-warn/",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T07:03:00Z",
                      "content": "Drug shortages in the US are at an all-time high, and some of the medications in short supply are life-saving chemotherapy drugs and emergency medications stored in hospitals, pharmacists warn. \\r\\nThe… [+2348 chars]"
                    },
                    {
                      "source": {
                        "id": null,
                        "name": "Nbcsportsbayarea.com"
                      },
                      "author": "Angelina Martin",
                      "title": "Warriors, Kings' NBA play-in picture entering regular-season finales - Yahoo Sports",
                      "description": "If the 2023-24 NBA season ended Friday, here's how the Western Conference's NBA Play-In Tournament bracket would look.",
                      "url": "https://www.nbcsportsbayarea.com/nba/golden-state-warriors/play-in-picture-western-conference-standings-bracket/1723951/?partner=yahoo",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T05:33:01Z",
                      "content": "Warriors, Kings' NBA play-in picture entering regular-season finales originally appeared on NBC Sports Bay Area\\r\\nAnother day of NBA basketball came and went Friday, and both the Warriors and Kings lo… [+1708 chars]"
                    },
                    {
                      "source": {
                        "id": null,
                        "name": "MMA Fighting"
                      },
                      "author": "Bryan Tucker",
                      "title": "UFC 300 Results: Pereira vs. Hill - MMA Fighting",
                      "description": "Get UFC 300 results for the Pereira vs. Hill fight card Saturday in Las Vegas.",
                      "url": "https://www.mmafighting.com/2024/4/13/24127738/ufc-300-results-pereira-vs-hill",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T04:00:00Z",
                      "content": "MMA Fighting has UFC 300 results for the Pereira vs. Hill event, live blogs for the entire fight card, and more from the T-Mobile Arena in Las Vegas, Nev., on Saturday night.\\r\\nIn the main event, UFC … [+935 chars]"
                    },
                    {
                      "source": {
                        "id": null,
                        "name": "Www.geo.tv"
                      },
                      "author": "Web Desk",
                      "title": "People fear going blind after witnessing Total Solar Eclipse without eye protection - Geo News",
                      "description": "It hasn’t been long since the day when some lucky Earthlings witnessed this year’s Total Solar Eclipse but doctors, in areas that fell in the path of totality, have reported a surge in cases of...",
                      "url": "https://www.geo.tv/latest/538866-people-fear-going-blind-after-witnessing-total-solar-eclipse-without-eye-protection",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T03:51:00Z",
                      "content": "Doctors see surge in eye-related injuries days after Total Solar Eclipse. \\r\\nIt hasnt been long since the day when some lucky Earthlings witnessed this years Total Solar Eclipse but doctors, in areas … [+1848 chars]"
                    },
                    {
                      "source": {
                        "id": "cnn",
                        "name": "CNN"
                      },
                      "author": "Natasha Bertrand, MJ Lee, Kevin Liptak, Samantha Waldenberg",
                      "title": "US expects Iran to carry out direct attack on Israel, sources say, as Biden warns ‘don’t’ - CNN",
                      "description": "The US expects that Iran will carry out strikes against multiple targets inside Israel in the coming days and is prepared to help intercept any weapons launched at its ally, sources tell CNN, as the Biden administration is on high alert for what could be the …",
                      "url": "https://www.cnn.com/2024/04/12/politics/white-house-iran-threat-israel/index.html",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T03:37:00Z",
                      "content": "The US expects that Iran will carry out strikes against multiple targets inside Israel in the coming days and is prepared to help intercept any weapons launched at its ally, sources tell CNN, as the … [+9544 chars]"
                    },
                    {
                      "source": {
                        "id": null,
                        "name": "DW (English)"
                      },
                      "author": "Deutsche Welle",
                      "title": "Argentina's Milei meets Elon Musk at Tesla factory - DW (English)",
                      "description": "The two like-minded men bonded over free markets and the need to defend liberty. Argentina, which is undergoing economic reforms, is home to lithium deposits required for rechargeable batteries like those in Teslas.",
                      "url": "https://www.dw.com/en/argentinas-milei-meets-elon-musk-at-tesla-factory/a-68809179",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T03:33:14Z",
                      "content": "Argentine President Javier Milei met tech billionaire Elon Musk on Friday at a Tesla plant in Austin, Texas, where they agreed on the \\"need to free markets.\\"\\r\\nMusk shared an image of the two on his m… [+2332 chars]"
                    },
                    {
                      "source": {
                        "id": null,
                        "name": "Daily Beast"
                      },
                      "author": "Noah Kirsch",
                      "title": "Truth Social Investors Try to Keep Hope Alive as Stock Tanks - The Daily Beast",
                      "description": "But some are holding out hope that Donald Trump’s hyping of the company means better days are ahead.",
                      "url": "https://www.thedailybeast.com/truth-social-investors-try-to-keep-hope-alive-as-stock-tanks",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T03:28:00Z",
                      "content": "Donald Trumps acolytes gathered at Mar-a-Lago on Wednesday evening to celebrate the public listing of his social media firm, even as the companys stock continued to crater. Under the Palm Beach sky, … [+3581 chars]"
                    },
                    {
                      "source": {
                        "id": null,
                        "name": "CBS Sports"
                      },
                      "author": null,
                      "title": "Arizona Coyotes players informed NHL franchise will be moving to Salt Lake City, per reports - CBS Sports",
                      "description": "Salt Lake City will be home to the relocated Coyotes in 2024-25 and play in a renovated Delta Center",
                      "url": "https://www.cbssports.com/nhl/news/arizona-coyotes-players-informed-nhl-franchise-will-be-moving-to-salt-lake-city-per-reports/",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T03:13:59Z",
                      "content": "Arizona Coyotes GM Bill Armstrong told players Friday that the NHL franchise will be relocating to Salt Lake City next season, according to multiple reports.\\r\\nThe Coyotes currently play home games in… [+595 chars]"
                    },
                    {
                      "source": {
                        "id": null,
                        "name": "New York Post"
                      },
                      "author": "Mark Cannizzaro",
                      "title": "Tiger Woods being 'right there' after making record Masters cut is something to celebrate - New York Post ",
                      "description": "Even if he won’t come out and say so, you can bet making his 24th consecutive Masters cut is an accomplishment that Tiger Woods appreciates.",
                      "url": "https://nypost.com/2024/04/12/sports/tiger-woods-making-record-masters-cut-is-something-to-celebrate/",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T02:54:00Z",
                      "content": "AUGUSTA, Ga. If, just a few years ago, you asked Tiger Woods a question about what an accomplishment it would be to make the cut at the Masters, youd be doing so at your own risk.\\r\\nWoods patented the… [+4694 chars]"
                    },
                    {
                      "source": {
                        "id": "abc-news",
                        "name": "ABC News"
                      },
                      "author": "JENNIFER SINCO KELLEHER Associated Press",
                      "title": "Heavy rain across Kauai prompts rescues from floodwater - ABC News",
                      "description": "Several people on the Hawaiian island of Kauai needed to be rescued from floodwaters during heavy rain, but there were no immediate reports of injuries",
                      "url": "https://abcnews.go.com/US/wireStory/heavy-rain-kauai-prompts-rescues-floodwater-immediate-reports-109184908",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T02:16:05Z",
                      "content": "HONOLULU -- Several people on the Hawaiian island of Kauai needed to be rescued from floodwaters during heavy rain, authorities said Friday, but there were no immediate reports of injuries. \\r\\nHeavy r… [+1956 chars]"
                    },
                    {
                      "source": {
                        "id": "the-verge",
                        "name": "The Verge"
                      },
                      "author": "Amrita Khalid",
                      "title": "Galaxy AI features are coming to last-gen Samsung phones — including the S21 series - The Verge",
                      "description": "Samsung’s flagship phones from 2022 will receive a special version of Galaxy AI in early May. Circle to Search and Magic Rewrite are also coming to select 2021 models.",
                      "url": "https://www.theverge.com/2024/4/12/24128914/samsung-galaxy-ai-features-s21-s22-series-phones-update",
                      "urlToImage": "http://localhost:${port}/image",
                      "publishedAt": "2024-04-13T01:18:00Z",
                      "content": "Galaxy AI features are coming to last-gen Samsung phones including the S21 series\\r\\nGalaxy AI features are coming to last-gen Samsung phones including the S21 series\\r\\n / Instant Slow-Mo will remain un… [+1343 chars]"
                    }
                  ]
                }
                """;
    }
}
