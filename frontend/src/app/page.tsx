import {fetchUserInfo} from "@/lib/api";

export default async function Home() {
    const data = await fetchUserInfo();

    const mapUrl = `https://www.google.com/maps?q=${data.location.latitude},${data.location.longitude}&z=12&output=embed`;

    return (
        <main className="min-h-screen bg-gray-100 text-gray-900 dark:bg-gray-900 dark:text-gray-100">
            <div className="mx-auto max-w-4xl px-4 py-10">
                <h1 className="mb-8 text-3xl font-bold">
                    🌍 Local Dashboard
                </h1>

                {/* Location */}
                <section className="mb-6 rounded-lg bg-white p-6 shadow dark:bg-gray-800">
                    <h2 className="mb-2 text-xl font-semibold">📍 Location</h2>
                    <p className="text-lg font-medium">
                        {data.location.cityName}, {data.location.regionName}
                    </p>
                    <p className="text-gray-600 dark:text-gray-400">
                        {data.location.countryName}
                    </p>

                    <div className="mt-4 overflow-hidden rounded-md">
                        <iframe
                            src={mapUrl}
                            className="h-64 w-full border-0"
                            loading="lazy"
                            referrerPolicy="no-referrer-when-downgrade"
                        />
                    </div>
                </section>

                {/* Weather */}
                <section className="mb-6 rounded-lg bg-white p-6 shadow dark:bg-gray-800">
                    <h2 className="mb-2 text-xl font-semibold">☁️ Weather</h2>
                    <div className="flex items-center gap-4">
                        <span className="text-4xl font-bold text-blue-600 dark:text-blue-400">
                          {data.weather.temperature}°C
                        </span>
                                            <span className="text-gray-600 dark:text-gray-400 flex items-center gap-1">
                          {getWeatherEmoji(data.weather.weatherDescription)} {data.weather.weatherDescription}
                        </span>
                    </div>
                </section>


                {/* News */}
                <section className="rounded-lg bg-white p-6 shadow dark:bg-gray-800">
                    <h2 className="mb-4 text-xl font-semibold">📰 News</h2>
                    <ul className="divide-y divide-gray-200 dark:divide-gray-700">
                        {data.news.map((article, index) => (
                            <li key={index} className="py-4">
                                <h3 className="font-semibold">
                                    {article.title}
                                </h3>

                                <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">
                                    {article.sourceName} ·{" "}
                                    {new Date(article.publishedAt).toLocaleDateString()}
                                </p>

                                {article.url && (
                                    <a
                                        href={article.url}
                                        target="_blank"
                                        rel="noopener noreferrer"
                                        className="mt-2 inline-block text-sm font-medium text-blue-600 hover:underline dark:text-blue-400"
                                    >
                                        Read article →
                                    </a>
                                )}
                            </li>
                        ))}
                    </ul>
                </section>
            </div>
        </main>
    );
}

function getWeatherEmoji(description: string) {
    const desc = description.toLowerCase();

    if (desc.includes("clear")) return "☀️";
    if (desc.includes("cloud")) return "☁️";
    if (desc.includes("rain")) return "🌧️";
    if (desc.includes("thunderstorm")) return "⛈️";
    if (desc.includes("snow")) return "❄️";
    if (desc.includes("mist") || desc.includes("fog")) return "🌫️";
    if (desc.includes("drizzle")) return "💧";
    if (desc.includes("haze")) return "🌤️";
    return "🌡️"; // fallback
}
