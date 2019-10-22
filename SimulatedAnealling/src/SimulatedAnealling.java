class SimulatedAnnealing {

   public static void main(String[] args) 
   {
      NQueen nq = new NQueen(7);
      int i = 1;
      do {
         System.out.println("Solution: "+i);
         nq.solve();
         nq.show();
         System.out.println("");
         i++;
      }while(!nq.reachResult());

      //        for(int i = 1; i <= 12; i++){
      //        
      //        }
   }

}