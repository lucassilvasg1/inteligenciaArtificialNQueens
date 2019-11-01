import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableMap;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AlgoritmoGenetico
{
   Integer passado = null;
   
   public static void main(String[] args)
   {
      ArrayList<ArrayList<Integer>> pop = new ArrayList<ArrayList<Integer>>();
      
      for(int i=0; i < 10;i++)
      {
         pop.add(AlgoritmoGenetico.tabRandomico(40));
      }
      
      ArrayList<Peso> pesosMapeados = new ArrayList<Peso>();
      
      for(int i=0; i < pop.size();i++)
      {
          Peso p = new Peso();
          p.setPeso(AlgoritmoGenetico.heuristica(pop.get(i)));
          p.setVetor(pop.get(i));
          
          pesosMapeados.add(p);
      }
      
      
   }
   
   public static List<Integer> cruzamento(List<Integer> pai, List<Integer> mae)
   {
      int dimensao = pai.size();
      
      Random gerador = new Random();
            
      int crossouver = gerador.nextInt(dimensao -1) + 1;
      
      List<Integer> f1 = new ArrayList<Integer>();
      
      for(int i=0; i < crossouver;i++)
      {
         f1.add(pai.get(i));
      }
      
      for(int i=crossouver; i < dimensao; i++)
      {
         f1.add(mae.get(i));
      }
      
      return f1;
   }
   
   public static List<Integer> cruzamentoKratos(List<Integer> pai, List<Integer> mae)
   {
      List<Integer> f1 = IntStream.rangeClosed(0, pai.size()-1).boxed().collect(Collectors.toList());
      
      LambdaFunction alterado = (int x) -> x%2==0?mae.get(x):pai.get(x);
      
      List<Integer> retorno = new ArrayList<Integer>();
      
      for (int i = 0; i < f1.size(); i++)
      {
         retorno.add(alterado.lambda(f1.get(i)));
      }
      
      return retorno;
   }
   
   public static List<Integer> cruzamentoSmart(List<Integer> pai, List<Integer> mae)
   {
      int dimensao = pai.size();
      
      List<Integer> f1 = AlgoritmoGenetico.cruzamentoKratos(pai, mae);
      
      HashSet<Integer> setFilho = new HashSet<Integer>(f1);
      
      Set<Integer> sub = new HashSet<Integer>(pai);
      
      sub.removeAll(setFilho);
      
      Object[] subList = sub.toArray();
      
      List<Integer> subArrayList = new ArrayList<Integer>();
      
      for (int i = 0; i < subList.length; i++)
      {
         subArrayList.add((Integer) subList[i]);
      }
      
      List<Integer> rangeDimensao = IntStream.rangeClosed(0, dimensao-1).boxed().collect(Collectors.toList());

      for(Integer i : rangeDimensao)
      {
         Integer aux = f1.get(i);
         
         if(setFilho.contains(aux))
         {
            setFilho.remove(aux);
         }else{
            f1.set(i, subArrayList.remove(subArrayList.size()-1));
         }
      }
      
      return f1;
   }
   
   public static void mutacao(List<Integer> jogo)
   {
	   int dimensao = jogo.size();
	   
	   List<Integer> auxiliar = new ArrayList<Integer>();
	   for (int i = 0; i < dimensao; i++) {
		   auxiliar.add(i);
	   }
	   
	   List<Integer> pecas = AlgoritmoGenetico.pickNRandomElements(auxiliar, 2, ThreadLocalRandom.current());
	   
	   jogo.set(pecas.get(1), pecas.get(0));
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
   
   public static ArrayList<Integer> tabRandomico(int dimensao)
   {
	   List<Integer> auxiliar = new ArrayList<Integer>();
	   for (int i = 0; i < dimensao; i++) {
		   auxiliar.add(i);
	   }
	   
	   List<Integer> passo = AlgoritmoGenetico.pickNRandomElements(auxiliar, dimensao, ThreadLocalRandom.current());
	   
	   ArrayList<Integer> retorno = new ArrayList<Integer>();
	   
	   for (int i = 0; i < passo.size(); i++) {
		   retorno.add(passo.get(i));
	   }
	   
	   return retorno;
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
   
   public void solve(List<List<Integer>> populacao) {
      int tam = populacao.size();
      
      //chave?
      
      long pop_sobrev = Math.round(0.2*tam);
      
      if(this.passado == null)
      {
         
      }
      
   }
   
   public Peso procriacao(List<Integer> pai, List<Integer> mae)
   {
      
      List<Integer> f1 = AlgoritmoGenetico.cruzamentoSmart(pai, mae);
      
      RandomCollection<Boolean> rc = new RandomCollection<Boolean>().add(0.1, true).add(0.9, false);
      
      Boolean mutacao = rc.next();
      
      if(mutacao)
      {
         Peso p = new Peso();
         p.setPeso(AlgoritmoGenetico.heuristica(f1));
         p.setVetor(f1);
         
         return p;
      }
      
      return null;
   }
   
   public List<Integer> escolha(List<Integer> x, List<List<Integer>> y)
   {
      List<Integer> escolha = new ArrayList<Integer>();
      
      for (int i = 0; i < y.size(); i++)
      {
         escolha.add(y.get(i).get(1));
      }
      
      RandomCollection<Integer> rc = new RandomCollection<Integer>();

      for (int i = 0; i < escolha.size(); i++)
      {
         rc.add(1/y.get(i).get(0), escolha.get(i));
      }
      
      List<Integer> retorno = new ArrayList<Integer>();
      
      retorno.add(rc.next());
      retorno.add(rc.next());
      
      return retorno;
   }
}
