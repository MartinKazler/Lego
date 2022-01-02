package org.proxy;

import lombok.Data;

@Data
public class Server {
    private int PORT;
    private String adress;

    public Server(int ports, String address) {
        this.PORT = ports;
        this.adress = address;
    }

    public int getPort() {
        return this.PORT;
    }
}
