package com.smarthome.device.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
public class HeartBeatService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${smarthome.heartbeat.url}")
    private String[] urls;

    private int index;

    private UUID deviceUuid;

    @PostConstruct
    public void init() throws IOException {
        String uuidStr = new String(Files.readAllBytes(Paths.get("/data/device"))).trim();
        deviceUuid = UUID.fromString(uuidStr);
    }

    @Scheduled(fixedRate=60000)
    public void heartBeat() throws IOException {
        restTemplate.put(urls[index], deviceUuid);
        index = (index + 1) % urls.length;
    }

}
