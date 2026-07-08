package br.com.twitterfake.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarPostRequest(

        @NotBlank(message = "O autor é obrigatório")
        @Size(max = 80, message = "O autor deve ter no máximo 80 caracteres")
        String autor,

        @NotBlank(message = "O conteúdo é obrigatório")
        @Size(max = 280, message = "O conteúdo deve ter no máximo 280 caracteres")
        String conteudo

) {
}