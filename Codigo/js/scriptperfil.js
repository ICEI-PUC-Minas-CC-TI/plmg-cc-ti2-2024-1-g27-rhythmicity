//Redirecionamento ao clicar em botões da navbar
const log = document.querySelector('#log');
const sign = document.querySelector('#sign');
const videoaulas = document.querySelector('#vid');
const perfil = document.querySelector('#perfil');

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

//Redirecionamento ao clicar no logotipo
const logotype = document.querySelector('#logotype');

logotype.addEventListener('click', function() {
  window.location.href = 'index.html';
});

let userLogado = {
  usuario: "Lucca Richter",
  sobre: "Escreva algo sobre você"
};

// Recuperar perfil do localStorage, se existir
if (localStorage.getItem("userLogado")) {
  userLogado = JSON.parse(localStorage.getItem("userLogado"));
} else {
  // Caso contrário, salvar o perfil inicial no localStorage
  localStorage.setItem("userLogado", JSON.stringify(userLogado));
}

const usuario = document.querySelector("#usuario");
usuario.innerHTML = userLogado.usuario;

let btn_editar = document.querySelector("#btn_editar");
let btn_salvar = document.querySelector("#btn_salvar");
let nomeUsuario = document.querySelector("#usuario");
let sobreMim = document.querySelector("#sobreMim");
let containerEdit = document.querySelector("#edit");
let containerShow = document.querySelector("#show");
let foto = document.querySelector("#fotoPerfil");

containerEdit.style.display = "none";

btn_editar.addEventListener("click", () => {
  containerEdit.style.display = "block";
  containerShow.style.display = "none";
  nomeUsuario.value = userLogado.usuario; // Preencher a caixa de texto com o nome de usuário atual
  sobreMim.value = userLogado.sobre; // Preencher a caixa de texto com a descrição atual
});

btn_salvar.addEventListener("click", () => {
  containerShow.style.display = "block";
  containerEdit.style.display = "none";

  userLogado.usuario = nomeUsuario.value;
  userLogado.sobre = sobreMim.value;
  localStorage.setItem("userLogado", JSON.stringify(userLogado));

  usuario.innerHTML = userLogado.usuario;
  about.innerHTML = userLogado.sobre;
});


const about = document.querySelector("#about");
about.innerHTML = userLogado.sobre;

  


//Mensagem estilizada no console
console.log("                                             ");
console.log(" _____ _       _   _         _     _ _       ");
console.log("| __  | |_ _ _| |_| |_ _____|_|___|_| |_ _ _ ");
console.log("|    -|   | | |  _|   |     | |  _| |  _| | |");
console.log("|__|__|_|_|_  |_| |_|_|_|_|_|_|___|_|_| |_  |");
console.log("          |___|                         |___|");
console.log("                                             ");