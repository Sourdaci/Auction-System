import java.util.ArrayList;
import java.util.Iterator;
/**
 * A simple model of an auction.
 * The auction maintains a list of lots of arbitrary length.
 *
 * @author David J. Barnes and Michael Kölling.
 * @version 2011.07.31
 */
public class Auction{
    // The list of Lots in this auction.
    private ArrayList<Lot> lots;
    // The number that will be given to the next lot entered
    // into this auction.
    private int nextLotNumber;
    /**
     * Create a new auction.
     */
    public Auction(){
        lots = new ArrayList<Lot>();
        nextLotNumber = 1;
    }

    /**
     * Enter a new lot into the auction.
     * @param description A description of the lot.
     */
    public void enterLot(String description){
        lots.add(new Lot(nextLotNumber, description));
        nextLotNumber++;
    }

    /**
     * Show the full list of lots in this auction.
     */
    public void showLots() {
        for(Lot lot : lots) {
            System.out.println(lot.toString());
        }
    }
    
    // Muestra los detalles de todos los elementos que estan en subasta
    // Si alguno tiene pujas, debe mostrarse su informacion
    // Si no, indicar que no hay pujas
    public void close(){
        for(Lot articulo : lots){
            Bid puja = articulo.getHighestBid();
            if (puja != null){
                System.out.println("-> Maximo pujador: " + puja.getBidder().getName());
            }else{
                System.out.println("-> No hay pujas");
            }
            System.out.println(articulo.toString() + "\n");
        }
    }
    
    // Muestra los detalles de los articulos por los que no se ha pujado
    public ArrayList<Lot> getUnsold(){
        ArrayList<Lot> sinPujas = new ArrayList<Lot>();
        for(Lot articulo : lots){
            if (articulo.getHighestBid() == null){
                sinPujas.add(articulo);
            }
        }
        return sinPujas;
    }
    
    /**
     * Make a bid for a lot.
     * A message is printed indicating whether the bid is
     * successful or not.
     *
     * @param lotNumber The lot being bid for.
     * @param bidder The person bidding for the lot.
     * @param value The value of the bid.
     */
    public void makeABid(int lotNumber, Person bidder, long value){
        Lot selectedLot = getLot(lotNumber);
        if(selectedLot != null) {
            boolean successful = selectedLot.bidFor(new Bid(bidder, value));
            if(successful) {
                System.out.println("The bid for lot number " +
                    lotNumber + " was successful.");
            }else{
                // Report which bid is higher.
                System.out.println("Lot number: " + lotNumber +
                    " already has a bid of: " +
                    selectedLot.getHighestBid().getValue());
            }
        }
    }

    /**
     * Return the lot with the given number. Return null
     * if a lot with this number does not exist.
     * @param lotNumber The number of the lot to return.
     */
    public Lot getLot(int lotNumber){
        Lot articulo = null; // articulo a devolver
        if((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
            boolean superado = false; // comprobacion de superado, inicialmente NO
            int numeroID; // identificador del articulo
            Iterator<Lot> iterador = lots.iterator(); // recorre los articulos
            while (iterador.hasNext() && !superado && articulo != null){
                // mientras haya articulos entre los que buscar....
                // no se haya superado el identificador buscado...
                // y no se haya encontrado el articulo
                articulo = iterador.next();
                numeroID = articulo.getNumber();
                if(numeroID > lotNumber){ // si el identificador actual supera al buscado
                    superado = true;
                    articulo = null;
                }else if (numeroID != lotNumber){ // si no se ha encontrado aun el articulo
                    articulo = null;
                }
            }
            if(articulo == null){
                System.out.println("El articulo " + lotNumber + " fue borrado anteriormente");
            }
        }else{
            System.out.println("Lot number: " + lotNumber + " does not exist.");
        }
        return articulo;
    }
    
    public Lot removeLot(int lotNumber){
        Lot articulo = null;
        if((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
            boolean borrado = false; // comprobacion de borrado, inicialmente NO
            boolean superado = false; // comprobacion de superado, inicialmente NO
            int numeroID;
            Iterator<Lot> iterador = lots.iterator(); // recorre los articulos
            while (iterador.hasNext() && !borrado && !superado){
                // mientras haya articulos entre los que buscar....
                // no se acabe de borrar el articulo...
                // y no se haya superado el identificador buscado
                articulo = iterador.next();
                numeroID = articulo.getNumber();
                if(numeroID == lotNumber){
                    // si hemos encontrado el identificador que queremos borrar
                    iterador.remove();
                    borrado = true;
                }else if (numeroID > lotNumber){
                    // si hemos superado el identificador a borrar
                    superado = true;
                }
            }
            if (borrado){ // si hemos borrado AHORA el articulo buscado
                System.out.println("El articulo " + lotNumber + " se ha borrado satisfactoriamente");
            }else{ // si el articulo ha sido borrado antes
                articulo = null;
                System.out.println("El articulo " + lotNumber + " fue borrado anteriormente");
            }
        }else{
            System.out.println("Lot number: " + lotNumber + " does not exist.");
        }
        return articulo;
    }
}