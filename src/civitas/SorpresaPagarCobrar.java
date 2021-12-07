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
public class SorpresaPagarCobrar extends Sorpresa {
    
    public SorpresaPagarCobrar(String texto, int valor) {
        super(texto, valor);
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        this.informe(actual, todos);
        todos.get(actual).modificarSaldo(this.valor);
    }

}
