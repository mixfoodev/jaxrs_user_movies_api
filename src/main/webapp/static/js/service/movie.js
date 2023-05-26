import { API_KEY } from "../../config.js";
const MOVIE_URL = "http://www.omdbapi.com/";

export async function fetchMovie(title, byId = false) {
  const selector = byId ? "i" : "t";
  const url = `${MOVIE_URL}?apikey=${API_KEY}&${selector}=${title}`;
  try {
    return await (await fetch(url)).json();
  } catch (error) {
    console.log("Movie fetch error:", error);
    return null;
  }
}
