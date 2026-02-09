package com.company.services;

import com.company.models.Car;
import com.company.repositories.interfaces.ICarRepository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ChatBotService {

    private final ICarRepository carRepo;
    private final String apiKey;
    private String lastUserMessage = "";
    private String lastAIMessage = "";



    public ChatBotService(ICarRepository carRepo) {


        this.carRepo = carRepo;

        this.apiKey = System.getenv("GROQ_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new RuntimeException("GROQ_API_KEY is not set");
        }
    }

    // Главный метод — спросить бота
    public String ask(String question) {

        List<Car> cars = carRepo.getAllCars();
        String context = buildContext(cars);

        String prompt = buildPrompt(
                context,
                lastUserMessage,
                lastAIMessage,
                question
        );

        String answer = callAPI(prompt);

        // Save last turn
        lastUserMessage = question;
        lastAIMessage = answer;

        return answer;
    }



    private String buildPrompt(String context,
                               String lastUser,
                               String lastAI,
                               String currentUser) {

        return
                "You are a professional car advisor.\n\n" +

                        "STRICT RULES:\n" +
                        "1. Always follow the format.\n" +
                        "2. Never write attributes in one line.\n" +
                        "3. Use only database cars.\n" +
                        "4. Always include real ID.\n" +
                        "5. Never invent cars.\n" +
                        "6. After answering clarification, give final answer.\n" +
                        "7. Do NOT ask more questions after final answer.\n\n" +
                        "8. If no real ID exists, do NOT recommend that car.\n"+

                        "FORMAT:\n" +
                        "Recommended car:\n" +
                        "• Name (ID: number)\n" +
                        "• Price\n" +
                        "• Year\n" +
                        "• Mileage\n" +
                        "• Engine\n" +
                        "• Category\n" +
                        "Reasons:\n" +
                        "• reason 1\n" +
                        "• reason 2\n" +
                        "Optional question (only once)\n\n" +

                        "DATABASE (PRIVATE):\n" +
                        context +

                        "\n\nPREVIOUS CONTEXT:\n" +
                        "User: " + lastUser + "\n" +
                        "AI: " + lastAI + "\n\n" +

                        "CURRENT USER MESSAGE:\n" +
                        currentUser +

                        "\n\nRespond ONLY in the specified format.";
    }


    public void resetHistory() {
        lastUserMessage = "";
        lastAIMessage = "";
    }







    // Собираем инфу о машинах
    private String buildContext(List<Car> cars) {

        StringBuilder sb = new StringBuilder();

        for (Car c : cars) {

            sb.append("ID=").append(c.getCarId())
                    .append(", Brand=").append(clean(c.getBrand()))
                    .append(", Model=").append(clean(c.getModel()))
                    .append(", Year=").append(c.getYear())
                    .append(", Price=").append(c.getSalePrice())
                    .append(", Mileage=").append(c.getMileage())
                    .append(", Engine=").append(clean(c.getEngineType()))
                    .append(", Category=").append(clean(c.getCategory()))
                    .append(". ");
        }

        return sb.toString();
    }
    private String clean(String s) {

        if (s == null) return "";

        return s.replaceAll("[\\p{Cntrl}]", "")
                .replace("\"", "")
                .trim();
    }



    // Запрос к Grok
    private String callAPI(String prompt) {

        try {

            URL url = new URL("https://api.groq.com/openai/v1/chat/completions");

            HttpURLConnection con =
                    (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);


            String body = """
        {
          "model": "llama-3.1-8b-instant",
          "messages": [
            {
              "role": "system",
              "content": "You are an expert car advisor."
            },
            {
              "role": "user",
              "content": "%s"
            }
          ],
          "temperature": 0.7,
          "max_tokens": 600
        }
        """.formatted(escape(prompt));


            try (OutputStream os = con.getOutputStream()) {
                os.write(body.getBytes("UTF-8"));
            }


            int status = con.getResponseCode();

            InputStream is = (status >= 200 && status < 300)
                    ? con.getInputStream()
                    : con.getErrorStream();


            BufferedReader br =
                    new BufferedReader(new InputStreamReader(is));

            StringBuilder res = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                res.append(line);
            }


            if (status != 200) {
                return "AI HTTP " + status + ":\n" + res;
            }


            return parse(res.toString());


        } catch (Exception e) {
            return "AI error: " + e.getMessage();
        }
    }



    // Вытаскиваем текст из JSON
    private String parse(String json) {

        int i = json.indexOf("\"content\":\"");

        if (i == -1) return "Cannot parse response:\n" + json;

        i += 11;

        int j = json.indexOf("\"", i);

        return json.substring(i, j)
                .replace("\\n", "\n")
                .replace("\\\"", "\"");
    }



    private String escape(String s) {

        return s
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", " ")
                .replace("\r", " ")
                .replace("\t", " ")
                .replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
    }


}
