
import GUI.CapturaNombres;
import GUI.CivitasView;
import civitas.CivitasJuego;
import controladorCivitas.Controlador;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author alejandronietoalarcon
 */
public class TestP5 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CivitasView vista = new CivitasView();
        CapturaNombres vistaNombres = new CapturaNombres(vista, true);
        ArrayList<String> nombresJugadores = vistaNombres.getNombres();
        CivitasJuego modelo = new CivitasJuego(nombresJugadores, true);
        Controlador controlador = new Controlador(modelo, vista);
        vista.setCivitasJuego(modelo);
        controlador.juega();
    }
    
}
