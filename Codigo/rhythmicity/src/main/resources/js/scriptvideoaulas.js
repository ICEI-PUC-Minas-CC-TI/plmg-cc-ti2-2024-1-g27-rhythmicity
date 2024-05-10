const API_KEY = "AIzaSyDRSPb4UA7JsqcNbx0B1RrmC03TRAmTQGI";
const PLAYLIST_ID = "PLrVhyUnEQMV_xr9310DMRkMnqjIm8iExQ";

function loadVideosFromYouTube() {
  const playlist_area = document.querySelector(".playlist");

  // Função para fazer a solicitação da próxima página de resultados
  async function fetchPlaylistPage(pageToken) {
    let url = `https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=${PLAYLIST_ID}&key=${API_KEY}&maxResults=50`;
    if (pageToken) {
      url += `&pageToken=${pageToken}`;
    }
    try {
      const response = await fetch(url);
      const data = await response.json();
      // Iterando sobre os itens da página atual
      data.items.forEach((item, index) => {
        const videoId = item.snippet.resourceId.videoId;
        const title = item.snippet.title;
        const thumbnail = item.snippet.thumbnails.default.url;

        // Criando um elemento de vídeo para cada item
        const div = document.createElement("div");
        div.classList.add("playlist-video");
        div.innerHTML = `
            <img src="${thumbnail}" alt="${title}">
            <label class="playlist-video-info">${title}</label>
          `;

        // Adicionando um evento de clique para trocar o vídeo principal
        div.addEventListener("click", () => {
          setVideo(videoId, title);
          updateActiveVideo(div);
        });

        playlist_area.appendChild(div);

        // Se for o primeiro vídeo, defini-lo como ativo
        if (index === 0 && !pageToken) {
          setVideo(videoId, title);
          updateActiveVideo(div);
        }

        // guardar informacoes dos videos quando mudar
        div.dataset.videoId = videoId;
        div.dataset.title = title;
      });

      // Verificando se há mais páginas de resultados
      if (data.nextPageToken) {
        // Se houver mais páginas, fazer outra solicitação para a próxima página
        fetchPlaylistPage(data.nextPageToken);
      }
    } catch (error) {
      console.error("Erro ao carregar vídeos:", error);
    }
  }

  // Iniciando a solicitação para a primeira página
  fetchPlaylistPage(null);
}

function setVideo(videoId, title) {
  const video_main = document.querySelector(".main-video-content");
  video_main.innerHTML = `
      <iframe id="youtube-video" width="560" height="315" src="https://www.youtube.com/embed/${videoId}" frameborder="0" allowfullscreen></iframe>
      <label>${title}</label>
    `;

  const youtube_video = document.getElementById("youtube-video");
  youtube_video.addEventListener("ended", playNextVideo);
}

function playNextVideo() {
  const active_video = document.querySelector(".playlist-video.active");
  const next_video = active_video.nextElementSibling;

  // verificar se tem um proximo video na playlist
  if (next_video) {
    const videoId = next_video.dataset.videoID;
    const title = next_video.dataset.title;
    setVideo(videoId, title);
    updateActiveVideo(next_video);
  }
}

function updateActiveVideo(selectedVideo) {
  const playlist_videos = document.querySelectorAll(".playlist-video");
  playlist_videos.forEach((video) => {
    video.classList.remove("active");
  });
  selectedVideo.classList.add("active");
}

loadVideosFromYouTube();

// variaveis
let searchBtn = document.querySelector(".searchBtn");
let closeBtn = document.querySelector(".closeBtn");
let searchBox = document.querySelector(".searchBox");
let navigation = document.querySelector(".navigation");
let menuToggle = document.querySelector(".menuToggle");
let header = document.querySelector("header");

// função para barra de pesquisa aparecer e sumir da navbar
searchBtn.onclick = function () {
  searchBox.classList.add("active");
  closeBtn.classList.add("active");
  searchBtn.classList.add("hidden"); // Adiciona a classe 'hidden' para ocultar o botão de pesquisa
};

closeBtn.onclick = function () {
  searchBox.classList.remove("active");
  closeBtn.classList.remove("active");
  searchBtn.classList.remove("hidden"); // Remove a classe 'hidden' para exibir o botão de pesquisa novamente
};

// responsividade da navbar
menuToggle.onclick = function () {
  header.classList.toggle("open");
};

//Mensagem estilizada no console
console.log("                                             ");
console.log(" _____ _       _   _         _     _ _       ");
console.log("| __  | |_ _ _| |_| |_ _____|_|___|_| |_ _ _ ");
console.log("|    -|   | | |  _|   |     | |  _| |  _| | |");
console.log("|__|__|_|_|_  |_| |_|_|_|_|_|_|___|_|_| |_  |");
console.log("          |___|                         |___|");
console.log("                                             ");
