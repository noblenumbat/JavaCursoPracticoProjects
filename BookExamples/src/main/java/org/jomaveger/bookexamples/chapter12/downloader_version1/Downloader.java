package org.jomaveger.bookexamples.chapter12.downloader_version1;

import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Downloader extends JPanel implements Runnable {

    private final URL downloadURL;
    private InputStream inputStream;

    private final OutputStream outputStream;
    private byte[] buffer;

    private final int fileSize;
    private int bytesRead;

    private JLabel urlLabel;
    private JLabel sizeLabel;
    private JLabel completeLabel;
    private JProgressBar progressBar;

    public final static int BUFFER_SIZE = 1000;

    private boolean stopped;
    private boolean sleepScheduled;
    private boolean suspended;

    public final static int SLEEP_TIME = 5 * 1000; // 5 seconds

    private Thread thisThread;

    public Downloader(URL url, OutputStream os) throws IOException {
        downloadURL = url;
        outputStream = os;
        bytesRead = 0;
        URLConnection urlConnection = downloadURL.openConnection();
        fileSize = urlConnection.getContentLength();
        if (fileSize == -1) {
            throw new FileNotFoundException(url.toString());
        }
        inputStream = new BufferedInputStream(
                urlConnection.getInputStream());
        buffer = new byte[BUFFER_SIZE];
        thisThread = new Thread(this);
        buildLayout();

        stopped = false;
        sleepScheduled = false;
        suspended = false;
    }

    private void buildLayout() {
        JLabel label;
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 10, 5, 10);

        constraints.gridx = 0;
        label = new JLabel("URL:", JLabel.LEFT);
        add(label, constraints);

        label = new JLabel("Complete:", JLabel.LEFT);
        add(label, constraints);

        label = new JLabel("Downloaded:", JLabel.LEFT);
        add(label, constraints);

        constraints.gridx = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.weightx = 1;
        urlLabel = new JLabel(downloadURL.toString());
        add(urlLabel, constraints);

        progressBar = new JProgressBar(0, fileSize);
        progressBar.setStringPainted(true);
        add(progressBar, constraints);

        constraints.gridwidth = 1;
        completeLabel = new JLabel(Integer.toString(bytesRead));
        add(completeLabel, constraints);

        constraints.gridx = 2;
        constraints.weightx = 0;
        constraints.anchor = GridBagConstraints.EAST;
        label = new JLabel("Size:", JLabel.LEFT);
        add(label, constraints);

        constraints.gridx = 3;
        constraints.weightx = 1;
        sizeLabel = new JLabel(Integer.toString(fileSize));
        add(sizeLabel, constraints);
    }

    public void startDownload() {
        thisThread.start();
    }

    public synchronized void setSleepScheduled(boolean doSleep) {
        sleepScheduled = doSleep;
    }

    public synchronized boolean isSleepScheduled() {
        return sleepScheduled;
    }
    
    public synchronized void setSuspended(boolean suspend) {
        suspended = suspend;
    }

    public synchronized boolean isSuspended() {
        return this.suspended;
    }

    @Override
    public void run() {
        performDownload();
    }

    public void performDownload() {
        int byteCount;
        Runnable progressUpdate = () -> {
            progressBar.setValue(bytesRead);
            completeLabel.setText(
                    Integer.toString(bytesRead));
        };
        while ((bytesRead < fileSize) && (!isStopped())) {
            try {
                if (isSleepScheduled()) {
                    try {
                        Thread.sleep(SLEEP_TIME);
                        setSleepScheduled(false);
                    } catch (InterruptedException ie) {
                        setStopped(true);
                        break;
                    }
                }
                byteCount = inputStream.read(buffer);
                if (byteCount == -1) {
                    setStopped(true);
                    break;
                } else {
                    outputStream.write(buffer, 0,
                            byteCount);
                    bytesRead += byteCount;
                    SwingUtilities.invokeLater(
                            progressUpdate);
                }
            } catch (IOException ioe) {
                setStopped(true);
                JOptionPane.showMessageDialog(this,
                        ioe.getMessage(),
                        "I/O Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
            }
            synchronized (this) {
                if (isSuspended()) {
                    try {
                        this.wait();
                        setSuspended(false);
                    } catch (InterruptedException ie) {
                        setStopped(true);
                        break;
                    }
                }
            }
            if (Thread.interrupted()) {
                setStopped(true);
                break;
            }
        }
        try {
            outputStream.close();
            inputStream.close();
        } catch (IOException ioe) {};
    }
    
    public synchronized void resumeDownload() {
        this.notify();
    }
    
    public synchronized void setStopped(boolean stop) {
        stopped = stop;
    }

    public synchronized boolean isStopped() {
        return stopped;
    }
    
    public void stopDownload() {
        thisThread.interrupt();
    }
}
