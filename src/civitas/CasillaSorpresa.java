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
public class CasillaSorpresa extends Casilla {
    
    protected MazoSorpresas mazo;
    
    CasillaSorpresa(String nombre, MazoSorpresas mazo){
        super(nombre);
        this.mazo = mazo;
    }
    
    @Override
    void recibeJugador(int iactual, ArrayList<Jugador> todos){
        Sorpresa sorpresa = this.mazo.siguiente();
        this.informe(iactual, todos);
        sorpresa.aplicarAJugador(iactual, todos);
    }
}
