import videos from './videos.js';

// Filtra os vídeos do array principal para cada nível
const videosiniciante = videos.filter(video => video.title === 'Aula 1' || video.title === 'Aula 2');
const videosintermediario = videos.filter(video => video.title === 'Aula 3' || video.title === 'Aula 4');
const videosavancado = videos.filter(video => video.title === 'Aula 5' || video.title === 'Aula 6');

// Função para obter um vídeo aleatório de um array
function getRandomVideo(videosArray) {
  const randomIndex = Math.floor(Math.random() * videosArray.length);
  return videosArray[randomIndex];
}

// Obtém os vídeos aleatórios para cada array
const videoIniciante = getRandomVideo(videosiniciante);
const videoIntermediario = getRandomVideo(videosintermediario);
const videoAvancado = getRandomVideo(videosavancado);

// Obtém as referências para as divs de vídeo
const videoOverlay1 = document.querySelector('#video-overlay1');
const videoOverlay2 = document.querySelector('#video-overlay2');
const videoOverlay3 = document.querySelector('#video-overlay3');

// Insere os vídeos aleatórios nas divs correspondentes
videoOverlay1.innerHTML = `<video src="${videoIniciante.src}" controls></video>`;
videoOverlay2.innerHTML = `<video src="${videoIntermediario.src}" controls></video>`;
videoOverlay3.innerHTML = `<video src="${videoAvancado.src}" controls></video>`;