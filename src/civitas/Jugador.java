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
public class Jugador implements Comparable<Jugador> {
    
    protected int CasasMax = 4;
    protected int CasasPorHotel = 4;
    private int casillaActual;
    protected int HotelesMax = 4;
    private String nombre;
    protected float PasoPorSalida = 1000;
    private boolean puedeComprar;
    private float saldo;
    private float SaldoInicial = 7500;
    
    private ArrayList<Casilla> propiedades;
    
    
    Jugador(String nombre){
        this.propiedades = new ArrayList();
        this.nombre = nombre;
        this.puedeComprar = false;
        this.saldo = this.SaldoInicial;
        this.casillaActual = 0;
    }
    
    protected Jugador(Jugador otro){
        this.nombre = otro.getNombre();
        this.saldo = otro.getSaldo();
        this.puedeComprar = otro.getPuedeComprar();
        this.casillaActual = otro.getCasillaActual();
        
        this.propiedades = otro.getPropiedades();
    }
    
    
    int cantidadCasasHoteles(){
        return this.propiedades.size();
    }

    boolean comprar(Casilla titulo){
        boolean result = false;
        if(this.puedeComprar){
            float precio = titulo.getPrecioCompra();
            if(this.puedoGastar(precio)){
                result = titulo.comprar(this);
                this.propiedades.add(titulo);
                Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " compra la propiedad " + titulo.getNombre());
                this.puedeComprar = false;
            } else {
                Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " no tiene saldo para comprar la propiedad " + titulo.getNombre());
            }
        }
        return result;
    }
    
    boolean construirCasa(int ip){
        boolean result = false;
        boolean existe = this.existeLaPropiedad(ip);
        System.out.println("***** existe " + existe);
        if(existe){
            Casilla casilla = this.propiedades.get(ip);
            boolean puedoEdificar = this.puedoEdificarCasa(casilla);
            System.out.println("***** puedoEdificar " + puedoEdificar);

            if(puedoEdificar){
                result = true;
                casilla.construirCasa(this);
                Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " construye una casa en la propiedad " + casilla.getNombre());
            }
        }
        return result;
    }
    
    boolean construirHotel(int ip){
        boolean result = false;
        if(this.existeLaPropiedad(ip)){
            Casilla propiedad = this.propiedades.get(ip);
            boolean puedoEdificarHotel = this.puedoEdificarHotel(propiedad);
            
            if(puedoEdificarHotel){
                result = propiedad.construirHotel(this);
                propiedad.derruirCasas(this.CasasPorHotel, this);
                Diario.getInstance().ocurreEvento("El jugador " + this.nombre + " construye un hotel en la propiedad " + propiedad.getNombre());
            }
        }
        return result;
    }
    
    boolean enBancarrota(){
        return this.saldo < 0 ? true : false;
    }
    
    private boolean existeLaPropiedad(int ip){
        boolean exists = false;
        if(this.propiedades.get(ip) != null){
            exists = true;
        }
        return exists;
    }
    
    private int getCasasMax(){
        return this.CasasMax;
    }
    
    int getCasasPorHotel(){
        return this.CasasPorHotel;
    }
    
    public int getCasillaActual(){
        return this.casillaActual;
    }
    
    private int getHotelesMax(){
        return this.HotelesMax;
    }
    
    protected String getNombre(){
        return this.nombre;
    }
    
    private float getPremioPasoPorSalida(){
        return this.PasoPorSalida;
    }
    
    public ArrayList<Casilla> getPropiedades(){
        return this.propiedades;
    }
    
    boolean getPuedeComprar(){
        return this.puedeComprar;
    }
    
    protected float getSaldo(){
        return this.saldo;
    }
    
    boolean modificarSaldo(float cantidad){
        this.saldo += cantidad;
        Diario.getInstance().ocurreEvento("El jugador: " + this.getNombre() + " modifica su saldo a: " + this.getSaldo());
        return true;
    }
    
    boolean moverACasilla(int numCasilla){
        this.casillaActual = numCasilla;
        this.puedeComprar = false;
        Diario.getInstance().ocurreEvento("El jugador: " + this.getNombre() + " se mueve a la casilla " + this.getCasillaActual());
        return true;
    }
    
    boolean paga(float cantidad){
        return this.modificarSaldo(cantidad * -1);
    }
    
    boolean pagaAlquiler(float cantidad){
        return this.paga(cantidad);
    }
    
    boolean pasaPorSalida(){
        this.recibe(this.getPremioPasoPorSalida());
        Diario.getInstance().ocurreEvento("El jugador " + this.getNombre() + " pasa por salida y recibe un premio de " + this.getPremioPasoPorSalida() + "$!!!");
        return true;
    }
    
    boolean puedeComprarCasilla(){
        this.puedeComprar = true;
        return this.getPuedeComprar();
    }
    
    
    private boolean puedoEdificarCasa(Casilla propiedad){
        boolean can = false;
        
        if(propiedad.getNumCasas() <= this.getCasasMax() && this.puedoGastar(propiedad.getPrecioEdificar())){
            can = true;
        }
        
        return can;
    }
    
    private boolean puedoEdificarHotel(Casilla propiedad){
        boolean can = false;
        
        if(this.puedoGastar(propiedad.getPrecioEdificar()) && propiedad.getNumHoteles() < this.getHotelesMax() && propiedad.getNumCasas() >= this.getCasasPorHotel()){
            can = true;
        }
        
        return can;
    }
    
    private boolean puedoGastar(float precio){
        return this.saldo > precio;
    }
    
    boolean recibe(float cantidad){
        return this.modificarSaldo(cantidad);
    }
    
    boolean tieneAlgoQueGestionar(){
        return this.propiedades.size() > 0;
    }
    
    @Override
    public String toString(){
        
        int casas = 0;
        
        for(int i = 0; i < this.propiedades.size(); i++){
            casas += this.propiedades.get(i).getNumCasas();
        }
        String infoP = "\nNombre " + this.getNombre() + "\n" +
                 "Saldo " + this.getSaldo() + "\n" +
                 "Casilla " + this.getCasillaActual() + "\n" +
                 "Propiedades " + this.cantidadCasasHoteles() + "\n" +
                 "Casas " + casas + "\n";
        
        
        
        return infoP;
    }
    
    boolean vender(int ip){
        //NO EXISTE COMERCIO EN EL JUEGO
        return false;
    }

    @Override
    public boolean compareTo(Jugador otro) {
        return this.getSaldo() > otro.getSaldo();
    }

    
    
}
