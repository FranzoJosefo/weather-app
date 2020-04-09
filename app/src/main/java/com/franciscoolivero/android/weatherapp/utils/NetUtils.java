package com.franciscoolivero.android.weatherapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class NetUtils {

    private String getIPaddress(Context context) {
        String IpAddress = null;
        boolean WIFI = false;
        boolean MOBILE = false;

        ConnectivityManager CM = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo[] networkInfo = new NetworkInfo[0];
        if (CM != null) {
            networkInfo = CM.getAllNetworkInfo();
        }

        for (NetworkInfo netInfo : networkInfo) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if (netInfo.isConnected())
                    WIFI = true;

            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (netInfo.isConnected())
                    MOBILE = true;
        }

        if (WIFI) {
            IpAddress = GetDeviceIpWiFiData(context);
        }

        if (MOBILE) {
            IpAddress = GetDeviceIpMobileData();
        }

        return IpAddress;
    }


    private String GetDeviceIpMobileData() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
                 en.hasMoreElements(); ) {
                NetworkInterface networkinterface = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = networkinterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Current IP", ex.toString());
        }
        return null;
    }

    private String GetDeviceIpWiFiData(Context context) {

        InetAddress myInetIP = null;
        WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        String ip = null;
        if (wm != null) {
            byte[] myIPAddress = BigInteger.valueOf(wm.getConnectionInfo().getIpAddress()).toByteArray();
            try {
                myInetIP = InetAddress.getByAddress(myIPAddress);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            ip = myInetIP != null ? myInetIP.getHostAddress() : null;
        }
        return ip;
    }

}
