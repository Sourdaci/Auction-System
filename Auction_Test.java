import java.util.ArrayList;
/**
 * Write a description of class Auction_Test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Auction_Test
{
    // instance variables - replace the example below with your own
    private Auction subasta;
    private Person paco, pepe, piti;

    /**
     * Constructor for objects of class Auction_Test
     */
    public Auction_Test()
    {
        subasta = new Auction();
        paco = new Person("Paco");
        pepe = new Person("Pepe");
        piti = new Person("Piti");
    }

    public void pruebaMetodoClose()
    {
        subasta.enterLot("Calendario Pirelli");
        subasta.enterLot("Calendario PlayBoy");
        subasta.enterLot("Calendario Woman's Secret");
        subasta.enterLot("Calendario Los Chicos del Coro");
        subasta.makeABid(1, paco, 20L);
        subasta.makeABid(2, pepe, 18L);
        subasta.makeABid(3, piti, 22L);
        subasta.close();
    }
    
    public void pruebaMetodoGetUnsold()
    {
        subasta.enterLot("Calendario Pirelli");
        subasta.enterLot("Calendario PlayBoy");
        subasta.enterLot("Calendario Woman's Secret");
        subasta.enterLot("Calendario Los Chicos del Coro");
        subasta.makeABid(1, paco, 20L);
        subasta.makeABid(2, pepe, 18L);
        subasta.makeABid(3, piti, 22L);
        ArrayList<Lot> sinPujas = new ArrayList<Lot>(subasta.getUnsold());
        for (Lot articulo : sinPujas){
            System.out.println(articulo.toString());
        }
    }
}
