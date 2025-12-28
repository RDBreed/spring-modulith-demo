import {ApiResponse} from "@/types/dashboard";

export async function fetchUserInfo(ip: string): Promise<ApiResponse> {
    const res = await fetch(`http://localhost:8080/api/user/info?ip=${ip}`, {
        cache: "no-store",
    });

    if (!res.ok) {
        throw new Error("Failed to fetch user info");
    }

    return res.json();
}
