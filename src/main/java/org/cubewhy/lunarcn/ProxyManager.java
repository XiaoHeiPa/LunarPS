package org.cubewhy.lunarcn;

import io.netty.bootstrap.ChannelFactory;
import io.netty.channel.socket.oio.OioSocketChannel;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

public class ProxyManager {
    private static final ProxyManager instance = new ProxyManager();

    public static final ProxyConfig proxyConfig = new ProxyConfig(System.getProperty("user.home") + "/.cubewhy/lunarcn/proxy.json");

    public String proxyIp = "127.0.0.1";
    public String proxyPort = "10808";
    public Proxy.Type proxyType = Proxy.Type.SOCKS;

    public boolean state = false;

    public static ProxyManager getInstance() {
        return instance;
    }

    public Proxy getProxy() {
        return new Proxy(proxyType, new InetSocketAddress(proxyIp, Integer.parseInt(proxyPort)));
    }

    public String getProxyAddress() {
        return proxyIp + ":" + proxyPort;
    }

    public void setAddress(String address) {
        String[] realAddress = address.split(":");
        if (realAddress.length != 2) {
            return; // 不是正确的IP地址
        }
        this.proxyIp = realAddress[0];
        this.proxyPort = realAddress[1];
        proxyConfig.setProxyAutomatically();
    }

    public static class ProxyOioChannelFactory implements ChannelFactory<OioSocketChannel> {

        /**
         * Creates a new channel.
         */
        @Override
        public OioSocketChannel newChannel() {
            return new OioSocketChannel(new Socket(ProxyManager.getInstance().getProxy()));
        }
    }
}
