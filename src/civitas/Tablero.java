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
public class Tablero {
    
    private ArrayList<Casilla> casillas;
    private boolean porSalida;
    
    private boolean tieneJuez;
    
    public Tablero() {
        casillas = new ArrayList();
        this.porSalida = false;
    }
    
    private boolean correcto(int numCasilla){
        boolean correcto = false;
        if(numCasilla >= 0 && numCasilla < this.casillas.size()){
            correcto = true;
        }
        return correcto;
    }
    
    boolean computarPasoPorSalida(){
        boolean lastPorSalida = this.porSalida;
        this.porSalida = false;
        return lastPorSalida;
    }
    
    void aniadeCasilla(Casilla casilla){
        this.casillas.add(casilla);
    }
    
    public Casilla getCasilla(int numCasilla){
        if(!this.correcto(numCasilla)){
            return null;
        }
        
        return this.casillas.get(numCasilla);
    }
    
    public ArrayList<Casilla> getCasillas(){
        return this.casillas;
    }
    
    int nuevaPosicion(int actual, int tirado){
        int nuevaPosicion = (actual+tirado) % this.getCasillas().size();
        if(nuevaPosicion != (actual+tirado)){
            this.porSalida = true;
        }
        return nuevaPosicion;
    }
    
    
    void aniadeJuez(){
        
    }
    
    
}
