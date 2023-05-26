const userForm = $(".user-form");
const usernameInput = $("#usernameInput");
const passwordInput = $("#passwordInput");

let dialogTimeOut;

export function hideUserForm() {
  if (isUserFormVisible()) toggleUserForm();
}

export function hideSideBar() {
  if (isMobile() && isMenuOpen()) toggleSideBar();
}

export function toggleSideBar() {
  $("aside").toggleClass("menu-open");
  $(".dark-overlay").toggle(0);

  enableBodyScroll(!isMenuOpen());
}

export function toggleUserForm() {
  userForm.toggle(() => {
    if (isUserFormVisible()) {
      if (isMobile) hideSideBar();
      usernameInput.focus();
    } else clearUserForm();
  });
}

export function changeFavButtonFlavor(add = true) {
  if (add) {
    $("#btn-favorites")
      .removeClass("fav-remove")
      .addClass("fav-add")
      .text("Add to favorites");
  } else {
    $("#btn-favorites")
      .removeClass("fav-add")
      .addClass("fav-remove")
      .text("Remove");
  }
}

export function setupUserView(user, showUserMovies) {
  $(".logout").show();
  showUserMovies(user?.movies);

  // if a movie was searched before login
  // check if that movie is in user favorites
  if (user?.movies?.length > 0 && $(".movie").html() !== "") {
    const id = $(".movie h1").attr("id");

    if (user.movies.map((m) => m.id).includes(id)) {
      changeFavButtonFlavor(false);
    }
  }
}

export function setupAdminView() {
  $(".user-form button span").text("Add user");
  $(".user-form button i").removeClass("fa-key").addClass("fa-plus");
  $(".primary-action").text("Add user");
}

export function setupVisitorView() {
  $(".primary-action").show().text("Login");
  $(".user-form button span").text("Login");
  $(".user-form button i").removeClass("fa-plus").addClass("fa-key");
  $("#username").text("Visitor");
  $(".logout").hide();
  $(".favorites ul").html("");

  // if a movie was present and user log out
  // change favorite button's flavor
  if ($("#btn-favorites")?.hasClass("fav-remove")) {
    changeFavButtonFlavor();
  }
}

export function showMessage(msg, success = false) {
  const div = $(".dialog").text(msg);
  const color = success ? "green" : "#b30505";
  div.css("background-color", color);

  clearTimeout(dialogTimeOut);

  div.hide().fadeIn(500, "linear");
  div.on("click", () => div.fadeOut(200));

  dialogTimeOut = setTimeout(() => {
    div.fadeOut(500);
  }, 2000);
}

function isMenuOpen() {
  return $("aside").is(":visible");
}

function isMobile() {
  return $(".fa-bars").is(":visible");
}

function isUserFormVisible() {
  return userForm.is(":visible");
}

function enableBodyScroll(enable = true) {
  if (enable) $("body").removeClass("disable-scroll");
  else $("body").addClass("disable-scroll");
}

function clearUserForm() {
  usernameInput.val("");
  passwordInput.val("");
}
