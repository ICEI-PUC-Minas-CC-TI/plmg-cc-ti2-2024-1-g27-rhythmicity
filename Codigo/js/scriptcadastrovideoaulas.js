


function leDados() {
  let strDados = localStorage.getItem('db');
  let objDados = {};

  if (strDados) {
    objDados = JSON.parse(strDados);
  } else {
    objDados = {
      videos: [
        {
          titulo: "Aula 1 de Violão iniciante Nível Zero",
          descricao: "Aula incial de violão",
          duracao: "14:57",
          link: "https://www.youtube.com/watch?v=jt9oRHFpZfs&ab_channel=HeitorCastro"
        },
        {
          titulo: "Aula 2",
          descricao: "Aula 2 de violão",
          duracao: "8:35",
          link: "https://www.youtube.com/watch?v=aaaaaaaaa"
        },
        {
          titulo: "Aula 3",
          descricao: "Aula 3 de violão",
          duracao: "3:17",
          link: "https://www.youtube.com/watch?v=bbbbbbbbb"
        }
      ]
    };
  }

  return objDados;
}

function salvaDados(dados) {
  localStorage.setItem('db', JSON.stringify(dados));
}

function imprimeDados() {
  let tela = document.getElementById('tela');
  let strHtml = '';
  let objDados = leDados();

  for (let i = 0; i < objDados.videos.length; i++) {
    let link = objDados.videos[i].link;
    try {
      const url = new URL(link);
      const searchParams = new URLSearchParams(url.search);
      const videoId = searchParams.get("v");
      const embedLink = `https://www.youtube.com/embed/${videoId}`;
      const videoEmbed = `<iframe width="560" height="315" src="${embedLink}" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>`;
      strHtml += `<p>${objDados.videos[i].titulo} - ${objDados.videos[i].descricao} - 
      ${objDados.videos[i].duracao}  <br>${videoEmbed}  <br><a href="${embedLink}">${embedLink}</a>
       <button class="btn btn-outline-dark" onclick="if(confirm('Tem certeza que deseja excluir este vídeo?')) { excluirVideo(${i}); }">Excluir</button>
      </p>`;
    } catch (e) {
      alert('A URL do vídeo é inválida. Verifique a URL e tente novamente.');
      localStorage.clear();
      return; // Sai da função caso haja uma URL inválida
    }
  }

  tela.innerHTML = strHtml;
}

function incluirVideoAula() {
  // Ler os dados do localStorage
  let objDados = leDados();

  // Incluir uma nova videoaula
  let strTitulo = document.getElementById('campoTitulo').value;
  let strDescricao = document.getElementById('campoDescricao').value;
  let strDuracao = document.getElementById('campoDuracao').value;
  let strLink = document.getElementById('campoLink').value;
  let novaVideoAula = {
    titulo: strTitulo,
    descricao: strDescricao,
    duracao: strDuracao,
    link: strLink
  };
  objDados.videos.push(novaVideoAula);

  // Salvar os dados no localStorage novamente
  salvaDados(objDados);

  // Atualiza os dados da tela
  imprimeDados();
}

function excluirVideo(index) {
  let objDados = leDados();
  objDados.videos.splice(index, 1);
  salvaDados(objDados);
  imprimeDados();
}


// Configura os botões
document.getElementById('btnCarregaVideos').addEventListener('click', imprimeDados);
document.getElementById('btnIncluirVideo').addEventListener('click', incluirVideoAula);
document.getElementById('btnExcluirVideo').addEventListener('click', excluirVideo);
