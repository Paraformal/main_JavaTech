package com.example.main_s2024.DynamicPack;

import com.example.main_s2024.DataPack.StaticGeneralStats;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class ConnectionManager {
    private int port;
    private TimerTask task;
    private Timer timer;
    private URL serverUrl;

    public void setPort(int port) {
        this.port = port;
        // Assuming SingletonStaticGeneralStats is still being used for other purposes
        StaticGeneralStats.getInstance().setPort(port);
    }

    public void scheduleTask() {
        timer = new Timer();
        System.out.println("task scheduled");

        if (task == null) {
            task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Regular task executed");
                    refresh();
                }
            };
            timer.schedule(task, 0, 3000); // Executes this every 3 seconds
        }
    }

    private void refresh() {
        try {
            if (serverUrl == null)
                serverUrl = new URL("http://localhost:" + port + "/pathToDataEndpoint");

            BufferedReader in = new BufferedReader(new InputStreamReader(serverUrl.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Error fetching data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void shutDown() {
        taskCancel();
    }

    private void taskCancel() {
        if (task != null) {
            System.out.println("Timer task stopped");
            task.cancel();
            timer.cancel();
        }
    }
}
