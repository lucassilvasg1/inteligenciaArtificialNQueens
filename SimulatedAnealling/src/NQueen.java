
class NQueen
{
   private int boardSize;
   private State currentState, nextState, expectedState;

   public NQueen(int boardSize)
   {
      this.boardSize = boardSize;
      currentState = new State(boardSize);
      expectedState = new State(boardSize);

   }

   public void solve()
   {
      double temperature;
      double delta;
      double probability;
      double rand;

      for( temperature = 100000; temperature > 0 && (currentState.getCost()!= 0) ; temperature--)
      {
         nextState = currentState.getNextState();
         delta = currentState.getCost() - nextState.getCost();
         probability = Math.exp(delta / temperature);
         rand = Math.random();

         if(delta > 0)
         {
            currentState = nextState;
         }
         else if(rand <= probability)
         {
            nextState.cost = (int)probability;
            currentState = nextState;
         }
      }
   }

   public void show()
   {
      int temp = 0;
      Queen q[] = currentState.getQueens();
      boolean queen = false;
      System.out.println();

      for (int i = 0; i < boardSize; i++) {
         for (int j = 0; j < boardSize; j++) {
            for (int k = 0; k < boardSize; k++) {
               if (i == q[k].getIndexOfX() && j == q[k].getIndexOfY()) {
                  queen = true;
                  temp = k;
                  break;
               }
            }
            if (queen) {
               System.out.print("|"+temp);
               queen = false;
            }
            else {
               System.out.print("|_");
            }
         }
         System.out.println("|");
      }
   }

   public boolean reachResult() 
   {

      Queen q[] = currentState.getQueens();

      //       Queen q1 = new Queen(0,2);
      //       Queen q2 = new Queen(1,0);
      //       Queen q3 = new Queen(2,3);
      //       Queen q4 = new Queen(3,1);
      //       
      //       Queen q[] = {q1,q2,q3,q4};
      //       
      //       currentState.q = q;
      //       
      //       show();


      for (int i = 0; i < boardSize; i++)
      {
         for (int j = i+1; j < boardSize; j++)
         {
            if(q[i].getIndexOfX() == q[j].getIndexOfX() || q[i].getIndexOfY() == q[j].getIndexOfY())
            {
               return false;
            }

            if((q[i].getIndexOfX() + q[i].getIndexOfY()) == (q[j].getIndexOfX() + q[j].getIndexOfY()))
            {
               return false;
            }

            if((q[i].getIndexOfX() - q[i].getIndexOfY()) == (q[j].getIndexOfX() - q[j].getIndexOfY()))
            {
               return false;
            }
         }

      }


      return true;
   }
}