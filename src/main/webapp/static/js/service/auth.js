//const BASE_URL = "/api/";
//const BASE_URL = "http://192.168.1.45:8080/api/";
const BASE_URL = "http://localhost:8080/api/";
const CREATE_USER_URL = BASE_URL + "users";
const LOGIN_USER_URL = BASE_URL + "auth/login";
const LOGOUT_USER_URL = BASE_URL + "auth/logout";
const RETRIEVE_USER_URL = BASE_URL + "auth";
const REFRESH_USER_SESSION_URL = BASE_URL + "auth/refresh";
const USER_URL = BASE_URL + "users";

const JSON_HEADERS = {
  Accept: "application/json",
  "Content-Type": "application/json",
};

export async function createUser(username, password) {
  try {
    return await $.ajax({
      url: CREATE_USER_URL,
      method: "POST",
      headers: JSON_HEADERS,
      data: JSON.stringify({ username, password }),
    });
  } catch ({ status }) {
    console.log("user create error: ", status);
    return null;
  }
}

export async function loginUser(username, password) {
  try {
    const resp = await fetch(LOGIN_USER_URL, {
      headers: JSON_HEADERS,
      method: "POST",
      body: JSON.stringify({ username, password }),
    });
    return await resp.json();
  } catch (error) {
    console.log("user login error: ", error);
    return null;
  }
}

export async function logoutUser() {
  try {
    return await $.ajax({
      url: LOGOUT_USER_URL,
      method: "GET",
    });
  } catch ({ status }) {
    console.log("user logout error: ", status);
    return null;
  }
}

export async function getUserSession() {
  try {
    return await $.ajax({
      url: RETRIEVE_USER_URL,
      method: "GET",
    });
  } catch (resp) {
    console.log("user retrieve error: ", resp.status);
    return { error: resp.status };
  }
}

export async function refreshUserSession() {
  try {
    await $.ajax({
      url: REFRESH_USER_SESSION_URL,
      method: "GET",
    });
    console.log("user session refreshed");
  } catch (error) {
    console.log("user refrresh error: ", error.status);
  }
}

export async function addMovieToUser(userId, movie) {
  const url = `${USER_URL}/${userId}/movies`;

  try {
    return await $.ajax({
      url,
      method: "POST",
      headers: JSON_HEADERS,
      data: JSON.stringify(movie),
    });
  } catch ({ status }) {
    console.log("add user movie error: ", status);
    return null;
  }
}

export async function removeMovieFromUser(userId, movieId) {
  const url = `${USER_URL}/${userId}/movies/${movieId}`;

  try {
    return await $.ajax({
      url,
      method: "DELETE",
    });
  } catch ({ status }) {
    console.log("remove user movie error: ", status);
    return null;
  }
}
