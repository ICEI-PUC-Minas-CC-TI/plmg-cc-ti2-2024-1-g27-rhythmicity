

//Mudar aparência dos ícones de mostrar/esconder senha e efetivamente, mostrar/esconder a senha. (estático)
const passwordFields = document.querySelectorAll('.password');
const eyeIcons = document.querySelectorAll('.olho-senha');
const forms = document.querySelector('.forms');

eyeIcons.forEach((icon, index) => {
  icon.addEventListener('click', () => {
    const type = passwordFields[index].getAttribute('type') === 'password' ? 'text' : 'password';
    passwordFields[index].setAttribute('type', type);
    icon.setAttribute('name', type === 'password' ? 'hide' : 'show');
  });
});



//Mostrar o formulário de Login ou o de Cadastrar-se, à depender da requisição do usuário. (estático)
const links = document.querySelectorAll('.form-link a');

links.forEach(link => {
  link.addEventListener("click", e => {
    e.preventDefault(); // Desabilitar submit padrão do form
    forms.classList.toggle("show-signup");
  })
});



//Verificar se a senha 1 e a senha 2 (confirmada) coincidem, e enviar uma mensagem de erro caso contrário. (estático)
const password = document.getElementById("signup-password");
const confirmPassword = document.getElementById("signup-confirm-password");
const error = document.getElementById("password-error");

function checkPassword() {
if (password.value !== confirmPassword.value) {
error.innerHTML = "As senhas não coincidem.";
} else {
error.innerHTML = "";
}
}

confirmPassword.addEventListener("keyup", checkPassword);


//Redirecionamento ao clicar no logotipo
const logotype = document.querySelector('#logotype');

logotype.addEventListener('click', function() {
  window.location.href = 'index.html';
});



//Mensagem estilizada no console
console.log("                                             ");
console.log(" _____ _       _   _         _     _ _       ");
console.log("| __  | |_ _ _| |_| |_ _____|_|___|_| |_ _ _ ");
console.log("|    -|   | | |  _|   |     | |  _| |  _| | |");
console.log("|__|__|_|_|_  |_| |_|_|_|_|_|_|___|_|_| |_  |");
console.log("          |___|                         |___|");
console.log("                                             ");