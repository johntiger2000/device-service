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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@Slf4j
public class HeartBeatService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${smarthome.heartbeat.url}")
    private String url;

    @Scheduled(fixedRate=5000)
    public void heartBeat() throws IOException {
        UUID deviceUuid = UUID.fromString(getDeviceUuidStr());
        Heartbeat heartbeat = new Heartbeat();
        heartbeat.setDeviceUuid(deviceUuid);
        restTemplate.put(url, heartbeat);
    }

    protected String getDeviceUuidStr() throws IOException {
        return new String(Files.readAllBytes(Paths.get("/data/device"))).trim();
    }

}
