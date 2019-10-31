import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AlgoritmoGenetico
{

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
   
}