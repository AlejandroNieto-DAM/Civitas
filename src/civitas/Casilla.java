/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package civitas;

import java.util.ArrayList;



/**
 *
 * @author alejandronietoalarcon
 */
public class Casilla {
    
    private static float FACTOALQUILERCALLE = 1.0f;
    private static float FACTORALQUILERCASA = 1.0f;
    private static float FACTORALQUILERHOTEL = 4.0f;
     
    private TipoCasilla tipo;
    private String nombre;
    private float precioCompra;
    private float precioEdificar;
    private float precioBaseAlquiler;
    private int numCasas;
    private int numHoteles;
    
    
    private MazoSorpresas mazo;
    
    
    private Jugador propietario;
    
    
    public int cantidadCasasHoteles(){
        return this.getNumCasas() + this.getNumHoteles();
    }
    
        
    Casilla(String nombre, float precioCompra, float precioEdificar, float precioBaseAlquiler) {
        init();
        this.tipo = TipoCasilla.CALLE;
        this.nombre = nombre;
        this.precioCompra = precioCompra;
        this.precioEdificar = precioEdificar;
        this.precioBaseAlquiler = precioBaseAlquiler;  
    }
    
    Casilla(String nombre) {
        init();
        this.tipo = TipoCasilla.DESCANSO;
        this.nombre = nombre;  
    }
    
    Casilla(String nombre, MazoSorpresas mazo) {
        init();
        this.tipo = TipoCasilla.SORPRESA;
        this.nombre = nombre;
        this.mazo = mazo;
    }
    
    boolean construirCasa( Jugador jugador){
        this.propietario.paga(this.precioEdificar);
        this.numCasas++;
        return true;
    }
    
    boolean construirHotel( Jugador jugador){
        this.propietario.paga(this.precioEdificar);
        this.numHoteles++;
        return true;
    }
    
    public boolean comprar(Jugador jugador){
        this.propietario = jugador;
        return this.propietario.paga(this.precioCompra);
    }
    
    private void init(){
        
        this.tipo = null;
        this.nombre = "";
        this.precioCompra = 0;
        this.precioEdificar = 0;
        this.precioBaseAlquiler = 0;
        this.numCasas = 0;
        this.numHoteles = 0;
        
        this.mazo = null;
    }
  
    void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("El jugador: "  + todos.get(actual).getNombre() + "\n" +
                                            "Cae en la casilla: " + this.toString() + "\n");
    }
    
    public void tramitarAlquiler(Jugador jugador){
       if(this.tienePropietario() && !this.esEsteElPropietario(jugador)){
           jugador.pagaAlquiler(this.getPrecioAlquilerCompleto());
           this.propietario.recibe(this.getPrecioAlquilerCompleto());
       }
    }
    
    public boolean esEsteElPropietario(Jugador jugador){
        return this.propietario.getNombre() == jugador.getNombre();
    }
    
    public boolean tienePropietario(){
        return this.propietario != null ? true : false;
    }
    
    boolean derruirCasas(int numero, Jugador jugador){
        boolean operacion = false;
        if(this.esEsteElPropietario(jugador) && this.getNumCasas() >= numero){
            this.numCasas -= numero;
            operacion = true;
        }
        
        return operacion;
        
    }
    
    public String getNombre() {
        return nombre;
    }

    public float getPrecioCompra() {
        return precioCompra;
    }

    public float getPrecioEdificar() {
        return precioEdificar;
    }
    
    public float getPrecioBaseAlquiler() {
        return precioBaseAlquiler;
    }

    public int getNumCasas() {
        return numCasas;
    }

    public int getNumHoteles() {
        return numHoteles;
    }
    
    public float getPrecioAlquilerCompleto() {
        float precioAlquilerCompleto = this.precioBaseAlquiler * (Casilla.FACTOALQUILERCALLE + this.getNumCasas() * Casilla.FACTORALQUILERCASA + this.getNumHoteles() * Casilla.FACTORALQUILERHOTEL);
        return precioAlquilerCompleto;
    }
    
    @Override
    public String toString() {
        
        String cadena = "\n"+ this.getNombre() + ". \nPrecios: \n\tCompra: " + this.getPrecioCompra() + "\n\tEdificar: " + this.getPrecioEdificar() + "\n\tAlquiler base: " + 
                this.getPrecioBaseAlquiler() + "\nCasas: " + this.getNumCasas() + "\nHoteles: " + this.getNumHoteles() + "\n";
        
        if(this.tienePropietario()){
            cadena += "Propietario: " + this.propietario.getNombre() + "\n";
        }
        
        return cadena;
    }
    
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        if(null != this.tipo)switch (this.tipo) {
            case CALLE:
                this.recibeJugador_calle(iactual, todos);
                break;
            case SORPRESA:
                this.recibeJugador_sorpresa(iactual, todos);
                break;
            case DESCANSO:
                this.informe(iactual, todos);
                break;
            default:
                break;
        }
    }
    
    void recibeJugador_calle(int iactual, ArrayList<Jugador> todos){
        this.informe(iactual, todos);
        Jugador jugador = todos.get(iactual);
        if(!this.tienePropietario()){
            jugador.puedeComprarCasilla();
        } else {
            this.tramitarAlquiler(jugador);
        }
    }
    
    void recibeJugador_sorpresa(int iactual, ArrayList<Jugador> todos){
        Sorpresa sorpresa = this.mazo.siguiente();
        this.informe(iactual, todos);
        sorpresa.aplicarAJugador(iactual, todos);
    }
    
}
