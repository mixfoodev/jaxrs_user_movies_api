import { fetchMovie } from "./service/movie.js";
import * as auth from "./service/auth.js";
import * as dom from "./view/dom.js";
import * as anim from "./view/animate.js";
import {
  createMovieContent,
  createMoviePoster,
  movieToFavoritesListItem,
} from "./view/movies.js";

let user = null;

$(document).ready(() => {
  addEventListeners();
  retrieveUser();
});

function dev() {
  fetch("./static/templates/movie.json")
    .then((res) => res.json())
    .then((m) => {
      const movies = [];
      const movie = {
        id: m["imdbID"],
        title: m["Title"],
        year: m["Year"],
        poster: m["Poster"],
      };
      console.log(movie);
      for (let index = 0; index < 19; index++) {
        movies.push(movie);
      }
      showUserMovies(movies);
    });
}

function addEventListeners() {
  $(".user-form").on("submit", userFormSubmit);
  $(".primary-action").on("click", dom.toggleUserForm);
  $(".logout").on("click", logout);
  $("#search").on("submit", searchMovie);
  $(".menu").on("click", dom.toggleSideBar);
  $(".container .content").on("click", () => {
    dom.hideUserForm();
    dom.hideSideBar();
  });
  $(".sidebar > .dark-overlay").on("click", dom.hideSideBar);
}

function userFormSubmit(e) {
  e.preventDefault();
  if (loggedIn() && !isAdmin()) return;

  const username = $("#usernameInput").val();
  const password = $("#passwordInput").val();
  if (username === "") return;
  if (password === "") return dom.showMessage("Password can not be empty!");

  if (isAdmin()) addUser(username, password);
  else login(username, password);
}

function checkUser() {
  $("#username").text(loggedIn() ? user?.username : "Visitor");
  if (!loggedIn()) return;

  dom.setupUserView(user, showUserMovies);

  if (isAdmin()) dom.setupAdminView();
  else $(".primary-action").hide();
}

async function retrieveUser() {
  const resp = await auth.getUserSession();
  if (!resp?.error) {
    user = resp;
    if (!user) dom.showMessage("Error. Could not get user session.");
  } else {
    if (resp.error === 401)
      dom.showMessage("You have been inactive for too long.. Please login!");
  }
  checkUser();
}

async function login(username, password) {
  anim.formLoadingAnimation();
  user = await auth.loginUser(username, password);
  anim.formLoadingAnimation(false);
  if (!user) dom.showMessage("Error. Could not login user.");
  if (user?.error) {
    dom.showMessage(user.error);
    user = null;
    return;
  }
  dom.hideUserForm();
  checkUser();
}

async function logout() {
  const resp = await auth.logoutUser();
  if (!resp) return dom.showMessage("Error. Could not logout user.");

  user = null;
  dom.setupVisitorView();
}

async function addUser(username, password) {
  anim.formLoadingAnimation();
  const resp = await auth.createUser(username, password);
  anim.formLoadingAnimation(false);
  if (!resp) return dom.showMessage("Some error occurred..");

  dom.hideUserForm();
  dom.showMessage("User added successfully!", true);
}

async function removeFromUserFavorites(movie) {
  const resp = await auth.removeMovieFromUser(user.id, movie.id);
  if (!resp) return dom.showMessage("Error. Could not remove movie from user.");

  console.log("removed from favorites ", movie);
  dom.showMessage("Movie removed successfully!", true);

  user.movies = user.movies.filter((m) => m.id != movie.id);
  addOrRemoveFromFavoritesView(movie, false);
  dom.changeFavButtonFlavor();
}

async function addToUserFavorites(movie) {
  const resp = await auth.addMovieToUser(user.id, movie);
  if (!resp) return dom.showMessage("Error. Could not add movie to user.");

  console.log("added to favorites ", movie);
  dom.showMessage("Movie added successfully!", true);

  user.movies.push(movie);
  addOrRemoveFromFavoritesView(movie);
  dom.changeFavButtonFlavor(false);
}

async function searchMovie(e) {
  e.preventDefault();

  const title = $("#movie").val();
  if (title === "") return;
  console.log(`Searching for '${title}' ..`);

  anim.searchButtonAnimation();
  const movie = await fetchMovie(title);
  anim.searchButtonAnimation(false);
  auth.refreshUserSession();

  if (!movie || movie["Error"])
    return dom.showMessage(`We could not find movie with title: '${title}'`);

  $("#movie").val("");
  showMovie(movie);
}

async function onFavoriteMovieSelect(movieId) {
  anim.favouriteLoadAnimation(movieId);
  const movie = await fetchMovie(movieId, true);
  anim.favouriteLoadAnimation(movieId, false);
  if (!movie) return dom.showMessage("Error. We could not reach server.");
  auth.refreshUserSession();

  showMovie(movie);
  dom.hideSideBar();
}

function showMovie(m) {
  const section = $(".movie");
  section.html("");

  const movieAlreadyInfavorites = $(`.favorites #${m["imdbID"]}`).length > 0;
  const poster = createMoviePoster(m);
  const content = createMovieContent(
    m,
    toggleFavorite,
    movieAlreadyInfavorites
  );

  section.append(poster).append(content);
}

function toggleFavorite(m) {
  if (!user) return dom.showMessage("You have to log in for this action!");

  const movie = {
    id: m["imdbID"],
    title: m["Title"],
    year: m["Year"],
    poster: m["Poster"],
  };

  const toBeRemovedFromFavs = user?.movies
    ?.map((m) => m.id)
    ?.includes(movie.id);

  if (toBeRemovedFromFavs) removeFromUserFavorites(movie);
  else addToUserFavorites(movie);
}

function showUserMovies(movies) {
  if (!movies || movies.length === 0) return;
  movies.forEach((m) => addOrRemoveFromFavoritesView(m));
}

function addOrRemoveFromFavoritesView(movie, add = true) {
  if (add)
    movieToFavoritesListItem(movie, onFavoriteMovieSelect).appendTo(
      $(".favorites ul")
    );
  else $(`.favorites ul > #${movie.id}`).remove();
}

function isAdmin() {
  return user && user?.admin === true;
}

function loggedIn() {
  return user !== null;
}
