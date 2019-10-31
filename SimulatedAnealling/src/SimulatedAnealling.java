import java.util.ArrayList;

class SimulatedAnealling
{

   public static void main(String[] args)
   {
//      NQueen nq = new NQueen(40);
//      
//      int i = 1;
//      while (!nq.reachResult())
//      {
//         System.out.println("Solution: " + i);
//         nq.solve();
//         nq.show();
//         System.out.println("");
//         i++;
//      }
	   
	   int tamanhoJogo = 40;
	   
	   int MAX = 10000; 
	   
	   int C = 1;
	   
	   ArrayList<Integer> passado = null;
	   
	   ArrayList<Integer> atual;
	   
	   ArrayList<Integer> jogo = new ArrayList<Integer>();
	   
	   
	   for(int i=0; i < tamanhoJogo; i++)
	   {
		   jogo.add(i);
	   }
	   
	   if(passado == null)
	   {
		   atual = NQueen.deepCopy(jogo); 
	   }else{
		   atual = new ArrayList<Integer>();
		   atual = passado;
	   }
	   
	   LambdaFunction prob = (int x, int y, int z) -> Math.exp((x - y/ z));
	   
	   ArrayList<Double> t = new ArrayList<Double>();
	   
	   for (int i = 1; i < MAX; i++) {
		   t.add(i/1.0);
	   }
	   
	   ArrayList<Double> T = new ArrayList<Double>();
	   
	   for (int i = 0; i < t.size(); i++) {
		   T.add(C/Math.sqrt(t.get(i)));
	   }
	   
	   

   }

}