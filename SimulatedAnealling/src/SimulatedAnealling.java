import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimulatedAnealling
{

   static List<Integer> jogo;

   public static void main(String[] args)
   {

      jogo = IntStream.rangeClosed(0, 4).boxed().collect(Collectors.toList());

      int i = 1;
      System.out.println("Solution: " + i);

      SimulatedAnealling a = new SimulatedAnealling();
      a.solve();

      ListIterator<Integer> it = jogo.listIterator();
      while (it.hasNext())
      {
         System.out.println(it.nextIndex() + " " + it.next());
      }
      
      SimulatedAnealling.imprimir(jogo);
      

      System.out.println("");
      i++;

   }

   public static List<Integer> getVizinho(List<Integer> jogo)
   {

      int[] jogoAntigo = new int[jogo.size()];

      for (int i = 0; i < jogoAntigo.length; i++)
      {
         jogoAntigo[i] = jogo.get(i).intValue();
      }

      int[] novoJogo = Arrays.copyOf(jogoAntigo, jogoAntigo.length);

      Random gerador = new Random();

      int dimensao = novoJogo.length;

      int coluna1 = gerador.nextInt(dimensao - 1);

      int coluna2 = gerador.nextInt(dimensao - 1);

      int linha2 = novoJogo[coluna2];

      novoJogo[coluna2] = novoJogo[coluna1];
      novoJogo[coluna1] = linha2;

      ArrayList<Integer> novo = new ArrayList<Integer>();

      for (int i = 0; i < novoJogo.length; i++)
      {
         novo.add(new Integer(novoJogo[i]));
      }

      return novo;
   }

   public static int heuristica(List<Integer> jogo)
   {
      int peso = 0;

      int dimensao = jogo.size();

      for (int i = 0; i < dimensao; i++)
      {
         for (int j = i + 1; j < dimensao; j++)
         {
            int[] posi = { i, jogo.get(i) };
            int[] posj = { j, jogo.get(j) };

            int[] delta = { Math.abs(posi[0] - posj[0]), Math.abs(posi[1] - posj[1]) };

            if (delta[0] == delta[1] || posi[0] == posj[0] || posi[1] == posj[1])
            {
               peso += 1;
            }
         }
      }

      return peso;
   }

   public static void imprimir(List<Integer> jogo)
   {
      System.out.println();

      for (Integer i = 0; i < jogo.size(); i++)
      {
         for (Integer j = 0; j < jogo.size(); j++)
         {
            if (j.intValue() == jogo.get(i))
            {
               String aux = "";
               if (j < 10) aux = "0" + j;
               else
                  aux = j.toString();
               System.out.print("|" + aux);
            }
            else
            {
               System.out.print("|__");
            }
         }
         System.out.println("|");
      }
   }

   public void solve()
   {

      class RandomCollection<E>
      {
         private final NavigableMap<Double, E> map = new TreeMap<Double, E>();

         private final Random random;

         private double total = 0;

         public RandomCollection()
         {
            this(new Random());
         }

         public RandomCollection(Random random)
         {
            this.random = random;
         }

         public RandomCollection<E> add(double weight, E result)
         {
            if (weight <= 0) return this;
            total += weight;
            map.put(total, result);
            return this;
         }

         public E next()
         {
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

      for (Double t : T)
      {
         int pesoAtual;
         int pesoVizinho;

         List<Integer> vizinho = SimulatedAnealling.getVizinho(jogo);

         pesoAtual = SimulatedAnealling.heuristica(jogo);
         pesoVizinho = SimulatedAnealling.heuristica(vizinho);

         if (t <= 0.005 || pesoAtual == 0)
         {
            break;
         }

         if (pesoAtual <= pesoVizinho)
         {
            jogo = vizinho;
         }
         else
         {
            probability = Math.exp(pesoAtual - pesoVizinho / t);
            RandomCollection<List<Integer>> rc = new RandomCollection<List<Integer>>().add(probability, vizinho).add(1 - probability, jogo);
            jogo = rc.next();
         }
      }

   }

}