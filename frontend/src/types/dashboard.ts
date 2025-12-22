export interface ApiResponse {
    location: Location;
    weather: Weather;
    news: NewsArticle[];
}

export interface Location {
    countryCode: string;
    countryName: string;
    regionName: string;
    cityName: string;
    latitude: number;
    longitude: number;
}

export interface Weather {
    temperature: number;
    weatherDescription: string;
}

export interface NewsArticle {
    sourceName: string;
    author: string;
    title: string;
    description: string | null;
    url: string | null;
    image: string;
    publishedAt: string;
}
