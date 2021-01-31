package com.chuonye.syso.utils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostUtil {
    final static Logger logger = LoggerFactory.getLogger(HostUtil.class);
    
    public static Set<String> getLocalHostAddress() {
        Set<String> address = new HashSet<String>();
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface ni : Collections.list(nis)) {
                if (ni.isLoopback()) continue;
                
                List<InetAddress> ias = Collections.list(ni.getInetAddresses());
                if (ias.size() > 0) {
                    for (InetAddress ia : ias) {
                        if (ia instanceof Inet4Address) {
                            address.add(ia.getHostAddress());
                        }
                    }
                }
            }
        } catch (SocketException e) {
            logger.warn("获取本地网卡失败", e);
        }
        return address;
    }
}
