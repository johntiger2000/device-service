package com.smarthome.device.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Heartbeat {

    UUID deviceUuid;
    String protocol;
    String ipAddress;
    Integer port;
    String version;

}
