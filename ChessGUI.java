// Necessary Java library imports for graphics
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

// GUI class intended to handle how the graphics work
public class ChessGUI{

   private JLabel[][] squares;
   private JLabel currentLabel;
   private JTextField chooseLabel;
   
   private JFrame mainFrame;
   
   private JTextField[] pawnsTaken;
   private JTextField[] rooksTaken;
   private JTextField[] bishopsTaken;
   private JTextField[] knightsTaken;
   private JTextField[] queensTaken;
   
   private ImageIcon[] takenIcons;
   private ImageIcon[] currentIcons;
   private ImageIcon blankIcon;

   private String[] pieceIcons;
   private String blank;
   
   private Color background = new Color(255,255,255);
   
   public final int NUMPLAYER = 2;
   
   public final int NUMROW = 8;
   
   public final int NUMCOL = 8;
   
   public final int NUMTYPE = 6;
   
   private final int PIECESIZE = 80;
   private final int SQUAREWIDTH = NUMCOL * PIECESIZE;
   private final int SQUAREHEIGHT = NUMROW * PIECESIZE;
   
   private final int INFOWIDTH = 2 * PIECESIZE;
   private final int INFOHEIGHT = SQUAREWIDTH;
   
   private final int LOGOHEIGHT = PIECESIZE - 20;
   private final int LOGOWIDTH = SQUAREWIDTH + PIECESIZE;
   
   private final int SCREENWIDTH = (int)(LOGOWIDTH * 1.5);
   private final int SCREENHEIGHT = (int)((LOGOHEIGHT + SQUAREHEIGHT) * 1.2);
   
   public ChessGUI () {
      initIcons();
      initColours();
      initSquares();
      createMainFrame();
   }
   
   private void initIcons(){
   
      try{
         BufferedReader in = new BufferedReader (new FileReader ("icons.txt"));
         
         pieceIcons = new String[NUMPLAYER*NUMTYPE];
         
         pieceIcons[0] = in.readLine();
         pieceIcons[1] = in.readLine();
         pieceIcons[2] = in.readLine();
         pieceIcons[3] = in.readLine();
         pieceIcons[4] = in.readLine();
         pieceIcons[5] = in.readLine();
         pieceIcons[6] = in.readLine();
         pieceIcons[7] = in.readLine();
         pieceIcons[8] = in.readLine();
         pieceIcons[9] = in.readLine();
         pieceIcons[10] = in.readLine();
         pieceIcons[11] = in.readLine();
         
         blank = in.readLine();
         
         in.close();
      }
      catch (IOException iox){
         System.out.println("Error reading file.");
      }
   }
   
   private void initColours(){
   
      takenIcons = new ImageIcon[NUMTYPE*NUMPLAYER];
      currentIcons = new ImageIcon[NUMPLAYER];
      for (int i = 0; i < NUMTYPE*NUMPLAYER; i++){
         takenIcons[i] = new ImageIcon(pieceIcons[i]);
         if (i == 0){
            currentIcons[0] = new ImageIcon(pieceIcons[i]);
         }
         else if (i == 6){
            currentIcons[1] = new ImageIcon(pieceIcons[i]);
         }
      }
      blankIcon = new ImageIcon(blank);
   }
   
   private void initSquares(){
   
      squares = new JLabel[NUMROW][NUMCOL];
      
      for (int i = 0; i < NUMROW; i++){
         for (int j = 0; j < NUMCOL; j++){
            squares[i][j] = new JLabel ();
            squares[i][j].setPreferredSize(new Dimension(PIECESIZE,PIECESIZE));
            squares[i][j].setHorizontalAlignment (SwingConstants.CENTER);
            squares[i][j].setBorder(new LineBorder (Color.black));
         }
      }
   }
   
   private JPanel createPlayPanel() {
   
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension (SQUAREWIDTH, SQUAREHEIGHT));
      panel.setBackground(background);
      panel.setLayout(new GridLayout(NUMROW,NUMCOL));
      for (int i = 0; i < NUMROW; i++){
         for (int j = 0 ; j < NUMCOL; j++){
            panel.add(squares[i][j]);
         }
      }
      return panel;
      
   }
   
   private JPanel createInfoPanel(){
     
      JPanel panel = new JPanel();
      panel.setPreferredSize(new Dimension(INFOWIDTH, INFOHEIGHT));
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.setBackground(background);
     
      Font headingFont = new Font ("Serif", Font.BOLD, 18);
      Font regularFont = new Font ("Serif", Font.BOLD, 20);
     
      JPanel piecePanel = new JPanel();
      piecePanel.setBackground(background);
      
      JLabel turnLabel = new JLabel ("Current Player");
      turnLabel.setFont(headingFont);
      turnLabel.setAlignmentX (Component.CENTER_ALIGNMENT);
      turnLabel.setForeground(Color.black);
      
      currentLabel = new JLabel();
      currentLabel = new JLabel(currentIcons[0]);
      currentLabel.setAlignmentX (Component.CENTER_ALIGNMENT);
      
      panel.add(turnLabel);
      panel.add(currentLabel);
      
      JLabel pieceLabel = new JLabel ("Pieces Taken");
      pieceLabel.setFont(headingFont);
      pieceLabel.setAlignmentX (Component.CENTER_ALIGNMENT);
      pieceLabel.setForeground(Color.black);
      
      JLabel[] pawnLabel = new JLabel[NUMPLAYER];
      pawnLabel[0] = new JLabel(takenIcons[0]);
      pawnLabel[0].setAlignmentX (Component.LEFT_ALIGNMENT);
      pawnLabel[1] = new JLabel(takenIcons[6]);
      pawnLabel[1].setAlignmentX (Component.RIGHT_ALIGNMENT);
      
      pawnsTaken = new JTextField[NUMPLAYER];
      for (int i = 0; i < NUMPLAYER; i++){
         pawnsTaken[i] = new JTextField();
         pawnsTaken[i].setFont(regularFont);
         pawnsTaken[i].setText("0");
         pawnsTaken[i].setEditable(false);
         pawnsTaken[i].setHorizontalAlignment (JTextField.CENTER);
         pawnsTaken[i].setPreferredSize (new Dimension (INFOWIDTH - PIECESIZE - 50, 30));
         pawnsTaken[i].setBackground(background);
         pawnsTaken[i].setBorder(new LineBorder (Color.black));
      }
      
      for (int i = 0; i < NUMPLAYER; i++){
         piecePanel.add(pawnLabel[i]);
         piecePanel.add(pawnsTaken[i]);
      }
      
      JLabel[] rookLabel = new JLabel[NUMPLAYER];
      rookLabel[0] = new JLabel(takenIcons[1]);
      rookLabel[0].setAlignmentX (Component.LEFT_ALIGNMENT);
      rookLabel[1] = new JLabel(takenIcons[7]);
      rookLabel[1].setAlignmentX (Component.RIGHT_ALIGNMENT);
      
      rooksTaken = new JTextField[NUMPLAYER];
      for (int i = 0; i < NUMPLAYER; i++){
         rooksTaken[i] = new JTextField();
         rooksTaken[i].setFont(regularFont);
         rooksTaken[i].setText("0");
         rooksTaken[i].setEditable(false);
         rooksTaken[i].setHorizontalAlignment (JTextField.CENTER);
         rooksTaken[i].setPreferredSize (new Dimension (INFOWIDTH - PIECESIZE - 50, 30));
         rooksTaken[i].setBackground(background);
         rooksTaken[i].setBorder(new LineBorder (Color.black));
      }
      
      for (int i = 0; i < NUMPLAYER; i++){
         piecePanel.add(rookLabel[i]);
         piecePanel.add(rooksTaken[i]);
      }
      
      JLabel[] knightLabel = new JLabel[NUMPLAYER];
      knightLabel[0] = new JLabel(takenIcons[2]);
      knightLabel[0].setAlignmentX (Component.LEFT_ALIGNMENT);
      knightLabel[1] = new JLabel(takenIcons[8]);
      knightLabel[1].setAlignmentX (Component.RIGHT_ALIGNMENT);
      
      knightsTaken = new JTextField[NUMPLAYER];
      for (int i = 0; i < NUMPLAYER; i++){
         knightsTaken[i] = new JTextField();
         knightsTaken[i].setFont(regularFont);
         knightsTaken[i].setText("0");
         knightsTaken[i].setEditable(false);
         knightsTaken[i].setHorizontalAlignment (JTextField.CENTER);
         knightsTaken[i].setPreferredSize (new Dimension (INFOWIDTH - PIECESIZE - 50, 30));
         knightsTaken[i].setBackground(background);
         knightsTaken[i].setBorder(new LineBorder (Color.black));
      }
      
      for (int i = 0; i < NUMPLAYER; i++){
         piecePanel.add(knightLabel[i]);
         piecePanel.add(knightsTaken[i]);
      }
      
      JLabel[] bishopLabel = new JLabel[NUMPLAYER];
      bishopLabel[0] = new JLabel(takenIcons[3]);
      bishopLabel[0].setAlignmentX (Component.LEFT_ALIGNMENT);
      bishopLabel[1] = new JLabel(takenIcons[9]);
      bishopLabel[1].setAlignmentX (Component.RIGHT_ALIGNMENT);
      
      bishopsTaken = new JTextField[NUMPLAYER];
      for (int i = 0; i < NUMPLAYER; i++){
         bishopsTaken[i] = new JTextField();
         bishopsTaken[i].setFont(regularFont);
         bishopsTaken[i].setText("0");
         bishopsTaken[i].setEditable(false);
         bishopsTaken[i].setHorizontalAlignment (JTextField.CENTER);
         bishopsTaken[i].setPreferredSize (new Dimension (INFOWIDTH - PIECESIZE - 50, 30));
         bishopsTaken[i].setBackground(background);
         bishopsTaken[i].setBorder(new LineBorder (Color.black));
      }
      
      for (int i = 0; i < NUMPLAYER; i++){
         piecePanel.add(bishopLabel[i]);
         piecePanel.add(bishopsTaken[i]);
      }
      
      JLabel[] queenLabel = new JLabel[NUMPLAYER];
      queenLabel[0] = new JLabel(takenIcons[4]);
      queenLabel[0].setAlignmentX (Component.LEFT_ALIGNMENT);
      queenLabel[1] = new JLabel(takenIcons[10]);
      queenLabel[1].setAlignmentX (Component.RIGHT_ALIGNMENT);
      
      queensTaken = new JTextField[NUMPLAYER];
      for (int i = 0; i < NUMPLAYER; i++){
         queensTaken[i] = new JTextField();
         queensTaken[i].setFont(regularFont);
         queensTaken[i].setText("0");
         queensTaken[i].setEditable(false);
         queensTaken[i].setHorizontalAlignment (JTextField.CENTER);
         queensTaken[i].setPreferredSize (new Dimension (INFOWIDTH - PIECESIZE - 50, 30));
         queensTaken[i].setBackground(background);
         queensTaken[i].setBorder(new LineBorder (Color.black));
      }
      
      for (int i = 0; i < NUMPLAYER; i++){
         piecePanel.add(queenLabel[i]);
         piecePanel.add(queensTaken[i]);
      }
         
      panel.add(pieceLabel);
      panel.add(piecePanel);
      
      return panel;
   }  
   
   private void createMainFrame() {
      mainFrame = new JFrame ("Chess");
      JPanel panel = (JPanel)mainFrame.getContentPane();
      panel.setLayout (new BoxLayout(panel, BoxLayout.Y_AXIS));
      
     
      JPanel logoPane = new JPanel();
      logoPane.setPreferredSize(new Dimension (LOGOWIDTH, LOGOHEIGHT));
      JLabel logo = new JLabel("CHESS");
      Font headingFont = new Font ("Serif", Font.BOLD, 18);
      logo.setFont(headingFont);
      logo.setAlignmentX (Component.CENTER_ALIGNMENT);
      logo.setForeground(Color.black);
      logo.setBackground(background);
      logoPane.add(logo);
      
      chooseLabel = new JTextField();
      chooseLabel.setFont(headingFont);
      chooseLabel.setText("Currently Selected: Nothing");
      chooseLabel.setEditable(false);
      chooseLabel.setPreferredSize (new Dimension (400,30));
      chooseLabel.setForeground(Color.black);
      chooseLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
      chooseLabel.setBorder(new LineBorder (background));
      
      logoPane.add(chooseLabel);
     
      JPanel playPane = new JPanel();
      playPane.setLayout(new BoxLayout(playPane, BoxLayout.X_AXIS));
      playPane.setPreferredSize(new Dimension(SQUAREWIDTH, SQUAREHEIGHT));
      playPane.add(createPlayPanel());
      playPane.add(createInfoPanel());
     
      panel.add(logoPane);
      panel.add(playPane);
     
      mainFrame.setContentPane(panel);
      mainFrame.setSize(SCREENWIDTH, SCREENHEIGHT);
      mainFrame.setVisible(true);
   }
   
   public void addListener (ChessListener listener) {
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            squares [i] [j].addMouseListener (listener);
         }
      }
   }

   
   public int rowDetect(JLabel label){
      int result = -1;
      for (int i = 0; i < NUMROW && result == -1; i++){
         for (int j = 0 ; j < NUMCOL && result == -1; j++){
            if (squares[i][j] == label){
               result = i;
            }
         }
      }
      return result;
   }
   
   public int colDetect(JLabel label){
      int result = -1;
      for (int i = 0; i < NUMROW && result == -1; i++){
         for (int j = 0 ; j < NUMCOL && result == -1; j++){
            if (squares[i][j] == label){
               result = j;
            }
         }
      }
      return result;
   }
   
   public void listenerCreate (ChessListener listener){
      for (int i = 0; i < NUMROW; i++){
         for (int j = 0; j < NUMCOL; j++){
            squares[i][j].addMouseListener (listener);
         }
      }
   }
   
   public void setPiece (int row, int col, int piece)
   {
         squares[row][col].setIcon(takenIcons[piece]);

   }
   public void removePiece (int row, int col)
   {
      squares[row][col].setIcon(null);
   }
   
   public void setNextPlayer(int player) {
      currentLabel.setIcon(currentIcons[player]);
   }
   
   public void setCurrentPiece(int piece) {
      switch (piece)
      {
      case -1:
         chooseLabel.setText("Currently selected: Nothing");
            JOptionPane.showMessageDialog(null, "Currently selected: Nothing", "Piece Selected", JOptionPane.PLAIN_MESSAGE, null); 
            break;
         case 0:
            chooseLabel.setText("Currently selected: Black Pawn");
            JOptionPane.showMessageDialog(null, "Currently selected: Black Pawn", "Piece Selected", JOptionPane.PLAIN_MESSAGE, null); 
            break;
         case 1:
            chooseLabel.setText("Currently selected: Black Rook");
            JOptionPane.showMessageDialog(null, "Currently selected: Black Rook", "Piece Selected", JOptionPane.PLAIN_MESSAGE, null); 
         
            break;
         case 2:
            chooseLabel.setText("Currently selected: Black Knight");
            JOptionPane.showMessageDialog(null, "Currently selected: Black Knight", "Piece Selected", JOptionPane.PLAIN_MESSAGE, null); 
         
            break;
         case 3:
            chooseLabel.setText("Currently selected: Black Bishop");
            JOptionPane.showMessageDialog(null, "Currently selected: Black Bishop", "Piece Selected", JOptionPane.PLAIN_MESSAGE, null); 
         
            break;
         case 4:
            chooseLabel.setText("Currently selected: Black Queen");
            JOptionPane.showMessageDialog(null, "Currently selected: Black Queen", "Piece Selected", JOptionPane.PLAIN_MESSAGE, null); 
         
            break;     
         case 5:
            chooseLabel.setText("Currently selected: Black King");
            JOptionPane.showMessageDialog(null, "Currently selected: Black King", "Piece Selected", JOptionPane.PLAIN_MESSAGE, null); 
         
            break;    
         case 6:
            chooseLabel.setText("Currently selected: White Pawn");
            JOptionPane.showMessageDialog(null, "Currently selected: White Pawn", "Piece Selected", JOptionPane.PLAIN_MESSAGE, null); 
         
            break;
         case 7:
            chooseLabel.setText("Currently selected: White Rook");
            JOptionPane.showMessageDialog(null, "Currently selected: White Rook", "Piece Selected", JOptionPane.PLAIN_MESSAGE, null); 
         
            break;      
         case 8:
            chooseLabel.setText("Currently selected: White Knight");
            JOptionPane.showMessageDialog(null, "Currently selected: White Knight", "Piece Selected", JOptionPane.PLAIN_MESSAGE, null); 
         
            break;
         case 9:
            chooseLabel.setText("Currently selected: White Bishop");
            JOptionPane.showMessageDialog(null, "Currently selected: White Bishop", "Piece Selected", JOptionPane.PLAIN_MESSAGE, null); 
         
            break;
         case 10:
            chooseLabel.setText("Currently selected: White Queen");
            JOptionPane.showMessageDialog(null, "Currently selected: White Queen", "Piece Selected", JOptionPane.PLAIN_MESSAGE, null); 
         
            break;
         case 11:
            chooseLabel.setText("Currently selected: White King");
            JOptionPane.showMessageDialog(null, "Currently selected: White King", "Piece Selected", JOptionPane.PLAIN_MESSAGE, null); 
            break;     
      }
   }
   
   public void pawnsTaken (int player, int pieces)
   {
      pawnsTaken[player].setText(pieces+"");
   }
   
   public void rooksTaken (int player, int pieces)
   {
      rooksTaken[player].setText(pieces+"");
   }
   
   public void knightsTaken (int player, int pieces)
   {
      knightsTaken[player].setText(pieces+"");
   }
   
   public void bishopsTaken (int player, int pieces)
   {
      bishopsTaken[player].setText(pieces+"");
   }
   
   public void queensTaken (int player, int pieces)
   {
      queensTaken[player].setText(pieces+"");
   }
   
   public void resetGameBoard() {
      for (int i = 0; i < NUMROW; i++) {
         for (int j = 0; j < NUMCOL; j++) {
            squares[i][j].setIcon(null);
         }
      }
   }

   public void showInvalidMoveMessage(){
      JOptionPane.showMessageDialog(null, " This move is invalid", "Invalid Move", JOptionPane.PLAIN_MESSAGE, null); 
   }


   
   public static void main (String[] args){
      ChessGUI gui = new ChessGUI ();
      Chess game = new Chess (gui);
      ChessListener listener = new ChessListener(game, gui);
   }
}
