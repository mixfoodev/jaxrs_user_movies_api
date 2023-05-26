export function searchButtonAnimation(start = true) {
  if (start) $("#search button").addClass("animate").attr("disabled", "true");
  else $("#search button").removeClass("animate").removeAttr("disabled");
}

export function favouriteLoadAnimation(id, start = true) {
  if (start) $(".favorites #" + id).addClass("fa-fade");
  else $(".favorites #" + id).removeClass("fa-fade");
}

export function formLoadingAnimation(start = true) {
  const animClass = "fa-flip";

  $(".user-form button").attr("disabled", start);
  if (start) $(".user-form button i").addClass(animClass);
  else $(".user-form button i").removeClass(animClass);
}
