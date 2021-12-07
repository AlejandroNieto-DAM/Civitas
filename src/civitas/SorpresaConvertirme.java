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
public class SorpresaConvertirme extends Sorpresa {

    public SorpresaConvertirme(String texto, int valor) {
        super(texto, valor);
    }

    @Override
    void aplicarAJugador(int actual, ArrayList<Jugador> todos) {
        informe(actual, todos);
        JugadorEspeculador aux = todos.get(actual).convertirme();
        todos.set(actual, aux);
    }
    
}
