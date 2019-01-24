// Developed mostly during the summer of 2017

public class Chess
{
	// rook doesn't work properly lol
   // so does bishop and queen diagonal... eats own team 
   // fix array out of bounds for misclick of piece (white turn, picks black)
	// promotion doesn't work
	// check doesn't work
	// checkmate doesn't work
   final int PLAYERS;
   final int PLAYER1 = 0;
   final int PLAYER2 = 1;
   final int MAXCOL;
   final int MAXROW;
   final int TYPES = 6;
   final int EMPTY = -1;
   
   int selRow;
   int selCol;
   
   int pawnsW = 0;
   int pawnsB = 0;
   int rooksW = 0;
   int rooksB = 0;
   int knightsW = 0;
   int knightsB = 0;
   int bishopsW = 0;
   int bishopsB = 0;
   int queensW = 0;
   int queensB = 0;

   boolean select = false;

   ChessGUI gui;
   int curPlayer;
   int board[][];
   int check[][];

   public Chess (ChessGUI gui)
   {
      this.gui = gui;
      PLAYERS = gui.NUMPLAYER;
      MAXCOL = gui.NUMCOL;
      MAXROW = gui.NUMROW;
      board = new int [MAXROW][MAXCOL];
      check = new int [MAXROW][MAXCOL];
      for (int i = 0 ; i < MAXROW; i++)
      {
         for (int j = 0; j < MAXCOL; j++)
         {
            board[i][j] = -1;
            check[i][j] = 0;
         }
      }
      curPlayer = PLAYER1;
      setBoard();
   }

   // Method which handles the actual playing of the game (selection of piece and movement of said piece)
   public void play (int row, int col)
   {
      if (!select && board[row][col] != -1 && validPiece(board[row][col], curPlayer))
      {
         gui.setCurrentPiece(board[row][col]);
         selRow = row;
         selCol = col;
         select = true;
      }
      else
      {
         if (row == selRow && col == selCol)
         {
            select = false;
            gui.setCurrentPiece(EMPTY);
         }
         if (validMove(row,col, selRow, selCol, board[selRow][selCol]))
         {
            select = false;
            if (curPlayer == PLAYER1)
            {
               curPlayer = PLAYER2;
            }
            else
            {
               curPlayer = PLAYER1;
            }
            gui.setNextPlayer(curPlayer);
            gui.setCurrentPiece(EMPTY);
            selRow = EMPTY;
            selCol = EMPTY;
         }
      
      }
   }

   // Method which (re)sets the board state
   public void setBoard()
   {
      gui.setPiece(0,0,1);
      board[0][0] = 1;
      gui.setPiece(0,1,2);
      board[0][1] = 2;
      gui.setPiece(0,2,3);
      board[0][2] = 3;
      gui.setPiece(0,3,4);
      board[0][3] = 4;
      gui.setPiece(0,4,5);
      board[0][4] = 5;
      gui.setPiece(0,5,3);
      board[0][5] = 3;
      gui.setPiece(0,6,2);
      board[0][6] = 2;
      gui.setPiece(0,7,1);
      board[0][7] = 1;
   
      for (int i = 0; i < MAXROW; i++)
      {
         gui.setPiece(1,i,0);
         board[1][i] = 0;
      }
   
      gui.setPiece(7,0,7);
      board[7][0] = 7;
      gui.setPiece(7,1,8);
      board[7][1] = 8;
      gui.setPiece(7,2,9);
      board[7][2] = 9;
      gui.setPiece(7,3,10);
      board[7][3] = 10;
      gui.setPiece(7,4,11);
      board[7][4] = 11;
      gui.setPiece(7,5,9);
      board[7][5] = 9;
      gui.setPiece(7,6,8);
      board[7][6] = 8;
      gui.setPiece(7,7,7);
      board[7][7] = 7;
   
      for (int i = 0; i < MAXROW; i++)
      {
         gui.setPiece(6,i,6);
         board[6][i] = 6;
      }
   
      for (int i = 0 ; i < MAXROW; i++)
      {
         for (int j = 0 ; j < MAXCOL; j++)
         {
            if (board[i][j] != -1)
            {
               check[i][j] = 1;
            }
         }
      }
   }
   
   // Method to indicate if it is a valid move based on the piece
   public boolean validMove(int row, int col, int selRow, int selCol, int piece)
   {
      int greater, lower, difference;
      int counter = 0;
      boolean allow = false;
   
      // black pawn - move (make it so it's only the first move that can be two moves, also attack)
      if (piece == 0)
      {
         if (selRow == 1)
         {
         // checks if square in front is free or if 2 in front is free, and nothing is directly in front
            if ((selRow+1 == row || (selRow+2 == row && check[selRow+1][col] == 0)) && selCol == col && check[row][col] == 0)
            {
               gui.setPiece(row,col,board[selRow][selCol]);
               gui.removePiece(selRow,selCol);
               if (board[row][col] != EMPTY)
               {
                  pieceTaken(board[row][col]);
               
               }
               board[row][col] = piece;
               check[row][col] = 1;
               board[selRow][selCol] = EMPTY;
               check[selRow][selCol] = 0;
               return true;
            }
            else if (selRow +1 == row && (selCol+1 == col || selCol-1 == col) && check[row][col] == 1)
            {
               gui.setPiece(row,col,board[selRow][selCol]);
               gui.removePiece(selRow,selCol);
               if (board[row][col] != EMPTY)
               {
                  pieceTaken(board[row][col]);
               }
               board[row][col] = piece;
               check[row][col] = 1;
               board[selRow][selCol] = EMPTY;
               check[selRow][selCol] = 0;
               return true;
               
            }
         
         	
         }
         else
         {
            if (selRow+1 == row && selCol == col && check[row][col] == 0)
            {
               gui.setPiece(row,col,board[selRow][selCol]);
               gui.removePiece(selRow,selCol);
               if (board[row][col] != EMPTY)
               {
                  pieceTaken(board[row][col]);
               
               }
               board[row][col] = piece;
               check[row][col] = 1;
               board[selRow][selCol] = EMPTY;
               check[selRow][selCol] = 0;
               return true;
            }
            
            else if (selRow +1 == row && (selCol+1 == col || selCol-1 == col) && check[row][col] == 1)
            {
               gui.setPiece(row,col,board[selRow][selCol]);
               gui.removePiece(selRow,selCol);
               if (board[row][col] != EMPTY)
               {
                  pieceTaken(board[row][col]);
               }
               board[row][col] = piece;
               check[row][col] = 1;
               board[selRow][selCol] = EMPTY;
               check[selRow][selCol] = 0;
               return true;
            
            }
         }
      }
      
      // shit for white pawn
      else if (piece == 6)
      {
         if(selRow == 6)
         {
            if ((selRow-1 == row || (selRow-2 == row && check[selRow-1][col] == 0)) && selCol == col && check[row][col] == 0)
            {
               gui.setPiece(row,col,board[selRow][selCol]);
               gui.removePiece(selRow,selCol);
               if (board[row][col] != EMPTY)
               {
                  pieceTaken(board[row][col]);
               }
               board[row][col] = piece;
               check[row][col] = 1;
               board[selRow][selCol] = EMPTY;
               check[selRow][selCol] = 0;
               return true;
            }
            else if (selRow -1 == row && (selCol+1 == col || selCol-1 == col) && check[row][col] == 1)
            {
               gui.setPiece(row,col,board[selRow][selCol]);
               gui.removePiece(selRow,selCol);
               if (board[row][col] != EMPTY)
               {
                  pieceTaken(board[row][col]);
               }
               board[row][col] = piece;
               check[row][col] = 1;
               board[selRow][selCol] = EMPTY;
               check[selRow][selCol] = 0;
               return true;
            
            }
         
         }
         else
         {
            if (selRow-1 == row && selCol == col && check[row][col] == 0)
            {
               gui.setPiece(row,col,board[selRow][selCol]);
               gui.removePiece(selRow,selCol);
               if (board[row][col] != EMPTY)
               {
                  pieceTaken(board[row][col]);
               
               }
               board[row][col] = piece;
               check[row][col] = 1;
               board[selRow][selCol] = EMPTY;
               check[selRow][selCol] = 0;
               return true;
            }
            
            else if (selRow - 1 == row && (selCol+1 == col || selCol-1 == col) && check[row][col] == 1)
            {
               gui.setPiece(row,col,board[selRow][selCol]);
               gui.removePiece(selRow,selCol);
               if (board[row][col] != EMPTY)
               {
                  pieceTaken(board[row][col]);
               }
               board[row][col] = piece;
               check[row][col] = 1;
               board[selRow][selCol] = EMPTY;
               check[selRow][selCol] = 0;
               return true;
            
            }
         }
      }
      // rook - move
      else if (piece == 1 || piece == 7)
      {
         // checks if same row or column
         if (selCol == col && selRow != row)
         {
            if (selRow > row)
            {
               greater = selRow;
               lower = row;
            }
            else
            {
               greater = row;
               lower = selRow;
            }
            // goes through from position to selected position and checks if there are any pieces in the way
            for (int i = lower+1; i < greater; i ++)
            {
               if (check[i][col] == 1 && i != row)
               {
                  counter++;
               }
               
            }
         }
         else if (selRow == row && selCol != col)
         {
            if (selCol > col)
            {
               greater = selCol;
               lower = col;
            }
            else
            {
               greater = col;
               lower = selCol;
            }
            // goes through from position to selected position and checks if there are any pieces in the way
            for (int i = lower+1; i < greater ; i++)
            {
               if (check[row][i] == 1 && i != col)
               {
                  counter++;
               }
            }
         }
         else
         {
            counter = 1;
         }
         // if the amount of pieces between position and selected, then it will move the piece
         if (counter == 0)
         {
            gui.setPiece(row,col,board[selRow][selCol]);
            gui.removePiece(selRow,selCol);
            if (board[row][col] != EMPTY)
            {
               pieceTaken(board[row][col]);
            }
            board[row][col] = piece;
            check[row][col] = 1;
            board[selRow][selCol] = EMPTY;
            check[selRow][selCol] = 0;
            return true;
         
         }
         else
         {
            return false;
         }   
      }
      // movement for knight
      else if (piece == 2 || piece == 8)
      {
         if (row == selRow - 2)
         {
            if (col == selCol + 1 || col == selCol - 1)
            {
               allow = true;
            }
         }
         else if (row == selRow - 1)
         {
            if (col == selCol + 2 || col == selCol - 2)
            {
               allow = true;
            }
         }
         else if (row == selRow + 2)
         {
            if (col == selCol + 1 || col == selCol - 1)
            {
               allow = true;
            }
         }
         else if (row == selRow + 1)
         {
            if (col == selCol + 2 || col == selCol - 2)
            {
               allow = true;
            }
         }
         if (allow == true)
         {
            gui.setPiece(row,col,board[selRow][selCol]);
            gui.removePiece(selRow,selCol);
            if (board[row][col] != EMPTY)
            {
               pieceTaken(board[row][col]);
            }
            board[row][col] = piece;
            check[row][col] = 1;
            board[selRow][selCol] = EMPTY;
            check[selRow][selCol] = 0;
            return true;
         }
         else
         {
            return false;
         }   
      }
      // movement for bishop
      else if (piece == 3 || piece == 9)
      {
         difference = Math.abs(selRow - row);
         if (Math.abs(selRow - row) == Math.abs(selCol - col))
         {
            if (row > selRow)
            {
               if (col > selCol)
               {
                  // down right
                  for (int i = 1; i < difference; i ++)
                  {
                     if (check[row-i][col-i] == 1)
                     {
                        counter++;
                     }
                  }
               }
               else if (selCol > col)
               {
                  // down left
                  for (int i = 1; i < difference; i ++)
                  {
                     if (check[row-i][col+i] == 1)
                     {
                        counter++;
                     }
                  }
               }
            }
            else if (selRow > row)
            {
               if (col > selCol)
               {
                  // up right
                  for (int i = 1; i < difference; i ++)
                  {
                     if (check[row+i][col-i] == 1)
                     {
                        counter++;
                     }
                  }
               }
               else if (selCol > col)
               {
                  // up left
                  for (int i = 1; i < difference; i ++)
                  {
                     if (check[row+i][col+i] == 1)
                     {
                        counter++;
                     }
                  }
               
               }
            }
            else
            {
               counter = 1;
            }
            
            if (counter == 0)
            {
               gui.setPiece(row,col,board[selRow][selCol]);
               gui.removePiece(selRow,selCol);
               if (board[row][col] != EMPTY)
               {
                  pieceTaken(board[row][col]);
               }
               board[row][col] = piece;
               check[row][col] = 1;
               board[selRow][selCol] = EMPTY;
               check[selRow][selCol] = 0;
               return true;
            }
            else
            {
               return false;
            }
         }
      }
      else if (piece == 4 || piece == 10)
      {		// queen
         // checks if same row or column
         if (selCol == col && selRow == row)
         {
            counter++;
         }
         if (selCol == col && check[row][col] == 0)
         {
            if (selRow > row)
            {
               greater = selRow;
               lower = row;
            }
            else
            {
               greater = row;
               lower = selRow;
            }
            // goes through from position to selected position and checks if there are any pieces in the way
            for (int i = lower; i < greater; i ++)
            {
               if (check[i][col] == 1 && i != row)
               {
                  counter++;
               }
               
            }
         }
         else if (selRow == row && check[row][col] == 0)
         {
            if (selCol > col)
            {
               greater = selCol;
               lower = col;
            }
            else
            {
               greater = col;
               lower = selCol;
            }
            // goes through from position to selected position and checks if there are any pieces in the way
            for (int i = lower; i < greater ; i++)
            {
               if (check[row][i] == 1 && i != col)
               {
                  counter++;
               }
            }
         }
         else if (Math.abs(selRow - row) == Math.abs(selCol - col))
         {
            difference = Math.abs(selRow - row);
            if (row > selRow)
            {
               if (col > selCol)
               {
                  // down right
                  for (int i = 1; i < difference; i ++)
                  {
                     if (check[row-i][col-i] == 1)
                     {
                        counter++;
                     }
                  }
               }
               else if (selCol > col)
               {
                  // down left
                  for (int i = 1; i < difference; i ++)
                  {
                     if (check[row-i][col+i] == 1)
                     {
                        counter++;
                     }
                  }
               }
            }
            else if (selRow > row)
            {
               if (col > selCol)
               {
                  // up right
                  for (int i = 1; i < difference; i ++)
                  {
                     if (check[row+i][col-i] == 1)
                     {
                        counter++;
                     }
                  }
               }
               else if (selCol > col)
               {
                  // up left
                  for (int i = 1; i < difference; i ++)
                  {
                     if (check[row+i][col+i] == 1)
                     {
                        counter++;
                     }
                  }
               
               }
            }
         }
            
         if (counter == 0)
         {
            gui.setPiece(row,col,board[selRow][selCol]);
            gui.removePiece(selRow,selCol);
            if (board[row][col] != EMPTY)
            {
               pieceTaken(board[row][col]);
            }
            board[row][col] = piece;
            check[row][col] = 1;
            board[selRow][selCol] = EMPTY;
            check[selRow][selCol] = 0;
            return true;
         }
         else
         {
            return false;
         }
      } 
         // movement for kings
      else if (piece == 5 || piece == 11)
      {
         if (check[row][col] == 0)
         {
            if (row == selRow - 1 && col == selCol - 1)
            {
               allow = true;
            }
            else if (row == selRow && col == selCol - 1)
            {
               allow = true;            
            }
            else if (row == selRow + 1 && col == selCol - 1)
            {
               allow = true;           
            }
            else if (row == selRow - 1 && col == selCol)
            {
               allow = true;            
            }
            else if (row == selRow + 1 && col == selCol)
            {
               allow = true;           
            }
            else if (row == selRow - 1 && col == selCol + 1)
            {
               allow = true;            
            }
            else if (row == selRow && col == selCol + 1)
            {
               allow = true;    
            }
            else if (row == selRow + 1 && col == selCol + 1)
            {
               allow = true;
            
            }
            if (allow)
            {
               gui.setPiece(row,col,board[selRow][selCol]);
               gui.removePiece(selRow,selCol);
               if (board[row][col] != EMPTY)
               {
                  pieceTaken(board[row][col]);
               }
               board[row][col] = piece;
               check[row][col] = 1;
               board[selRow][selCol] = EMPTY;
               check[selRow][selCol] = 0;
               return true;
            }
            else
            {
               return false;
            } 
         }
      }
      else
      {
      
      }
      return false;
   }

   public boolean validPiece(int piece, int player)
   {
      if (player == PLAYER1 && (piece >= 0 && piece <= 5))
      {
         return true;
      }
      else if  (player == PLAYER2 && (piece >= 6 && piece <=11))
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   public void pieceTaken (int piece)
   {
      if (piece == 0)
      {
         pawnsB++;
         gui.pawnsTaken(PLAYER1,pawnsB);
      }
      else if (piece == 1)
      {
         rooksB++;
         gui.rooksTaken(PLAYER1,rooksB);
      }
      else if (piece == 2)
      {
         knightsB++;
         gui.knightsTaken(PLAYER1,knightsB);
      }
      else if (piece == 3)
      {
         bishopsB++;
         gui.bishopsTaken(PLAYER1, bishopsB);
      }
      else if (piece == 4)
      {
         queensB++;
         gui.queensTaken(PLAYER1, queensB);
      }
      else if (piece == 6)
      {
         pawnsW++;
         gui.pawnsTaken(PLAYER2,pawnsW);
      }
      else if (piece == 7)
      {
         rooksW++;
         gui.rooksTaken(PLAYER2,rooksW);
      }
      else if (piece == 8)
      {
         knightsW++;
         gui.knightsTaken(PLAYER2,knightsW);
      }
      else if (piece == 9)
      {
         bishopsW++;
         gui.bishopsTaken(PLAYER2, bishopsW);
      }
      else if (piece == 10)
      {
         queensW++;
         gui.queensTaken(PLAYER2, queensW);
      }
   	
   	//public static void check ()
   	//{
   	
   	//}
   }
}

