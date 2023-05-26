export function movieToFavoritesListItem(movie, onClick) {
  const li = $("<li>").attr("id", movie.id);

  $("<div>")
    .addClass("movie-item")
    .append(
      $("<div>")
        .addClass("item-poster")
        .append($("<img>").attr("src", movie.poster).attr("alt", movie.title))
    )
    .append(
      $("<div>")
        .addClass("item-content")
        .append($("<span>").addClass("item-title").text(movie.title))
        .append($("<span>").addClass("item-year").text(movie.year))
    )
    .appendTo(li);

  li.on("click", () => onClick(movie.id));

  return li;
}

export function createMovieContent(m, handleOnFavorite, isInFavorites) {
  // content
  const content = $("<div>").addClass("content");

  // favorites button
  const favBtn = createFavoriteButton(isInFavorites);
  favBtn.on("click", () => handleOnFavorite(m));

  // title
  const title = createMovieTitle(m["imdbID"], m["Title"]);

  // year, duration, genre
  const basicStats = createBasicStatsParagraph(
    m["Year"],
    m["Runtime"],
    m["Genre"]
  );

  // rating
  const rating = createRatingParagraph(m["imdbRating"]);
  // plot

  const plot = createPlotParagraph(m["Plot"]);

  // rest of the stats
  const otherStatsParagraphs = createOtherStats(m);

  content
    .append(favBtn)
    .append(title)
    .append(basicStats)
    .append(rating)
    .append(plot)
    .append(otherStatsParagraphs);

  return content;
}

export function createMoviePoster(m) {
  return $("<div>")
    .addClass("poster")
    .append($("<img>").attr("src", m["Poster"]).attr("alt", m["Title"]));
}

function createFavoriteButton(removeFlavor) {
  const favClass = removeFlavor ? "fav-remove" : "fav-add";
  const favText = removeFlavor ? "Remove" : "Add to favorites";
  return $("<button>")
    .attr("id", "btn-favorites")
    .addClass(favClass)
    .text(favText);
}

function createMovieTitle(movieId, title) {
  return $("<h1>").attr("id", movieId).text(title);
}

function createBasicStatsParagraph(year, duration, genre) {
  const inlineStats = [year, duration, genre].map((s) =>
    $("<span>").addClass("stat").text(s)
  );

  return $("<p>").addClass("basic-stats").append(inlineStats);
}

function createRatingParagraph(rating) {
  return $("<p>")
    .addClass("rating")
    .append($("<img>").attr("src", "./static/assets/imdb.png"))
    .append($("<span>").text(` ${rating} / 10`));
}

function createPlotParagraph(plot) {
  return $("<p>").addClass("plot").text(plot);
}

function createOtherStats(m) {
  const rowStats = [
    { title: "Direction", value: m["Director"] },
    { title: "Actors", value: m["Actors"] },
    { title: "Production", value: m["Production"] },
    { title: "Box Office", value: m["BoxOffice"] },
    { title: "Languages", value: m["Language"] },
    { title: "Rated", value: m["Rated"] },
  ];

  return rowStats.map((s) => {
    return $("<p>")
      .append($("<span>").addClass("info").text(s.title))
      .append($("<span class='values'>").text(s.value));
  });
}
