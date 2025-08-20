package ua.example.dataapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.example.dataapi.data.TransformRequest;
import ua.example.dataapi.service.TransformService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class DataController {

    @Value("${internal.token}")
    private String internalToken;

    private final TransformService transformService;

    @PostMapping("/transform")
    public ResponseEntity<String> transformData(@RequestBody TransformRequest transformRequest,
                                                @RequestHeader("X-Internal-Token") String token) {
        if (!internalToken.equals(token)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid token");
        }
        String response = transformService.transformData(transformRequest.getText());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
