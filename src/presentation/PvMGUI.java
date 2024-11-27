package presentation;

import domain.PvM;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PvMGUI extends LevelGUI {
    private BufferedImage barProgress;
    private Image progressBar;
    private static Shovel shovel;
    Long startTime;

    public PvMGUI(PvM level, String lawn, Dimension dimension) {
        super(level, lawn, dimension);
        SwingUtilities.invokeLater(this::prepareElements);
        startTimer();
    }

    public PvMGUI(PvM level, Dimension dimension) {
        super(level);
        setPreferredSize(dimension);
        SwingUtilities.invokeLater(this::prepareElements);
        startTimer();
    }
    
    private void prepareShovelButton(){
        int shovelWidth = getWidth() * 69 / 1280;
        int shovelHeight = getHeight() * 75 / 720;
        int shovelXPosition = getWidth() * 1156 / 1280;
        int shovelYPosition = getHeight() * 634 / 720;
        shovel = new Shovel(shovelWidth, shovelHeight);
        shovel.setBounds(shovelXPosition, shovelYPosition, shovelWidth, shovelHeight);
        int bankWidth = getWidth() * 99 / 1280;
        int bankHeight = getHeight() * 102 / 720;
        int bankXPosition = getWidth() * 1141 / 1280;
        int bankYPosition = getHeight() * 620 / 720;
        shovel.prepareShovelBank(bankWidth, bankHeight, bankXPosition, bankYPosition);
        shovel.addActionListener(_ -> {
            changeBoardState(true);
            currentAction = "remove";
            shovel.setVisible(false);
        });
        add(shovel);
    }

    public static void setShovelVisibility(boolean state) {
        shovel.setVisible(state);
    }

    public void prepareElements() {
        prepareShovelButton();
        progressBar = new ImageIcon().getImage();
        try{
            barProgress = ImageIO.read(new File("src/resources/imag/Level Interface/FlagMeterFull.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startTimer() {
        readySetPlant();
        startTime = System.currentTimeMillis();
        Timer timer = new Timer(100 / 3, _ -> refresh());
        TimerTask stopTask = new TimerTask() {
            @Override
            public void run() {
                timer.stop();
            }
        };
        java.util.Timer stopTimer = new java.util.Timer();
        stopTimer.schedule(stopTask, 600000);
        setSunTimer();
        timer.start();

    }

    public String getTimeElapsed() {
        String timerText = null;
        if (startTime != null) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long minutes = (elapsedTime / 1000) / 60;
            long seconds = (elapsedTime / 1000) % 60;
            timerText = String.format("%02d:%02d", minutes, seconds);
        }
        return timerText;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // shovel Bank
        Image shovelBank = shovel.getShovelBank();
        int[] shovelBankPosition = shovel.getShovelBankPosition();
        int[] shovelBankSize = shovel.getShovelBankSize();
        g2d.drawImage(shovelBank, shovelBankPosition[0], shovelBankPosition[1], shovelBankSize[0], shovelBankSize[1],null);

        // Progress bar


        // Wave Progress Bar
        int xPositionBarProgress = getWidth() * 444 / 1280;
        int yPositionBarProgress = getHeight() * 8 / 720;
        int barProgressWidth = barProgress.getWidth();
        int barProgressHeight = barProgress.getHeight();
        int newBarProgressWidth = getWidth() * 231 / 1280;
        int newBarProgressHeight = barProgressHeight * newBarProgressWidth / barProgressWidth;
        int waveProgress = ((PvM) level).getWaveProgress();
        int bufferWidth = newBarProgressWidth * waveProgress / 100;
        if (waveProgress > 0 && waveProgress < 100) {
            BufferedImage waveSubimage = barProgress.getSubimage(0, 0, barProgressWidth * waveProgress / 100, barProgressHeight);
            g2d.drawImage(waveSubimage, xPositionBarProgress, yPositionBarProgress, bufferWidth, newBarProgressHeight, null);
        } else if (waveProgress >= 100) {
            g2d.drawImage(barProgress, xPositionBarProgress, yPositionBarProgress, newBarProgressWidth, newBarProgressHeight, null);
            ((PvM) level).updateWave();
        }

        // Elapsed time
        g2d.drawString(getTimeElapsed(), 200, 30);
        }

    private static class Shovel extends JButton {
        private Image shovelBank;
        private int[] shovelBankPosition;
        private int[] shovelBankSize;

        private Shovel(int width, int height) {
            Image shovelImage = new ImageIcon("src/resources/imag/Level Interface/Shovel_hi_res.png").getImage();
            ImageIcon shovel = new ImageIcon(shovelImage.getScaledInstance(width, height, Image.SCALE_SMOOTH));
            setIcon(shovel);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
        }

        private void prepareShovelBank(int width, int height, int xPosition, int yPosition) {
            shovelBank = new ImageIcon("src/resources/imag/Level Interface/ShovelBank.png").getImage();
            shovelBankPosition = new int[]{xPosition, yPosition};
            shovelBankSize = new int[]{width, height};
        }

        private Image getShovelBank() {
            return shovelBank;
        }

        private int[] getShovelBankPosition() {
            return shovelBankPosition;
        }

        private int[] getShovelBankSize() {
            return shovelBankSize;
        }
    }
}
    