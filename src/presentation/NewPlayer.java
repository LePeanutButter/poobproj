package presentation;

import java.awt.*;
import javax.swing.*;

public class NewPlayer extends JPanel {
    private final Image nameImage;
    private final Image field;
    private JButton ok;
    private JTextField text;

    public NewPlayer(int width, int height) {
        int panelWidth = width * 101 / 192;
        int panelHeight = height * panelWidth / width;
        int xPosition = width * 91 / 384;
        int yPosition = height * 259 / 1080;
        setBounds(xPosition, yPosition, panelWidth, panelHeight);
        nameImage = new ImageIcon("src/resources/imag/Menu/newUserPanel.png").getImage();
        field = new ImageIcon("src/resources/imag/Menu/nameBox.png").getImage();
        SwingUtilities.invokeLater(() -> {
            prepareTextField();
            setJButton();
        });
    }

    private void prepareTextField() {
        text = new JTextField(20);
        int fieldWidth = getWidth() * 423 / 505;
        int fieldHeight = getHeight() * fieldWidth / getWidth();
        text.setBounds(getWidth() * 41 / 505, getHeight() * 146 / 281, fieldWidth, fieldHeight);
        add(text);
    }

    private void setJButton() {
        Image okImage = new ImageIcon("src/resources/imag/Menu/OkButton.png").getImage();
        ok = new JButton();
        int width = okImage.getWidth(null);
        int height = okImage.getHeight(null);
        int newWidth = getWidth() * 199 / 505;
        int newHeight = height * newWidth / width;
        ImageIcon okScaled = new ImageIcon(okImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        ok.setBounds(getWidth() * 153 / 505, getHeight() * 208 / 281, okScaled.getIconWidth(), okScaled.getIconHeight());
        ok.setIcon(okScaled);
        ok.setFocusPainted(false);
        ok.setBorderPainted(false);
        ok.setContentAreaFilled(false);
        add(ok);
    }

    public String getText() {
        return text.getText();
    }

    public JButton getButton() {
        return ok;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawImage(nameImage, 0, 0, getWidth(), getHeight(), null);
        g2d.drawImage(field, text.getX(), text.getY(), text.getWidth(), text.getHeight(), null);
    }
}
