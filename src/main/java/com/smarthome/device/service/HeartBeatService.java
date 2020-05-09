package com.smarthome.device.service;

import com.smarthome.device.model.Heartbeat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private String url;

    @Scheduled(fixedRate=5000)
    public void heartBeat() throws IOException {
        String data = new String(Files.readAllBytes(Paths.get("/data/device")));
        UUID deviceUuid = UUID.fromString(data);
        Heartbeat heartbeat = new Heartbeat();
        heartbeat.setDeviceUuid(deviceUuid);
        restTemplate.put(url, heartbeat);
    }

}
