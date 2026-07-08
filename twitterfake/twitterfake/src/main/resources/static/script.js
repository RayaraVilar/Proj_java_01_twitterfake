const API_URL = "http://localhost:8080/posts";

const form = document.getElementById("postForm");
const autorInput = document.getElementById("autor");
const conteudoInput = document.getElementById("conteudo");
const postsContainer = document.getElementById("posts");
const contador = document.getElementById("contador");

let postEditandoId = null;

conteudoInput.addEventListener("input", () => {
  contador.textContent = `${conteudoInput.value.length}/280`;
});

form.addEventListener("submit", async (event) => {
  event.preventDefault();

  const post = {
    autor: autorInput.value,
    conteudo: conteudoInput.value,
  };

  try {
    const url = postEditandoId ? `${API_URL}/${postEditandoId}` : API_URL;
    const method = postEditandoId ? "PUT" : "POST";

    const response = await fetch(url, {
      method,
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(post),
    });

    if (!response.ok) {
      throw new Error("Erro ao salvar postagem");
    }

    autorInput.value = "";
    conteudoInput.value = "";
    contador.textContent = "0/280";
    postEditandoId = null;

    form.querySelector("button").textContent = "Publicar";

    carregarPosts();
  } catch (error) {
    alert("Não foi possível salvar o post.");
    console.error(error);
  }
});

async function carregarPosts() {
  try {
    const response = await fetch(API_URL);
    const posts = await response.json();

    postsContainer.innerHTML = "";

    if (posts.length === 0) {
      postsContainer.innerHTML = `<p class="empty">Nenhuma postagem ainda.</p>`;
      return;
    }

    posts.forEach((post) => {
      const postElement = document.createElement("article");
      postElement.classList.add("post");

      postElement.innerHTML = `
        <div class="post-header">
          <span class="autor">@${post.autor}</span>
          <span class="data">${formatarData(post.criadoEm)}</span>
        </div>

        <p class="conteudo">${post.conteudo}</p>

        <div class="post-actions">
          <button class="btn-edit" onclick="editarPost(${post.id}, '${escapeText(post.autor)}', '${escapeText(post.conteudo)}')">
            Editar
          </button>

          <button class="btn-delete" onclick="deletarPost(${post.id})">
            Excluir
          </button>
        </div>
      `;

      postsContainer.appendChild(postElement);
    });
  } catch (error) {
    postsContainer.innerHTML = `<p class="empty">Erro ao carregar postagens.</p>`;
    console.error(error);
  }
}

function editarPost(id, autor, conteudo) {
  postEditandoId = id;

  autorInput.value = autor;
  conteudoInput.value = conteudo;
  contador.textContent = `${conteudo.length}/280`;

  form.querySelector("button").textContent = "Salvar edição";

  window.scrollTo({
    top: 0,
    behavior: "smooth",
  });
}

async function deletarPost(id) {
  const confirmar = confirm("Deseja excluir esta postagem?");

  if (!confirmar) {
    return;
  }

  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "DELETE",
    });

    if (!response.ok) {
      throw new Error("Erro ao excluir postagem");
    }

    carregarPosts();
  } catch (error) {
    alert("Não foi possível excluir o post.");
    console.error(error);
  }
}

function formatarData(data) {
  if (!data) {
    return "";
  }

  return new Date(data).toLocaleString("pt-BR");
}

function escapeText(text) {
  return String(text).replaceAll("'", "\\'").replaceAll('"', "&quot;");
}

carregarPosts();
