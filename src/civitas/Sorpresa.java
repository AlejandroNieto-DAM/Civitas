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
public abstract class Sorpresa {
    
    protected String texto;
    protected int valor;
       
    
    Sorpresa(String texto, int valor){
        this.texto = texto;
        this.valor = valor;
    }
    
    abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);
    
    
    protected void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("Se aplica la sorpresa: " + this.toString() + " al jugador " + todos.get(actual).getNombre());
    }
    
    @Override
    public String toString(){
        return this.texto;
    }
    
}
