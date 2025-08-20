package ua.example.dataapi.service;

import org.springframework.stereotype.Service;

@Service
public class TransformService {

    public String transformData(String text) {
        return text.toUpperCase();
    }
}
