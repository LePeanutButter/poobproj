package presentation;

import domain.Clustering;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;

public class ClusteringGUI {
    private static Clustering clustering;
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenuItem newItem;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;
    private JFileChooser fileSelector;
    private JButton settings;
    private JButton newGame;
    private JLabel scoreNumber;
    private JLabel movesNumber;
    private JPanel board;
    
    public ClusteringGUI(){
        prepareElements();
        prepareActions();
    }

    public void prepareElements() {
        frame = new JFrame("Clustering");
        frame.setLayout(new BorderLayout());
        frame.setBackground(new Color(248, 248,238));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width/2;
        int height = screenSize.height/2;
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        prepareElementsMenu();
        prepareElementsBoard();
        JPanel eastPanel = new JPanel();
        JPanel westPanel = new JPanel();
        int panelWidth = (int) (frame.getWidth() * 0.31);
        eastPanel.setPreferredSize(new Dimension(panelWidth, frame.getHeight()));
        westPanel.setPreferredSize(new Dimension(panelWidth, frame.getHeight()));
        frame.add(BorderLayout.EAST, eastPanel);
        frame.add(BorderLayout.WEST, westPanel);
        JPanel southPanel = new JPanel();
        int panelHeight = frame.getHeight() / 16;
        southPanel.setPreferredSize(new Dimension(frame.getWidth(), panelHeight));
        frame.add(BorderLayout.SOUTH, southPanel);
        fileSelector = new JFileChooser();
    }

    public void prepareElementsMenu() {
        JMenu fileMenu = new JMenu("Opciones");
        newItem = new JMenuItem("Nuevo");
        openItem = new JMenuItem("Abrir");
        saveItem = new JMenuItem("Salvar");
        exitItem = new JMenuItem("Salir");      
        fileMenu.add(newItem);
        fileMenu.addSeparator();
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        JPanel top = getTopPanel();
        JPanel buttons = getButtonsPanel();
        header.add(top);
        header.add(buttons);
        frame.add(BorderLayout.PAGE_START, header);
    }

    private JPanel getTopPanel() {
        int panelSize = frame.getHeight() / 10;
        JPanel top = new JPanel();
        top.setLayout(new FlowLayout());
        JPanel score = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g.setColor(new Color(190, 172, 162));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
            }
        };
        score.setLayout(new BoxLayout(score, BoxLayout.Y_AXIS));
        score.setPreferredSize(new Dimension(panelSize, panelSize));
        JLabel scoreText = new JLabel("SCORE");
        scoreText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        scoreText.setForeground(Color.WHITE);
        score.add(scoreText);
        scoreNumber = new JLabel(String.valueOf(clustering.getScore()));
        scoreNumber.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        scoreNumber.setForeground(Color.WHITE);
        score.add(scoreNumber);
        JPanel moves = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g.setColor(new Color(190, 172, 162));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
            }
        };
        moves.setLayout(new BoxLayout(moves, BoxLayout.Y_AXIS));
        moves.setPreferredSize(new Dimension(panelSize, panelSize));
        JLabel movesText = new JLabel("MOVES");
        movesText.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        movesText.setForeground(Color.WHITE);
        moves.add(movesText);
        movesNumber = new JLabel(String.valueOf(clustering.getMoves()));
        movesNumber.setForeground(Color.WHITE);
        movesNumber.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        moves.add(movesNumber);
        top.add(score);
        top.add(moves);
        return top;
    }

    private JPanel getButtonsPanel() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout());
        settings = new JButton("Settings") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g.setColor(new Color(140, 121, 106));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                super.paintComponent(g);
            }
        };
        newGame = new JButton("New Game") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g.setColor(new Color(140, 121, 106));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
                super.paintComponent(g);
            }
        };
        settings.setBorderPainted(false);
        settings.setContentAreaFilled(false);
        settings.setFocusPainted(false);
        settings.setForeground(Color.WHITE);
        newGame.setBorderPainted(false);
        newGame.setContentAreaFilled(false);
        newGame.setFocusPainted(false);
        newGame.setForeground(Color.WHITE);
        buttons.add(settings);
        buttons.add(newGame);
        return buttons;
    }

    public void prepareElementsBoard() {
        board = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g.setColor(new Color(190, 172, 162));
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 15, 15));
            }
        };
        Color[][] tiles = clustering.getTilesColors();
        int row = tiles.length, column = tiles[0].length;
        board.setLayout(new GridLayout(row, column, 5, 5));
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                TileButton tile = getTileButton(tiles, i, j);
                board.add(tile);
            }
        }
        frame.add(BorderLayout.CENTER, board);
    }

    private TileButton getTileButton(Color[][] tiles, int i, int j) {
        Color initialColor = tiles[i][j] != null ? tiles[i][j] : new Color(206, 194, 182);
        TileButton tile = new TileButton(initialColor);
        if (Objects.equals(initialColor, new Color(206, 194, 182))) {
            tile.setEnabled(false);
        }
        tile.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e){
                Color color = JColorChooser.showDialog(frame, "Escoge un color:", initialColor);
                if (color != null) {
                    tile.changeColor(color);
                    clustering.changeTileColor(i, j, color);
                    refresh();
                }
                frame.requestFocusInWindow();
            }
        });
        return tile;
    }

    public void refresh() {
        Component[] components = board.getComponents();
        Color[][] tiles = clustering.getTilesColors();
        int column = tiles[0].length;
        int componentIndex = 0;
        for (Color[] tile : tiles) {
            for (int j = 0; j < column; j++) {
                Color color = tile[j] != null ? tile[j] : new Color(206, 194, 182);
                if (components[componentIndex] instanceof TileButton button) {
                    button.changeColor(color);
                    button.setEnabled(!Objects.equals(color, new Color(206, 194, 182)));
                }
                componentIndex++;
            }
        }
        scoreNumber.setText(String.valueOf(clustering.getScore()));
        movesNumber.setText(String.valueOf(clustering.getMoves()));
    }

    public void prepareActions() {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener (new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                actionClose();
            }
        });
        prepareActionsMenu();
    }

    public void prepareActionsMenu() {
        exitItem.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e){
            	actionClose();
            }
        });
        openItem.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e ){
            	actionOpen();
            }
        });
    	saveItem.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e ){
            	actionSave();
            }
        });

        newGame.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e ) {
                actionRestart();
                frame.requestFocusInWindow();
            }
        });

        newItem.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e ) {
                actionRestart();
                frame.requestFocusInWindow();
            }
        });

        settings.addActionListener( new ActionListener(){
            public void actionPerformed( ActionEvent e ) {
                openSettingsDialog();
                frame.requestFocusInWindow();
                refresh();
            }
        });

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        clustering.tilt('u');
                        refresh();
                        break;
                    case KeyEvent.VK_DOWN:
                        clustering.tilt('d');
                        refresh();
                        break;
                    case KeyEvent.VK_LEFT:
                        clustering.tilt('l');
                        refresh();
                        break;
                    case KeyEvent.VK_RIGHT:
                        clustering.tilt('r');
                        refresh();
                        break;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        frame.setFocusable(true);
        frame.setFocusTraversalKeysEnabled(false);
    }

    public void actionRestart(){
        int ans = JOptionPane.showConfirmDialog(null, "¿Desea reiniciar el tablero?", "Reiniciar tablero", JOptionPane.OK_CANCEL_OPTION);
        if (ans == JOptionPane.OK_OPTION){
            restart();
        }
    }
    public void restart(){
        clustering = new Clustering();
        refreshBoard();
    }
    public void actionOpen() {
    	File file = null;
    	int confirmation = fileSelector.showOpenDialog(openItem);
    	if (confirmation == JFileChooser.APPROVE_OPTION) {
    		file = fileSelector.getSelectedFile();
            clustering.loadGame(file);
            refreshBoard();
    	}
    }

    public void actionSave() {
    	File file = new File("board.ser");
        fileSelector.setSelectedFile(file);
    	int confirmation = fileSelector.showSaveDialog(saveItem);
    	if (confirmation == JFileChooser.APPROVE_OPTION) {
            file = fileSelector.getSelectedFile();
            if (file != null && !file.getName().endsWith(".ser")) {
                file = new File(file.getAbsolutePath() + ".ser");
            }
            clustering.saveGame(file);
        }
    }

    public void actionClose() {
        int confirm = JOptionPane.showConfirmDialog(null, "¿Seguro desea salir?","Cerrar",JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

    private void openSettingsDialog() {
        JDialog dimensionsDialog = new JDialog(frame, "Cambiar Dimensiones del Tablero", true);
        dimensionsDialog.setLayout(new GridLayout(4, 2, 5, 5));
        dimensionsDialog.setSize(300, 150);
        dimensionsDialog.setLocationRelativeTo(frame);
        JLabel rowsLabel = new JLabel("Número de filas:");
        JTextField rowsField = new JTextField("");
        JLabel colsLabel = new JLabel("Número de columnas:");
        JTextField colsField = new JTextField("");
        JLabel pcrgLabel = new JLabel("Porcentaje:");
        JTextField pcrgField = new JTextField("");
        JButton applyButton = new JButton("Aplicar");
        applyButton.addActionListener(e -> {
            try {
                int rows = Integer.parseInt(rowsField.getText());
                int columns = Integer.parseInt(colsField.getText());
                int percentage = Integer.parseInt(pcrgField.getText());
                clustering.resizeBoard(rows, columns, percentage);
                refreshBoard();
                dimensionsDialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dimensionsDialog, "Ingrese números válidos para las dimensiones.");
            }
        });
        dimensionsDialog.add(rowsLabel);
        dimensionsDialog.add(rowsField);
        dimensionsDialog.add(colsLabel);
        dimensionsDialog.add(colsField);
        dimensionsDialog.add(pcrgLabel);
        dimensionsDialog.add(pcrgField);
        dimensionsDialog.add(new JLabel());
        dimensionsDialog.add(applyButton);
        dimensionsDialog.setVisible(true);
    }

    private void refreshBoard() {
        frame.remove(board);
        prepareElementsBoard();
        frame.revalidate();
        frame.repaint();
        refresh();
    }

    public static void main(String[] args) {
        clustering = new Clustering();
        SwingUtilities.invokeLater(ClusteringGUI::new);
    }
}