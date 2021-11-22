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
    
    private String nombre;

    Casilla(String nombre) {
        this.nombre = nombre;  
    }
    

    void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("El jugador: "  + todos.get(actual).getNombre() + "\n" +
                                            "Cae en la casilla: " + this.toString() + "\n");
    }
    
      
    public String getNombre() {
        return nombre;
    }
 
    @Override
    public String toString() {
        String cadena = "\n"+ this.getNombre(); 
        return cadena;
    }
    
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        this.informe(iactual, todos);
    }
    
}
