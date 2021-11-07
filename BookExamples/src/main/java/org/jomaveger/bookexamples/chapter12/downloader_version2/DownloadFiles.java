package org.jomaveger.bookexamples.chapter12.downloader_version2;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class DownloadFiles extends JPanel {

    private final JPanel listPanel;
    private final GridBagConstraints constraints;

    public static void main(String[] args) {
        JFrame f = new JFrame("Download Files");
        DownloadFiles df = new DownloadFiles();
        for (String arg : args) {
            df.createDownloader(arg);
        }
        f.getContentPane().add(df);
        f.setSize(600, 400);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public DownloadFiles() {
        setLayout(new BorderLayout());
        listPanel = new JPanel();
        listPanel.setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.weightx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.anchor = GridBagConstraints.NORTH;
        JScrollPane jsp = new JScrollPane(listPanel);
        add(jsp, BorderLayout.CENTER);
        add(getAddURLPanel(), BorderLayout.SOUTH);
    }

    private JPanel getAddURLPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("URL:");
        final JTextField textField = new JTextField(20);
        JButton downloadButton = new JButton("Download");
        ActionListener actionListener = (ActionEvent event) -> {
            if (createDownloader(textField.getText())) {
                textField.setText("");
                revalidate();
            }
        };
        textField.addActionListener(actionListener);
        downloadButton.addActionListener(actionListener);
        JButton clearAll = new JButton("Cancel All");
        clearAll.addActionListener((ActionEvent event) -> {
            Downloader.cancelAllAndWait();
            listPanel.removeAll();
            revalidate();
            repaint();
        });
        panel.add(label);
        panel.add(textField);
        panel.add(downloadButton);
        panel.add(clearAll);
        return panel;
    }

    private boolean createDownloader(String url) {
        try {
            URL downloadURL = new URL(url);
            URLConnection urlConn = downloadURL.openConnection();
            int length = urlConn.getContentLength();
            if (length < 0) {
                throw new Exception(
                        "Unable to determine content "
                        + "length for '" + url + "'");
            }
            int index = url.lastIndexOf('/');
            FileOutputStream fos = new FileOutputStream(
                    url.substring(index + 1));
            BufferedOutputStream bos
                    = new BufferedOutputStream(fos);
            DownloaderManager dm = new DownloaderManager(
                    downloadURL, bos);
            listPanel.add(dm, constraints);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),
                    "Unable To Download",
                    JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
