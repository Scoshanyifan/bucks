package com.kunbu.spring.bucks;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @project: bucks
 * @author: kunbu
 * @create: 2019-09-09 09:32
 **/
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class NetTest {

    public static void main(String[] args) {

        try {
            InetAddress address1 = InetAddress.getLocalHost();
            System.out.println("hostName: " + address1.getHostName());
            System.out.println("IP: " + address1.getHostAddress());

            InetAddress address3 = InetAddress.getByName("127.0.0.1");
            System.out.println("hostName: " + address1.getHostName());
            System.out.println("IP: " + address1.getHostAddress());

            InetAddress address4 = InetAddress.getByName("192.168.63.1");
            System.out.println("hostName: " + address1.getHostName());
            System.out.println("IP: " + address1.getHostAddress());

            InetAddress address2 = InetAddress.getByName("www.baidu.com");
            System.out.println("hostName: " + address1.getHostName());
            System.out.println("IP: " + address1.getHostAddress());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

}
