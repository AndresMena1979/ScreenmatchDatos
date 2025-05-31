package com.aluracursos.screenmatch.service;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {


        public static String obtenerTraduccion(String texto) {
            OpenAiService service = new OpenAiService("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");  //ApiKey de openai

            CompletionRequest requisicion = CompletionRequest.builder()
                    .model("gpt-3.5-turbo")
                    .prompt("traduce a español el siguiente texto: " + texto)
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            var respuesta = service.createCompletion(requisicion);
            return respuesta.getChoices().get(0).getText();
        }

}
