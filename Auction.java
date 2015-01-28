import java.util.ArrayList;
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
        if((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
            // The number seems to be reasonable.
            try {
                Lot selectedLot = lots.get(lotNumber - 1);// Include a confidence check to be sure we have the
                // right lot.
                if (selectedLot != null){
                    if(selectedLot.getNumber() != lotNumber) {
                        System.out.println("Internal error: Lot number " +
                            selectedLot.getNumber() + " was returned instead of " + lotNumber);
                        System.out.println("El lote: " + lotNumber + " fue borrado en algun momento.");
                        // Don't return an invalid lot.
                        selectedLot = null;
                    }
                    return selectedLot;
                }else{
                    System.out.println("El lote: " + lotNumber + " fue borrado en algun momento.");
                    return null;
                }
            }catch (IndexOutOfBoundsException ioob){
                System.out.println("El lote: " + lotNumber + " fue borrado en algun momento.");
                return null;
            }
        }else{
            System.out.println("Lot number: " + lotNumber +
                " does not exist.");
            return null;
        }
    }
    
    public void removeLot(int lotNumber){
        if((lotNumber >= 1) && (lotNumber < nextLotNumber)) {
            // The number seems to be reasonable.
            // Include a confidence check to be sure we have the right lot.
            if (lots.get(lotNumber - 1) != null){
                lots.remove(lotNumber - 1);
                System.out.println("El lote ha sido borrado satisfactoriamente");
            }else{
                System.out.println("El lote: " + lotNumber + " fue borrado en algun momento.");
            }
        }else{
            System.out.println("Lot number: " + lotNumber + " does not exist.");
        }
    }
}