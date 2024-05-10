//Redirecionamento ao clicar em botões da navbar
const log = document.querySelector('#log');
const sign = document.querySelector('#sign');
const videoaulas = document.querySelector('#vid')
const perfil = document.querySelector('#perfil')
const adicionar = document.querySelector('#adicionar')

log.addEventListener('click', function() {
  window.location.href = 'forms.html';
});

sign.addEventListener('click', function() {
    window.location.href = 'forms.html';
});

videoaulas.addEventListener('click', function() {
  window.location.href = 'indexvideoaulas.html';
});

perfil.addEventListener('click', function() {
  window.location.href = 'indexperfil.html';
});

// Obtém as referências para as divs
const di1 = document.querySelector('#di1');
const di2 = document.querySelector('#di2');
const di3 = document.querySelector('#di3');
const co1 = document.querySelector('#co1');
const co2 = document.querySelector('#co2');
const co3 = document.querySelector('#co3');
const x1 = document.querySelector('#x1');
const x2 = document.querySelector('#x2');
const x3 = document.querySelector('#x3');
const btn1 = document.querySelector('#btnPlaylistIniciante');
const btn2 = document.querySelector('#btnPlaylistIntermediario');
const btn3 = document.querySelector('#btnPlaylistAvancado');

function setPlaylist1() {
  PLAYLIST_ID = "PLLxxFuebb29P9_GvoxXQ6pvzSUOd67sof";
  console.log("Playlist ID alterado para:", PLAYLIST_ID);
}

function setPlaylist2() {
  PLAYLIST_ID = "PL7Z74DuOF81Hzok9FSRFZRblT4UfqHhJ4";
  console.log("Playlist ID alterado para:", PLAYLIST_ID);
}

function setPlaylist3() {
  PLAYLIST_ID = "PL0ao6cotJFFUgKFlnkidAO7S5pWXQtiLB";
  console.log("Playlist ID alterado para:", PLAYLIST_ID);
}

// Adiciona eventos de clique aos botões para chamar as funções correspondentes
btn1.addEventListener('click', setPlaylist1);
btn2.addEventListener('click', setPlaylist2);
btn3.addEventListener('click', setPlaylist3);

btn1.addEventListener('click', function(){
  window.location.href='indexvideoaulas.html';
})
btn2.addEventListener('click', function(){
  window.location.href='indexvideoaulas.html';
})
btn3.addEventListener('click', function(){
  window.location.href='indexvideoaulas.html';
})
// Oculta as divs de id "co(numero)"
co1.style.display = 'none';
co2.style.display = 'none';
co3.style.display = 'none';

// Adiciona os event listeners para as divs di1, di2 e di3
di1.addEventListener('click', function() {
  co1.style.display = 'flex';
  co2.style.display = 'none';
  co3.style.display = 'none';
});

di2.addEventListener('click', function() {
  co1.style.display = 'none';
  co2.style.display = 'flex';
  co3.style.display = 'none';
});

di3.addEventListener('click', function() {
  co1.style.display = 'none';
  co2.style.display = 'none';
  co3.style.display = 'flex';
});

x1.addEventListener('click', function() {
  co1.style.display = 'none';
  co2.style.display = 'none';
  co3.style.display = 'none';
});

x2.addEventListener('click', function() {
  co1.style.display = 'none';
  co2.style.display = 'none';
  co3.style.display = 'none';
});

x3.addEventListener('click', function() {
  co1.style.display = 'none';
  co2.style.display = 'none';
  co3.style.display = 'none';
});



//Mensagem estilizada no console
console.log("                                             ");
console.log(" _____ _       _   _         _     _ _       ");
console.log("| __  | |_ _ _| |_| |_ _____|_|___|_| |_ _ _ ");
console.log("|    -|   | | |  _|   |     | |  _| |  _| | |");
console.log("|__|__|_|_|_  |_| |_|_|_|_|_|_|___|_|_| |_  |");
console.log("          |___|                         |___|");
console.log("                                             ");