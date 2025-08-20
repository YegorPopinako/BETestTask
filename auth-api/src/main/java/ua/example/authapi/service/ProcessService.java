package ua.example.authapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ua.example.authapi.dto.TransformRequest;
import ua.example.authapi.model.ProcessingLog;
import ua.example.authapi.repository.LogRepository;

import java.time.LocalTime;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private final String URL = "http://data-api:8081/api/transform";

    @Value("${internal.token}")
    private String internalToken;

    private final RestClient restClient;

    private final LogRepository logRepository;

    public String process(TransformRequest request, String userId) {
        String response = restClient.post()
                .uri(URL)
                .contentType(APPLICATION_JSON)
                .header("X-Internal-Token", internalToken)
                .body(request)
                .retrieve()
                .body(String.class);

        ProcessingLog processingLog = new ProcessingLog();
        processingLog.setId(UUID.randomUUID().toString());
        processingLog.setUserId(userId);
        processingLog.setInputText(request.getText());
        processingLog.setOutputText(response);
        processingLog.setTimeStamp(LocalTime.now());
        logRepository.save(processingLog);

        return response;
    }
}

