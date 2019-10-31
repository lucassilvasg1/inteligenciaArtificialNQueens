import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
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
      
      double probability;
      
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
         
         if(currentState.getCost() <= nextState.getCost())
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
   
   public int heuristica(ArrayList<Integer> jogo)
   {
	   int peso = 0;
	   
	   int dimensao = jogo.size();
	   
	   for(int i=0; i < dimensao; i++ )
	   {
		   for(int j= i+1; j < dimensao; j++)
		   {
			   int[] posi = {i, jogo.get(i)};
			   int[] posj = {j, jogo.get(j)};
			   
			   int[] delta = {Math.abs(posi[0] - posj[0]),Math.abs(posi[1] - posj[1])};
			   
			   if(delta[0] == delta[1] || posi[0] == posj[0] || posi[1] == posj[1])
			   {
				   peso += 1;
			   }
		   }
	   }
	   
	   return peso;
   }
   
   public ArrayList<Integer> movimentacaoRandomicaSmart(ArrayList<Integer> jogo)
   {
	   
	   int[] jogoAntigo = new int[jogo.size()];
	   
	   for (int i = 0; i < jogoAntigo.length; i++) {
		   jogoAntigo[i] = jogo.get(i).intValue();
	   }
	   
	   int[] novoJogo = Arrays.copyOf(jogoAntigo, jogoAntigo.length);
	   
	   Random gerador = new Random();
	   
	   int dimensao = novoJogo.length;
	   
	   int coluna1 = gerador.nextInt(dimensao);
	   
	   int coluna2 = gerador.nextInt(dimensao);
	   
	   int linha2 = novoJogo[coluna2];
	   
	   novoJogo[coluna2] = novoJogo[coluna1];
	   novoJogo[coluna1] = linha2;
	   
	   ArrayList<Integer> novo = new ArrayList<Integer>();
	   
	   for (int i = 0; i < novoJogo.length; i++) {
		   novo.add(new Integer(novoJogo[i]));
	   }
	   
	   return novo;
   }
   
   public ArrayList<Integer> tabRandomico(int dimensao)
   {
	   List<Integer> auxiliar = new ArrayList<Integer>();
	   for (int i = 0; i < dimensao; i++) {
		   auxiliar.add(i);
	   }
	   
	   List<Integer> passo = NQueen.pickNRandomElements(auxiliar, dimensao, ThreadLocalRandom.current());
	   
	   ArrayList<Integer> retorno = new ArrayList<Integer>();
	   
	   for (int i = 0; i < passo.size(); i++) {
		   retorno.add(passo.get(i));
	   }
	   
	   return retorno;
   }
   
   public static <E> List<E> pickNRandomElements(List<E> list, int n, Random r) {
	    int length = list.size();

	    if (length < n) return null;

	    //We don't need to shuffle the whole list
	    for (int i = length - 1; i >= length - n; --i)
	    {
	        Collections.swap(list, i , r.nextInt(i + 1));
	    }
	    return list.subList(length - n, length);
	}
   
   public static ArrayList<Integer> deepCopy(ArrayList<Integer> copiado)
   {
       int[] antigo = new int[copiado.size()];
       
       for (int i = 0; i < antigo.length; i++) {
           antigo[i] = copiado.get(i).intValue();
       }
       
       int[] novo = Arrays.copyOf(antigo, antigo.length);
       
       ArrayList<Integer> copia = new ArrayList<Integer>();
       
       for (int i = 0; i < novo.length; i++) {
           copia.add(novo[i]);
       }
       
       return copia;
   }

   public State getCurrentState()
   {
      return currentState;
   }

   public void setCurrentState(State currentState)
   {
      this.currentState = currentState;
   }

   public State getNextState()
   {
      return nextState;
   }

   public void setNextState(State nextState)
   {
      this.nextState = nextState;
   }
   
}