import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import sun.security.util.ArrayUtil;

class State
{
   int boardSize;

   int cost;

   Queen q[];

   Random randomGenerator = new Random();

   public State(int boardSize)
   {
      int i;
      this.boardSize = boardSize;
      q = new Queen[boardSize];

      for (i = 0; i < boardSize; i++)
      {
         q[i] = new Queen(i, randomGenerator.nextInt(boardSize));
      }

      cost = 0;
   }

   public State(int boardSize, Queen q[])
   {
      this.boardSize = boardSize;
      this.q =  Arrays.copyOf(q, q.length);
      cost = 0;
   }

   public State getNextState(State state)
   {
      State newGame = new State(state.boardSize, state.q);
      
      int col1 = randomGenerator.nextInt(boardSize);
      int col2 = randomGenerator.nextInt(boardSize);
      
      Queen linha2  = newGame.q[col2];
      newGame.q[col2] = newGame.q[col1];
      newGame.q[col1] = linha2;
      
  /*    int rand = randomGenerator.nextInt(boardSize);

      for (i = 0; i < boardSize; i++)
      {
         nextStateQueen[i] = new Queen(q[i].getIndexOfX(), q[i].getIndexOfY());

         if (rand == i)
         {
            int temp = randomGenerator.nextInt(boardSize);

            while (temp == q[i].getIndexOfY())
            {
               temp = randomGenerator.nextInt(boardSize);
            }

            nextStateQueen[i] = new Queen(q[i].getIndexOfX(), temp);
         }
      } */
      return newGame;
   }

   public void calculateCost()
   {
      int i, j;
      cost = 0;

      for (i = 0; i < boardSize; i++)
      {
         for (j = i+1; j < boardSize; j++)
         {
            if (q[i].getIndexOfX() == q[j].getIndexOfX()
                || q[i].getIndexOfY() == q[j].getIndexOfY()
                || (q[i].getIndexOfX() + q[i].getIndexOfY()) == (q[j].getIndexOfX() + q[j].getIndexOfY())
                || (q[i].getIndexOfX() - q[i].getIndexOfY()) == (q[j].getIndexOfX() - q[j].getIndexOfY()))
            {
               cost++;
            }
         }
      }
   }
   
   public void heuristica()
   {
       int dimensao = boardSize;
       cost = 0;
       for(int i=0; i < dimensao; i++ )
       {
           for(int j= i+1; j < dimensao; j++)
           {
               int[] posi = {i, q[i].getIndexOfX()};
               int[] posj = {j, q[j].getIndexOfY()};
               
               int[] delta = {Math.abs(posi[0] - posj[0]),Math.abs(posi[1] - posj[1])};
               
               if(delta[0] == delta[1] || posi[0] == posj[0] || posi[1] == posj[1])
               {
                  cost += 1;
               }
           }
       }
       
   }
   

   public int getCost()
   {
      calculateCost();
      return cost;
   }

   public Queen[] getQueens()
   {
      return q;
   }
   
   @Override
   public String toString()
   {
      Integer temp = new Integer(0);
      boolean queen = false;
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < boardSize; i++)
      {
         for (int j = 0; j < boardSize; j++)
         {
            for (int k = 0; k < boardSize; k++)
            {
               if (i == q[k].getIndexOfX() && j == q[k].getIndexOfY())
               {
                  queen = true;
                  temp = new Integer(k);
                  break;
               }
            }
            if (queen)
            {
               String aux = "";
               if (temp.intValue() < 10) aux = "0" + temp;
               else
                  aux = temp.toString();
               sb.append("|" + aux);
               queen = false;
            }
            else
            {
               sb.append("|__" );
            }
         }
         sb.append("|" + "\n");
      }
      return sb.toString();
   }
}