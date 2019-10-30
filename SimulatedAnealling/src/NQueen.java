import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class NQueen
{
   private int boardSize;

   private State currentState, nextState;

   public NQueen(int boardSize)
   {
      this.boardSize = boardSize;
      currentState = new State(boardSize);
   }

   public void solve()
   {
      
      class RandomCollection<E> {
         private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
         private final Random random;
         private double total = 0;

         public RandomCollection() {
             this(new Random());
         }

         public RandomCollection(Random random) {
             this.random = random;
         }

         public RandomCollection<E> add(double weight, E result) {
             if (weight <= 0) return this;
             total += weight;
             map.put(total, result);
             return this;
         }

         public E next() {
             double value = random.nextDouble() * total;
             return map.higherEntry(value).getValue();
         }
     }
      
      double temperature;
      double delta;
      double probability;
      double rand;
      
      List<Integer> ta = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
      List<Double> T = new ArrayList<Double>();
      
      for (Integer a : ta)
      {
         T.add(1 / Math.sqrt(a));
      }

      for(Double t : T)
      {
         nextState = currentState.getNextState(currentState);
         
         if(t <= 0.005 || currentState.getCost() == 0)
         {
            break;
         }
         
         if(currentState.cost <= nextState.getCost())
         {
            currentState = nextState;
         }
         else
         {
            probability = Math.exp((currentState.getCost() - nextState.getCost())/t);
            RandomCollection<State> rc = new RandomCollection<State>().add(probability, nextState).add(1 - probability, currentState);
            currentState = rc.next();
         }
      }
   }

   public void show()
   {
      Integer temp = new Integer(0);
      Queen q[] = currentState.getQueens();
      boolean queen = false;
      System.out.println();

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
               System.out.print("|" + aux);
               queen = false;
            }
            else
            {
               System.out.print("|__");
            }
         }
         System.out.println("|");
      }
   }

   public boolean reachResult()
   {

      Queen q[] = currentState.getQueens();

      // Queen q1 = new Queen(0,2);
      // Queen q2 = new Queen(1,0);
      // Queen q3 = new Queen(2,3);
      // Queen q4 = new Queen(3,1);
      //
      // Queen q[] = {q1,q2,q3,q4};
      //
      // currentState.q = q;
      //
      // show();

      for (int i = 0; i < boardSize; i++)
      {
         for (int j = i + 1; j < boardSize; j++)
         {
            if (q[i].getIndexOfX() == q[j].getIndexOfX() || q[i].getIndexOfY() == q[j].getIndexOfY())
            {
               return false;
            }

            if ((q[i].getIndexOfX() + q[i].getIndexOfY()) == (q[j].getIndexOfX() + q[j].getIndexOfY()))
            {
               return false;
            }

            if ((q[i].getIndexOfX() - q[i].getIndexOfY()) == (q[j].getIndexOfX() - q[j].getIndexOfY()))
            {
               return false;
            }
         }

      }

      return true;
   }
}