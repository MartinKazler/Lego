package org.proxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Proxy {

    private final int PORT;
    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    public List<Server> servers;

    public Proxy(int port) {
        this.PORT = port;
        this.bossGroup = new NioEventLoopGroup();
        this.workerGroup = new NioEventLoopGroup();
        this.servers = new ArrayList<>();
    }

    public void start() {
        System.out.println("Proxy starta");

        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ProxyChannelInitializer(this))
                    .bind(this.PORT).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void load() {
        System.out.println("load" + PORT);

        File file = new File("config.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Scanner serverInput = new Scanner(file);
            while (serverInput.hasNextLine()) {
                String line = serverInput.nextLine();
                String[] split = line.split(" ");
                servers.add(new Server(Integer.parseInt(split[1]), split[0]));
                System.out.println("Search: file--> " + servers);
            }
            serverInput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public EventLoopGroup getWorkerGroup() {
        return workerGroup;
    }

    private int nodeIndex = 0;

    public Server roundRobin() {
        System.out.println("" + nodeIndex + " Round Roibn walks through each line " + servers);

        if (nodeIndex >= servers.size()) {
            nodeIndex = 0;
        }
        return servers.get(nodeIndex++);
    }

}
