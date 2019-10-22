import java.util.Random;

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
      this.q = q;
      cost = 0;
   }

   public State getNextState()
   {
      int i;
      Queen nextStateQueen[] = new Queen[boardSize];

      int rand = randomGenerator.nextInt(boardSize);

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
      }
      return new State(boardSize, nextStateQueen);
   }

   public void calculateCost()
   {
      int i, j;
      cost = 0;

      for (i = 0; i < boardSize; i++)
      {
         for (j = 0; j < boardSize; j++)
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
      cost = cost / 2;
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
}