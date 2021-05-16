package com.sergisa;

public class Application extends Thread {

    private static Application instance;
    private DraggingForm mainWinowInstance;


    public static synchronized Application getInstance() {
        if (instance == null) {
            instance = new Application();
        }
        return instance;
    }

    @Override
    public void run() {
        System.out.println("Started Application thread");
        for (int i = 0; i < 100; i++) {
            try {
                sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Iteration " + i);
        }
    }

    public DraggingForm getMainWinowInstance() {
        return mainWinowInstance;
    }

    public void setMainWinowInstance(DraggingForm mainWinowInstance) {
        this.mainWinowInstance = mainWinowInstance;
    }
}
