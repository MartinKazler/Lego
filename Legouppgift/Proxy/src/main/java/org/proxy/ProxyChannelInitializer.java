package org.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ProxyChannelInitializer extends ChannelInitializer<SocketChannel> {

    public Proxy proxy;

    public ProxyChannelInitializer(Proxy proxy) {
        this.proxy = proxy;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        Server server = proxy.roundRobin();
        Bootstrap bootstrap = new Bootstrap();
        Channel channel = bootstrap.group(proxy.getWorkerGroup())
                .channel(NioSocketChannel.class)
                .handler(new ServerChannelInitializer(socketChannel))
                .connect(server.getAdress(), server.getPort()).sync().channel();
        socketChannel.pipeline().addFirst(new ProxyChannelHandler(channel));
    }

}
