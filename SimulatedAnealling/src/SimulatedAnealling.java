class SimulatedAnealling
{

   public static void main(String[] args)
   {
      NQueen nq = new NQueen(40);
      int i = 1;
      while (!nq.reachResult())
      {
         System.out.println("Solution: " + i);
         nq.solve();
         nq.show();
         System.out.println("");
         i++;
      }

   }

}