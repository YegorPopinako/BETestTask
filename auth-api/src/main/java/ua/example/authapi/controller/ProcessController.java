package ua.example.authapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.example.authapi.dto.TransformRequest;
import ua.example.authapi.service.ProcessService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProcessController {

    private final ProcessService processService;

    @PostMapping("/process")
    public ResponseEntity<String> process(@RequestBody TransformRequest request,
                                          @AuthenticationPrincipal Jwt jwt) {
        String response = processService.process(request, jwt.getClaimAsString("userId"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}