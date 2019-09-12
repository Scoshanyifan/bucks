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
        long time = 0L;
        try {
            InetAddress address1 = InetAddress.getLocalHost();
            System.out.println("本机地址: " + address1);
            System.out.println("本机名 hostName: " + address1.getHostName());
            System.out.println("本机IP: " + address1.getHostAddress());

            System.out.println();

            InetAddress address3 = InetAddress.getByName("127.0.0.1");
            System.out.println("address: " + address3);
            System.out.println("hostName: " + address3.getHostName());
            System.out.println("IP: " + address3.getHostAddress());
            System.out.println("address: " + address3);

            System.out.println();

            InetAddress address4 = InetAddress.getByName("192.168.63.1");
            System.out.println("address: " + address4);
            System.out.println("hostName: " + address4.getHostName());
            System.out.println("IP: " + address4.getHostAddress());
            System.out.println("address: " + address4);

            System.out.println();

            time = System.currentTimeMillis();
            InetAddress address2 = InetAddress.getByName("www.taobao.com");
            System.out.println("address: " + address2);
            System.out.println("hostName: " + address2.getHostName());
            System.out.println("IP: " + address2.getHostAddress());
            System.out.println(" taobao cost: " + (System.currentTimeMillis() - time));
            System.out.println("address: " + address2);

            System.out.println();

            time = System.currentTimeMillis();
            InetAddress address5 = InetAddress.getByName("180.101.49.11");
            System.out.println("address: " + address5);
            System.out.println("hostName: " + address5.getHostName());
            System.out.println("IP: " + address5.getHostAddress());
            System.out.println(" baidu dns cost: " + (System.currentTimeMillis() - time));
            System.out.println("address: " + address5);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

}
