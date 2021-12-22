/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package civitas;

import GUI.VistaDado;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author alejandronietoalarcon
 */
public class CivitasJuego {
    
    private int indiceJugadorActual;
    
    private ArrayList<Jugador> jugadores;
    
    private GestorEstados gestor;
    private EstadoJuego estadoJuego;
    
    private MazoSorpresas mazoSorpresas;
    
    private Tablero tablero;
    
    public CivitasJuego(ArrayList<String> nombreJugadores, boolean debug){
        
        jugadores = new ArrayList();
        for(int i = 0; i < nombreJugadores.size(); i++){
            jugadores.add(new Jugador(nombreJugadores.get(i)));
        }
        
        gestor = new GestorEstados();
        estadoJuego = gestor.estadoInicial();
        
        VistaDado.getInstance().setDebug(debug);
        indiceJugadorActual = VistaDado.getInstance().quienEmpieza(jugadores.size());

        mazoSorpresas = new MazoSorpresas();
        
        tablero = new Tablero();
        this.iniciaTablero(mazoSorpresas);
        this.inicializaMazoSorpresas();
        
    }
    
    private void avanzaJugador(){
        Jugador jugadorActual = this.getJugadorActual();
        int posicionActual = jugadorActual.getCasillaActual();
        int tirada = VistaDado.getInstance().tirar();
        int posicionNueva = this.tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = this.tablero.getCasilla(posicionNueva);
        this.contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(this.getIndiceJugadorActual(), this.jugadores);
    }
    
    public boolean comprar(){
        Jugador jugadorActual = this.getJugadorActual();
        int numCasillaActual = jugadorActual.getCasillaActual();
        CasillaCalle casilla = (CasillaCalle) this.tablero.getCasilla(numCasillaActual);
        return jugadorActual.comprar(casilla);
    }
    
    public boolean construirCasa(int ip){
        return this.jugadores.get(this.indiceJugadorActual).construirCasa(ip);
    }
    
    public boolean construirHotel(int ip){
         return this.jugadores.get(this.indiceJugadorActual).construirHotel(ip);
    }
    
    private void contabilizarPasosPorSalida(Jugador jugadorActual){
        if(this.tablero.computarPasoPorSalida()){
            jugadorActual.pasaPorSalida();
        }
    }
    
    public boolean finalDelJuego(){
        boolean bancarrota = false;
        for(int i = 0; i < this.jugadores.size() && bancarrota == false; i++){
            if(this.jugadores.get(i).enBancarrota()){
                bancarrota = true;
            }
        }
        
        return bancarrota;
    }
    
    public int getIndiceJugadorActual(){
        return this.indiceJugadorActual;
    }
    
    public Jugador getJugadorActual(){
        return this.jugadores.get(this.indiceJugadorActual);
    }
    
    public ArrayList<Jugador> getJugadores(){
        return this.jugadores;
    }
    
    public Tablero getTablero(){
        return this.tablero;
    }
    
    private void inicializaMazoSorpresas(){
       
        for(int i = 0; i < 3; i++){
            this.mazoSorpresas.alMazo(new SorpresaPagarCobrar("COBRAR", 500));
            this.mazoSorpresas.alMazo(new SorpresaPagarCobrar("PAGAR", -500));
        }
        
        for(int i = 0; i < 2; i++){
            this.mazoSorpresas.alMazo(new SorpresaPorCasaHotel("COBRAR POR CASA HOTEL", 100));
            this.mazoSorpresas.alMazo(new SorpresaPorCasaHotel("PAGAR POR CASA HOTEL", -100));
        }
        
    }
    
    private void iniciaTablero(MazoSorpresas mazo){
        
        Random rd = new Random();
        
        this.tablero.aniadeCasilla(new Casilla("SALIDA"));
        int util = 0;
        for(int i = 0; i < 4; i++){
            
            for(int j = 0; j < 4; j++){
                this.tablero.aniadeCasilla(new CasillaCalle("Ciudad " + util, rd.nextInt(5500) + 2200, rd.nextInt(3000) + 1500, rd.nextInt(1500)+ 500));
                util++;
            }
            
            if(i == 2){
                this.tablero.aniadeCasilla(new Casilla("PARKING"));
            }

            this.tablero.aniadeCasilla(new CasillaSorpresa("Sorpresa " + i%4, this.mazoSorpresas));

        }
         
    }
    
    private void pasarTurno(){
        this.indiceJugadorActual = (this.indiceJugadorActual + 1)%this.jugadores.size(); 
    }
    
    public ArrayList<Jugador> ranking(){        
        Collections.sort(this.getJugadores());
        return this.jugadores;
    }
    
    public OperacionJuego siguientePaso(){
        Jugador j = this.getJugadorActual();
        OperacionJuego oj = this.gestor.siguienteOperacion(j, estadoJuego);
        
        if(oj == OperacionJuego.PASAR_TURNO){
            this.pasarTurno();
            this.siguientePasoCompletado(oj);
        } else if(oj == OperacionJuego.AVANZAR){
            this.avanzaJugador();
            this.siguientePasoCompletado(oj);
        }
        

        
        return oj;
    }
    
    public void siguientePasoCompletado(OperacionJuego opciones){
        this.estadoJuego = gestor.siguienteEstado(this.jugadores.get(indiceJugadorActual), estadoJuego, opciones);
    }
    
    
}
