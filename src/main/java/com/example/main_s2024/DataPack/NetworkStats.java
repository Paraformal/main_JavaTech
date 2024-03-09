package com.example.main_s2024.DataPack;

import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.Enumeration;
import java.util.logging.ErrorManager;

public class NetworkStats {
    private static NetworkStats ourInstance = new NetworkStats();
    private SystemInfo systemInfo = new SystemInfo();
    private StringBuilder genericStringBuilder = new StringBuilder();

    private NetworkStats() {
    }

    public static NetworkStats getInstance() {
        return ourInstance;
    }

    private String getDefaultNetworkInteface() throws SocketException, UnknownHostException {
        Enumeration<NetworkInterface> netifs = NetworkInterface.getNetworkInterfaces();

        InetAddress myAddr = InetAddress.getLocalHost();

        while (netifs.hasMoreElements()) {
            NetworkInterface networkInterface = netifs.nextElement();
            Enumeration<InetAddress> inAddrs = networkInterface.getInetAddresses();
            while (inAddrs.hasMoreElements()) {
                InetAddress inAddr = inAddrs.nextElement();
                if (inAddr.equals(myAddr)) {
                    return networkInterface.getName();
                }
            }
        }
        return "";
    }

    public String getNetworkSpeed() {

        genericStringBuilder.setLength(0);
        String genericString;
        try {
            genericString = getDefaultNetworkInteface();
        } catch (SocketException | UnknownHostException e) {
            genericStringBuilder.append("download speed: not supported\n");
            genericStringBuilder.append("upload speed: not supported\n");
            e.printStackTrace();
            return genericStringBuilder.toString();
        }

        NetworkIF[] networkIFs = systemInfo.getHardware().getNetworkIFs().toArray(new NetworkIF[0]);
        int i = 0;
        NetworkIF net = networkIFs[0];
        try {
            while (!networkIFs[i].getName().equals(genericString)) {
                net = networkIFs[i];
                i++;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            genericStringBuilder.append("download speed: not supported\n");
            genericStringBuilder.append("upload speed: not supported\n");
            return genericStringBuilder.toString();
        }

        long download1 = net.getBytesRecv();
        long upload1 = net.getBytesSent();
        long timestamp1 = net.getTimeStamp();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            new ErrorManager().notifyAll();
            e.printStackTrace();
        }
        net.updateAttributes(); //Updating network stats
        long download2 = net.getBytesRecv();
        long upload2 = net.getBytesSent();
        long timestamp2 = net.getTimeStamp();

        genericStringBuilder.append("download speed: ").append(formatSize((download2 - download1) /
                (timestamp2 - timestamp1))).append("/s\n");
        genericStringBuilder.append("upload speed: ").append(formatSize((upload2 - upload1) /
                (timestamp2 - timestamp1))).append("/s\n");

        return genericStringBuilder.toString();
    }

    private String formatSize(long size) {
        double m = size / 1024.0;
        double g = size / 1048576.0;
        double t = size / 1073741824.0;

        DecimalFormat dec = new DecimalFormat("0.000");
        String genericString;
        if (t > 1) {
            genericString = dec.format(t).concat(" TB");
        } else if (g > 1) {
            genericString = dec.format(g).concat(" GB");
        } else if (m > 1) {
            genericString = dec.format(m).concat(" MB");
        } else {
            genericString = size + " KB";
        }
        return genericString;
    }


}
