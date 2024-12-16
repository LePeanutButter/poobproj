package presentation;

import domain.POOBVsZombiesException;
import java.awt.*;
import java.io.File;
import javax.swing.*;

public class MainMenu extends JPanel {
    private final JLayeredPane layeredPane;
    private Image backgroundImage;
    private Image cartelImage;
    private JButton quit;
    private static JButton save;
    private static JButton load;
    private static JButton pvp;
    private static JButton pvm;
    private static JButton mvm;
    private Sound backgroundMusic;

    public MainMenu() {
        super();
        layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        SwingUtilities.invokeLater(() -> {
            setLayout(new BorderLayout());
            add(layeredPane, BorderLayout.CENTER);
        });
    }

    public void preparePlayerName() {
        NewPlayer name = new NewPlayer(getWidth(), getHeight());
        SwingUtilities.invokeLater(() -> {
            name.ok.addActionListener(_ -> {
                String text = name.text.getText();
                if (text != null && !text.isEmpty()) {
                    POOBVsZombiesGUI.poobVsZombies.setPlayerName(text);
                    layeredPane.remove(name);
                    pvm.setEnabled(true);
                    pvp.setEnabled(true);
                    mvm.setEnabled(true);
                    save.setEnabled(true);
                    load.setEnabled(true);
                    SwingUtilities.invokeLater(() -> {
                        layeredPane.revalidate();
                        layeredPane.repaint();
                    });
                } else {
                    POOBVsZombiesGUI.buzzerSound();
                }
            });
            layeredPane.add(name, JLayeredPane.PALETTE_LAYER);
            SwingUtilities.invokeLater(() -> {
                layeredPane.revalidate();
                layeredPane.repaint();
            });
        });
    }

    public void prepareElements() {
        backgroundImage = new ImageIcon("src/resources/imag/Menu/mainMenu.png").getImage();
        cartelImage = new ImageIcon("src/resources/imag/Menu/Cartel.png").getImage();
        backgroundMusic = new Sound("src/resources/sound/soundtracks/mainMenu.wav");
        backgroundMusic.loop();
        prepareExitButton();
        prepareSaveButton();
        prepareLoadButton();
        preparePVPButton();
        preparePVMButton();
        prepareMvMButton();
        prepareActions();
        String playerName = POOBVsZombiesGUI.poobVsZombies.getPlayerName();
        if (playerName == null || playerName.isEmpty()) {
            preparePlayerName();
        }
        SwingUtilities.invokeLater(() -> {
            layeredPane.revalidate();
            layeredPane.repaint();
        });
    }

    private void prepareExitButton() {
        quit = new JButton();
        Image quit1Image = new ImageIcon("src/resources/imag/Menu/SelectorScreen_Quit1.png").getImage();
        Image quit2Image = new ImageIcon("src/resources/imag/Menu/SelectorScreen_Quit2.png").getImage();
        int width = quit1Image.getWidth(null);
        int height = quit1Image.getHeight(null);
        int newWidth = getWidth() * 17 / 384;
        int newHeight = height * newWidth / width;
        ImageIcon quit1 = new ImageIcon(quit1Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        ImageIcon quit2 = new ImageIcon(quit2Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        quit.setBounds(getWidth() * 1777 / 1920, getHeight() * 116 / 135, quit1.getIconWidth(), quit1.getIconHeight());
        quit.setIcon(quit1);
        quit.setRolloverIcon(quit2);
        quit.setFocusPainted(false);
        quit.setBorderPainted(false);
        quit.setContentAreaFilled(false);
        layeredPane.add(quit, JLayeredPane.DEFAULT_LAYER);
    }

    private void prepareSaveButton() {
        save = new JButton();
        Image save1Image = new ImageIcon("src/resources/imag/Menu/SelectorScreen_Save1.png").getImage();
        Image save2Image = new ImageIcon("src/resources/imag/Menu/SelectorScreen_Save2.png").getImage();
        int newWidth = getWidth() * 51 / 1280;
        int newHeight = getHeight() * 19 / 720;
        ImageIcon save1 = new ImageIcon(save1Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        ImageIcon save2 = new ImageIcon(save2Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        save.setBounds(getWidth() * 1102 / 1280, getHeight() * 630 / 720, save1.getIconWidth(), save1.getIconHeight());
        save.setIcon(save1);
        save.setDisabledIcon(save1);
        save.setRolloverIcon(save2);
        save.setFocusPainted(false);
        save.setBorderPainted(false);
        save.setContentAreaFilled(false);
        layeredPane.add(save, JLayeredPane.DEFAULT_LAYER);
    }

    private void prepareLoadButton() {
        load = new JButton();
        Image load1Image = new ImageIcon("src/resources/imag/Menu/SelectorScreen_Load1.png").getImage();
        Image load2Image = new ImageIcon("src/resources/imag/Menu/SelectorScreen_Load2.png").getImage();
        int newWidth = getWidth() * 59 / 1280;
        int newHeight = getHeight() * 18 / 720;
        ImageIcon load1 = new ImageIcon(load1Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        ImageIcon load2 = new ImageIcon(load2Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        load.setBounds(getWidth() * 1021 / 1280, getHeight() * 602 / 720, load1.getIconWidth(), load1.getIconHeight());
        load.setIcon(load1);
        load.setDisabledIcon(load1);
        load.setRolloverIcon(load2);
        load.setFocusPainted(false);
        load.setBorderPainted(false);
        load.setContentAreaFilled(false);
        layeredPane.add(load, JLayeredPane.DEFAULT_LAYER);
    }

    private void preparePVPButton() {
        pvp = new JButton();
        Image pvp1Image = new ImageIcon("src/resources/imag/Menu/PvP.png").getImage();
        Image pvp2Image = new ImageIcon("src/resources/imag/Menu/PvPHover.png").getImage();
        int width = pvp1Image.getWidth(null);
        int height = pvp1Image.getHeight(null);
        int newWidth = getWidth() * 387 / 1280;
        int newHeight = height * newWidth / width;
        ImageIcon pvp1 = new ImageIcon(pvp1Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        ImageIcon pvp2 = new ImageIcon(pvp2Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        pvp.setBounds(getWidth() * 817 / 1280, getHeight() * 93 / 720, pvp1.getIconWidth(), pvp1.getIconHeight());
        pvp.setIcon(pvp1);
        pvp.setDisabledIcon(pvp1);
        pvp.setRolloverIcon(pvp2);
        pvp.setFocusPainted(false);
        pvp.setBorderPainted(false);
        pvp.setContentAreaFilled(false);
        layeredPane.add(pvp, JLayeredPane.DEFAULT_LAYER);
    }

    private void preparePVMButton() {
        pvm = new JButton();
        Image pvm1Image = new ImageIcon("src/resources/imag/Menu/PvM.png").getImage();
        Image pvm2Image = new ImageIcon("src/resources/imag/Menu/PvMHover.png").getImage();
        int width = pvm1Image.getWidth(null);
        int height = pvm1Image.getHeight(null);
        int newWidth = getWidth() * 387 / 1280;
        int newHeight = height * newWidth / width;
        ImageIcon pvm1 = new ImageIcon(pvm1Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        ImageIcon pvm2 = new ImageIcon(pvm2Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        pvm.setBounds(getWidth() * 817 / 1280, getHeight() * 219 / 720, pvm1.getIconWidth(), pvm1.getIconHeight());
        pvm.setIcon(pvm1);
        pvm.setDisabledIcon(pvm1);
        pvm.setRolloverIcon(pvm2);
        pvm.setFocusPainted(false);
        pvm.setBorderPainted(false);
        pvm.setContentAreaFilled(false);
        layeredPane.add(pvm, JLayeredPane.DEFAULT_LAYER);
    }

    private void prepareMvMButton() {
        mvm = new JButton();
        Image mvm1Image = new ImageIcon("src/resources/imag/Menu/MvM.png").getImage();
        Image mvm2Image = new ImageIcon("src/resources/imag/Menu/MvMHover.png").getImage();
        int width = mvm1Image.getWidth(null);
        int height = mvm1Image.getHeight(null);
        int newWidth = getWidth() * 335 / 1280;
        int newHeight = height * newWidth / width;
        ImageIcon mvm1 = new ImageIcon(mvm1Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        ImageIcon mvm2 = new ImageIcon(mvm2Image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        mvm.setBounds(getWidth() * 822 / 1280, getHeight() * 319 / 720, mvm1.getIconWidth(), mvm1.getIconHeight());
        mvm.setIcon(mvm1);
        mvm.setDisabledIcon(mvm1);
        mvm.setRolloverIcon(mvm2);
        mvm.setFocusPainted(false);
        mvm.setBorderPainted(false);
        mvm.setContentAreaFilled(false);
        layeredPane.add(mvm, JLayeredPane.DEFAULT_LAYER);
    }

    private void prepareActions() {
        pvm.addActionListener(_ -> getSelector("PvM"));
        pvp.addActionListener(_ -> getSelector("PvP"));
        mvm.addActionListener(_ -> getSelector("MvM"));
        quit.addActionListener(_ -> System.exit(0));
        save.addActionListener(_ -> actionSave());
        load.addActionListener(_ -> actionLoad());
    }

    private void actionSave() {
        File file = new File("data.dat");
        JFileChooser fileSelector = new JFileChooser();
        fileSelector.setSelectedFile(file);
        int confirmation = fileSelector.showSaveDialog(save);
        if (confirmation == JFileChooser.APPROVE_OPTION) {
            file = fileSelector.getSelectedFile();
            if (file != null && !file.getName().endsWith(".dat")) {
                file = new File(file.getAbsolutePath() + ".dat");
            }
            POOBVsZombiesGUI.poobVsZombies.saveData(file);
        }
    }

    private void actionLoad() {
        File file;
        JFileChooser fileSelector = new JFileChooser();
    	int confirmation = fileSelector.showOpenDialog(load);
    	if (confirmation == JFileChooser.APPROVE_OPTION) {
    		file = fileSelector.getSelectedFile();
            POOBVsZombiesGUI.poobVsZombies.loadData(file);
            SwingUtilities.invokeLater(() -> {
                revalidate();
                repaint();
            });
    	}
    }

    private void getSelector(String gameMode) {
        try {
            playGame(gameMode, "", -1, -1, -1, "");
        } catch (POOBVsZombiesException _) {
            Selector selector = new Selector(getWidth(), getHeight(), gameMode);
            layeredPane.add(selector, JLayeredPane.PALETTE_LAYER);
            SwingUtilities.invokeLater(() -> {
                selector.play.addActionListener(_ -> {
                    switch (gameMode) {
                        case "PvM" -> setPlayActionPvM(selector, gameMode);
                        case "PvP" -> setPlayActionPvP(selector, gameMode);
                        case "MvM" -> setPlayActionMvM(selector, gameMode);
                    }
                    });
                selector.cancel.addActionListener(_ -> setCancelAction(selector));
                revalidate();
                repaint();
            });
        }
    }

    private void setPlayActionPvM(Selector selector, String gameMode) {
        boolean canPlay = true;
        String lawn = "";
        int resources = -1, waves = -1, time = -1;
        for (int i = 0; i < 4; i++) {
            JTextField textField = selector.text[i];
            String text = textField.getText();
            if (text.isEmpty()) {
                canPlay = false;
                POOBVsZombiesGUI.buzzerSound();
                break;
            }
            switch (i) {
                case 0 -> lawn = text;
                case 1 -> resources = Integer.parseInt(text);
                case 2 -> waves = Integer.parseInt(text);
                default -> time = Integer.parseInt(text);
            }
        }
        if (canPlay) {
            try {
                playGame(gameMode, lawn, resources, waves, time, "");
            } catch (POOBVsZombiesException _) {
                layeredPane.remove(selector);
                pvm.setEnabled(true);
                pvp.setEnabled(true);
                mvm.setEnabled(true);
                revalidate();
                repaint();
            }
        }
    }

    private void setPlayActionPvP(Selector selector, String gameMode) {
        boolean canPlay = true;
        String lawn = "";
        int resources = -1, time = -1;
        for (int i = 0; i < 3; i++) {
            JTextField textField = selector.text[i];
            String text = textField.getText();
            if (text.isEmpty()) {
                canPlay = false;
                POOBVsZombiesGUI.buzzerSound();
                break;
            }
            switch (i) {
                case 0 -> lawn = text;
                case 1 -> resources = Integer.parseInt(text);
                default -> time = Integer.parseInt(text);
            }
        }
        if (canPlay) {
            try {
                playGame(gameMode, lawn, resources, 2, time, "");
            } catch (POOBVsZombiesException _) {
                layeredPane.remove(selector);
                pvm.setEnabled(true);
                pvp.setEnabled(true);
                mvm.setEnabled(true);
                revalidate();
                repaint();
            }
        }
    }

    private void setPlayActionMvM(Selector selector, String gameMode) {
        boolean canPlay = true;
        String lawn = "", machine = "";
        int resources = -1, time = -1;
        for (int i = 0; i < 4; i++) {
            JTextField textField = selector.text[i];
            String text = textField.getText();
            if (text.isEmpty()) {
                canPlay = false;
                POOBVsZombiesGUI.buzzerSound();
                break;
            }
            switch (i) {
                case 0 -> lawn = text;
                case 1 -> resources = Integer.parseInt(text);
                case 2 -> machine = text;
                default -> time = Integer.parseInt(text);
            }
        }
        if (canPlay) {
            try {
                playGame(gameMode, lawn, resources, -1, time, machine);
            } catch (POOBVsZombiesException _) {
                layeredPane.remove(selector);
                pvm.setEnabled(true);
                pvp.setEnabled(true);
                mvm.setEnabled(true);
                revalidate();
                repaint();
            }
        }
    }

    private void setCancelAction(Selector selector) {
        layeredPane.remove(selector);
        pvm.setEnabled(true);
        pvp.setEnabled(true);
        mvm.setEnabled(true);
        revalidate();
        repaint();
    }

    private void playGame(String gameMode, String lawn, int resources, int waves, int time, String machine) throws POOBVsZombiesException {
        switch (gameMode) {
            case "PvM" -> addPvM(lawn, resources, waves, time);
            case "PvP" -> addPvP(lawn, resources, waves, time);
            case "MvM" -> addMvM(lawn, resources, time, machine);
            default -> {
            }
        }
    }

    private void addPvM(String lawn, int sun, int waves, int time) throws POOBVsZombiesException {
        if (POOBVsZombiesGUI.poobVsZombies.hasActiveMatch("PvM")) {
            PvMGUI pvMGUI = new PvMGUI(POOBVsZombiesGUI.poobVsZombies.play("PvM"));
            pvMGUI.setSize(POOBVsZombiesGUI.dimensions);
            SwingUtilities.invokeLater(() -> {
                POOBVsZombiesGUI.switchToPanel(pvMGUI);
                backgroundMusic.stopClip();
            });
        } else {
            PvMGUI pvMGUI = new PvMGUI(POOBVsZombiesGUI.poobVsZombies.play("PvM", lawn, sun, waves, time, ""), lawn);
            pvMGUI.setSize(POOBVsZombiesGUI.dimensions);
            SwingUtilities.invokeLater(() -> {
                POOBVsZombiesGUI.switchToPanel(pvMGUI);
                backgroundMusic.stopClip();
            });
        }
    }

    private void addPvP(String lawn, int sun, int waves, int time) throws POOBVsZombiesException {
        if (POOBVsZombiesGUI.poobVsZombies.hasActiveMatch("PvP")) {
            PvPGUI pvPGUI = new PvPGUI(POOBVsZombiesGUI.poobVsZombies.play("PvP"));
            pvPGUI.setSize(POOBVsZombiesGUI.dimensions);
            SwingUtilities.invokeLater(() -> {
                POOBVsZombiesGUI.switchToPanel(pvPGUI);
                backgroundMusic.stopClip();
            });
        } else {
            PvPGUI pvPGUI = new PvPGUI(POOBVsZombiesGUI.poobVsZombies.play("PvP", lawn, sun, waves, time, ""), lawn);
            pvPGUI.setSize(POOBVsZombiesGUI.dimensions);
            SwingUtilities.invokeLater(() -> {
                POOBVsZombiesGUI.switchToPanel(pvPGUI);
                backgroundMusic.stopClip();
            });
        }
    }

    private void addMvM(String lawn, int sun, int time, String machine) throws POOBVsZombiesException {
        if (POOBVsZombiesGUI.poobVsZombies.hasActiveMatch("MvM")) {
            MvMGUI mvMGUI = new MvMGUI(POOBVsZombiesGUI.poobVsZombies.play("MvM"));
            mvMGUI.setSize(POOBVsZombiesGUI.dimensions);
            SwingUtilities.invokeLater(() -> {
                POOBVsZombiesGUI.switchToPanel(mvMGUI);
                backgroundMusic.stopClip();
            });
        } else {
            MvMGUI mvMGUI = new MvMGUI(POOBVsZombiesGUI.poobVsZombies.play("MvM", lawn, sun, -1, time, machine), lawn);
            mvMGUI.setSize(POOBVsZombiesGUI.dimensions);
            SwingUtilities.invokeLater(() -> {
                POOBVsZombiesGUI.switchToPanel(mvMGUI);
                backgroundMusic.stopClip();
            });
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        g2d.setFont(POOBVsZombiesGUI.briannesHand);
        g2d.setColor(Color.WHITE);

        //Cartel
        int originalWidth = cartelImage.getWidth(null);
        int originalHeight = cartelImage.getHeight(null);
        int newWidth = getWidth() * 247 / 960;
        int newHeight = originalHeight * newWidth / originalWidth;
        int xPosition = (int) (getWidth() * 0.05);
        g2d.drawImage(cartelImage, xPosition, 0, newWidth, newHeight, null);

        // User Name
        String playerName = POOBVsZombiesGUI.poobVsZombies.getPlayerName();
        if (playerName != null && !playerName.isEmpty()) {
            float customSize = (float) (getHeight() * 24) / 720;
            Font customFont = POOBVsZombiesGUI.briannesHand.deriveFont(customSize);
            g2d.setFont(customFont);
            int textMiddle = g2d.getFontMetrics().stringWidth(playerName) / 2;
            int cartelMiddle = getWidth() * 233 / 1280;
            int textXPosition = cartelMiddle - textMiddle;
            int textYPosition = getHeight() * 124 / 720;
            g2d.drawString(playerName, textXPosition, textYPosition);
        }
    }

    private static class NewPlayer extends JPanel {
        private final JLayeredPane playerPane;
        private JButton ok;
        private JTextField text;

        public NewPlayer(int width, int height) {
            pvp.setEnabled(false);
            pvm.setEnabled(false);
            mvm.setEnabled(false);
            save.setEnabled(false);
            load.setEnabled(false);
            playerPane = new JLayeredPane();
            playerPane.setLayout(null);
            int panelWidth = width * 101 / 192;
            int panelHeight = height * panelWidth / width;
            int xPosition = width * 91 / 384;
            int yPosition = height * 259 / 1080;
            SwingUtilities.invokeLater(() -> {
                setLayout(new BorderLayout());
                add(playerPane, BorderLayout.CENTER);
                setOpaque(false);
                setBounds(xPosition, yPosition, panelWidth, panelHeight);
                prepareBackground();
                prepareTextField();
                setJButton();
            });
        }

        private void prepareBackground() {
            Image name = new ImageIcon("src/resources/imag/Menu/newUserPanel.png").getImage();
            ImageIcon nameScaled = new ImageIcon(name.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
            JLabel nameLabel = new JLabel();
            nameLabel.setBounds(0, 0, getWidth(), getHeight());
            nameLabel.setIcon(nameScaled);
            playerPane.add(nameLabel, JLayeredPane.DEFAULT_LAYER);
        }

        private void prepareTextField() {
            final JLabel field = setJLabel();
            playerPane.add(field, JLayeredPane.PALETTE_LAYER);
            text = new JTextField(20);
            int fieldWidth = getWidth() * 820 / 1010;
            int fieldHeight = getHeight() * 53 / 562;
            float fontSize = (float) (getHeight() * 40) / 562;
            text.setFont(POOBVsZombiesGUI.briannesHand.deriveFont(fontSize));
            text.setForeground(Color.WHITE);
            text.setBounds(getWidth() * 92 / 1010, getHeight() * 302 / 562, fieldWidth, fieldHeight);
            text.setBorder(BorderFactory.createEmptyBorder());
            text.setCaretColor(Color.WHITE);
            text.setOpaque(false);
            playerPane.add(text, JLayeredPane.PALETTE_LAYER);
            playerPane.moveToFront(text);
        }

        private JLabel setJLabel() {
            int boxWidth = getWidth() * 864 / 1010;
            int boxHeight = getHeight() * 72 / 562;
            Image nameBox = new ImageIcon("src/resources/imag/Menu/nameBox.png").getImage();
            ImageIcon nameBoxScaled = new ImageIcon(nameBox.getScaledInstance(boxWidth, boxHeight, Image.SCALE_SMOOTH));
            JLabel field = new JLabel();
            field.setBounds(getWidth() * 79 / 1010, getHeight() * 292 / 562, boxWidth, boxHeight);
            field.setIcon(nameBoxScaled);
            field.setOpaque(false);
            return field;
        }

        private void setJButton() {
            Image okImage = new ImageIcon("src/resources/imag/Menu/OkButton.png").getImage();
            ok = new JButton();
            int newWidth = getWidth() * 398 / 1010;
            int newHeight = getHeight() * 88 / 562;
            ImageIcon okScaled = new ImageIcon(okImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            ok.setBounds(getWidth() * 306 / 1010, getHeight() * 416 / 562, okScaled.getIconWidth(), okScaled.getIconHeight());
            ok.setIcon(okScaled);
            ok.setFocusPainted(false);
            ok.setBorderPainted(false);
            ok.setContentAreaFilled(false);
            playerPane.add(ok, JLayeredPane.PALETTE_LAYER);
        }
    }

    private static class Selector extends JPanel {
        private final JLayeredPane selectorPane;
        private JButton play;
        private JButton cancel;
        private JTextField[] text;

        public Selector(int width, int height, String gameMode) {
            pvp.setEnabled(false);
            pvm.setEnabled(false);
            mvm.setEnabled(false);
            selectorPane = new JLayeredPane();
            selectorPane.setLayout(null);
            int panelWidth = width * 785 / 1280;
            int panelHeight = height * 634 / 720;
            int xPosition = width * 254 / 1280;
            int yPosition = height * 59 / 720;
            SwingUtilities.invokeLater(() -> {
                setLayout(new BorderLayout());
                add(selectorPane, BorderLayout.CENTER);
                setOpaque(false);
                setBounds(xPosition, yPosition, panelWidth, panelHeight);
                setJButton();
                switch (gameMode) {
                    case "PvM" -> {
                        prepareBackground();
                        prepareTextField();
                    }
                    case "PvP" -> {
                        prepareBackgroundPvP();
                        prepareTextFieldPvP();
                    }
                    default -> {
                        prepareBackgroundMvM();
                        prepareTextFieldMvM();
                    }
                }
            });
        }

        private void prepareBackground() {
            Image panel = new ImageIcon("src/resources/imag/Menu/Selector.png").getImage();
            ImageIcon panelScaled = new ImageIcon(panel.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
            JLabel panelLabel = new JLabel();
            panelLabel.setBounds(0, 0, getWidth(), getHeight());
            panelLabel.setIcon(panelScaled);
            selectorPane.add(panelLabel, JLayeredPane.DEFAULT_LAYER);
        }

        private void prepareBackgroundPvP() {
            Image panel = new ImageIcon("src/resources/imag/Menu/SelectorPvP.png").getImage();
            ImageIcon panelScaled = new ImageIcon(panel.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
            JLabel panelLabel = new JLabel();
            panelLabel.setBounds(0, 0, getWidth(), getHeight());
            panelLabel.setIcon(panelScaled);
            selectorPane.add(panelLabel, JLayeredPane.DEFAULT_LAYER);
        }

        private void prepareBackgroundMvM() {
            Image panel = new ImageIcon("src/resources/imag/Menu/SelectorMvM.png").getImage();
            ImageIcon panelScaled = new ImageIcon(panel.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH));
            JLabel panelLabel = new JLabel();
            panelLabel.setBounds(0, 0, getWidth(), getHeight());
            panelLabel.setIcon(panelScaled);
            selectorPane.add(panelLabel, JLayeredPane.DEFAULT_LAYER);
        }

        private void prepareTextField() {
            text = new JTextField[4];
            for (int i = 0; i < 4; i++) {
                text[i] = new JTextField(20);
                int fieldWidth = getWidth() * 576 / 785;
                int fieldHeight = getHeight() * 33 / 634;
                float fontSize = (float) (getHeight() * 20) / 634;
                text[i].setFont(POOBVsZombiesGUI.briannesHand.deriveFont(fontSize));
                text[i].setForeground(Color.WHITE);
                text[i].setBounds(getWidth() * 98 / 785, getHeight() * (151 + (99 * i)) / 634, fieldWidth, fieldHeight);
                text[i].setBorder(BorderFactory.createEmptyBorder());
                text[i].setCaretColor(Color.WHITE);
                text[i].setOpaque(false);
                selectorPane.add(text[i], JLayeredPane.PALETTE_LAYER);
            }
        }

        private void prepareTextFieldPvP() {
            text = new JTextField[3];
            for (int i = 0; i < 3; i++) {
                text[i] = new JTextField(20);
                int fieldWidth = getWidth() * 576 / 785;
                int fieldHeight = getHeight() * 33 / 634;
                float fontSize = (float) (getHeight() * 20) / 634;
                text[i].setFont(POOBVsZombiesGUI.briannesHand.deriveFont(fontSize));
                text[i].setForeground(Color.WHITE);
                text[i].setBounds(getWidth() * 98 / 785, getHeight() * (151 + (99 * i)) / 634, fieldWidth, fieldHeight);
                text[i].setBorder(BorderFactory.createEmptyBorder());
                text[i].setCaretColor(Color.WHITE);
                text[i].setOpaque(false);
                selectorPane.add(text[i], JLayeredPane.PALETTE_LAYER);
            }
        }

        private void prepareTextFieldMvM() {
            text = new JTextField[4];
            for (int i = 0; i < 4; i++) {
                text[i] = new JTextField(20);
                int fieldWidth = getWidth() * 576 / 785;
                int fieldHeight = getHeight() * 33 / 634;
                float fontSize = (float) (getHeight() * 20) / 634;
                text[i].setFont(POOBVsZombiesGUI.briannesHand.deriveFont(fontSize));
                text[i].setForeground(Color.WHITE);
                text[i].setBounds(getWidth() * 98 / 785, getHeight() * (151 + (99 * i)) / 634, fieldWidth, fieldHeight);
                text[i].setBorder(BorderFactory.createEmptyBorder());
                text[i].setCaretColor(Color.WHITE);
                text[i].setOpaque(false);
                selectorPane.add(text[i], JLayeredPane.PALETTE_LAYER);
            }
        }

        private void setJButton() {
            setPlayButton();
            setCancelButton();
        }

        private void setPlayButton() {
            play = new JButton();
            Image playImage = new ImageIcon("src/resources/imag/Menu/Play.png").getImage();
            int newWidth = getWidth() * 285 / 785;
            int newHeight = getHeight() * 62 / 634;
            ImageIcon playScaled = new ImageIcon(playImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            play.setBounds(getWidth() * 410 / 785, getHeight() * 541 / 634, playScaled.getIconWidth(), playScaled.getIconHeight());
            play.setIcon(playScaled);
            play.setFocusPainted(false);
            play.setBorderPainted(false);
            play.setContentAreaFilled(false);
            selectorPane.add(play, JLayeredPane.PALETTE_LAYER);
        }

        private void setCancelButton() {
            cancel = new JButton();
            Image playImage = new ImageIcon("src/resources/imag/Menu/Cancel.png").getImage();
            int newWidth = getWidth() * 285 / 785;
            int newHeight = getHeight() * 62 / 634;
            ImageIcon playScaled = new ImageIcon(playImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            cancel.setBounds(getWidth() * 76 / 785, getHeight() * 541 / 634, playScaled.getIconWidth(), playScaled.getIconHeight());
            cancel.setIcon(playScaled);
            cancel.setFocusPainted(false);
            cancel.setBorderPainted(false);
            cancel.setContentAreaFilled(false);
            selectorPane.add(cancel, JLayeredPane.PALETTE_LAYER);
        }
    }
}
