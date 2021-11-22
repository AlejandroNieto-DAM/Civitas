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
public class CasillaCalle extends Casilla {
    
    private static float FACTOALQUILERCALLE = 1.0f;
    private static float FACTORALQUILERCASA = 1.0f;
    private static float FACTORALQUILERHOTEL = 4.0f;
     
    private float precioCompra;
    private float precioEdificar;
    private float precioBaseAlquiler;
    private int numCasas;
    private int numHoteles;
    
    private Jugador propietario;
    
    CasillaCalle(String nombre, float precioCompra, float precioEdificar, float precioBaseAlquiler) {
        super(nombre);
        this.precioCompra = precioCompra;
        this.precioEdificar = precioEdificar;
        this.precioBaseAlquiler = precioBaseAlquiler;  
    }
    
    public int cantidadCasasHoteles(){
        return this.getNumCasas() + this.getNumHoteles();
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
    
    public void tramitarAlquiler(Jugador jugador){
       if(this.tienePropietario() && !this.esEsteElPropietario(jugador)){
           jugador.pagaAlquiler(this.getPrecioAlquilerCompleto());
           this.propietario.recibe(this.getPrecioAlquilerCompleto());
       }
    }
    
    public boolean esEsteElPropietario(Jugador jugador){
        return this.propietario.getNombre().equals(jugador.getNombre());
    }
    
    public boolean tienePropietario(){
        return this.propietario != null;
    }
    
    boolean derruirCasas(int numero, Jugador jugador){
        boolean operacion = false;
        if(this.esEsteElPropietario(jugador) && this.getNumCasas() >= numero){
            this.numCasas -= numero;
            operacion = true;
        }
        
        return operacion;
        
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
        float precioAlquilerCompleto = this.precioBaseAlquiler * (CasillaCalle.FACTOALQUILERCALLE + this.getNumCasas() * CasillaCalle.FACTORALQUILERCASA + this.getNumHoteles() * CasillaCalle.FACTORALQUILERHOTEL);
        return precioAlquilerCompleto;
    }
    
    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        this.informe(iactual, todos);
        Jugador jugador = todos.get(iactual);
        if(!this.tienePropietario()){
            jugador.puedeComprarCasilla();
        } else {
            this.tramitarAlquiler(jugador);
        }
    }
    
    
    @Override 
    public String toString(){
        String cadena = super.toString() + ". \nPrecios: \n\tCompra: " + this.getPrecioCompra() + "\n\tEdificar: " + this.getPrecioEdificar() + "\n\tAlquiler base: " + 
                this.getPrecioBaseAlquiler() + "\nCasas: " + this.getNumCasas() + "\nHoteles: " + this.getNumHoteles() + "\n";
        
        if(this.tienePropietario()){
            cadena += "Propietario: " + this.propietario.getNombre() + "\n";
        }
        
        return cadena;
    }
}
