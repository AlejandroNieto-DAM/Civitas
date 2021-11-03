/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package civitas;

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
    
    public CivitasJuego(ArrayList<String> nombreJugadores){
        
        jugadores = new ArrayList();
        for(int i = 0; i < nombreJugadores.size(); i++){
            jugadores.add(new Jugador(nombreJugadores.get(i)));
        }
        
        gestor = new GestorEstados();
        estadoJuego = gestor.estadoInicial();
        
        Dado.getInstance().setDebug(true);
        indiceJugadorActual = Dado.getInstance().quienEmpieza(jugadores.size());

        mazoSorpresas = new MazoSorpresas();
        
        tablero = new Tablero();
        this.iniciaTablero(mazoSorpresas);
        this.inicializaMazoSorpresas();
        
    }
    
    private void avanzaJugador(){
        Jugador jugadorActual = this.getJugadorActual();
        int posicionActual = jugadorActual.getCasillaActual();
        int tirada = Dado.getInstance().tirar();
        int posicionNueva = this.tablero.nuevaPosicion(posicionActual, tirada);
        Casilla casilla = this.tablero.getCasilla(posicionNueva);
        this.contabilizarPasosPorSalida(jugadorActual);
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(this.getIndiceJugadorActual(), this.jugadores);
    }
    
    public boolean comprar(){
        Jugador jugadorActual = this.getJugadorActual();
        int numCasillaActual = jugadorActual.getCasillaActual();
        Casilla casilla = this.tablero.getCasilla(numCasillaActual);
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
            this.mazoSorpresas.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, "PAGAR COBRAR", 500));
            this.mazoSorpresas.alMazo(new Sorpresa(TipoSorpresa.PAGARCOBRAR, "PAGAR COBRAR", -500));
        }
        
        for(int i = 0; i < 2; i++){
            this.mazoSorpresas.alMazo(new Sorpresa(TipoSorpresa.PORCASAHOTEL, "PAGAR POR CASA HOTEL", 100));
            this.mazoSorpresas.alMazo(new Sorpresa(TipoSorpresa.PORCASAHOTEL, "PAGAR POR CASA HOTEL", -100));
        }
        
    }
    
    private void iniciaTablero(MazoSorpresas mazo){
        
        Random rd = new Random();
        
        this.tablero.aniadeCasilla(new Casilla("SALIDA"));
        int util = 0;
        for(int i = 0; i < 4; i++){
            
            for(int j = 0; j < 4; j++){
                this.tablero.aniadeCasilla(new Casilla("Ciudad " + util, rd.nextInt(800), rd.nextInt(2500) + 1300, rd.nextInt(300)));
                util++;
            }
            
            if(i == 2){
                this.tablero.aniadeCasilla(new Casilla("PARKING"));
            }

            this.tablero.aniadeCasilla(new Casilla("Sorpresa " + i%4, this.mazoSorpresas));

        }
         
    }
    
    private void pasarTurno(){
        this.indiceJugadorActual = (this.indiceJugadorActual + 1)%this.jugadores.size(); 
    }
    
    public ArrayList<Jugador> ranking(){
        ArrayList ranking = (ArrayList<Jugador>) this.jugadores.clone();
        
        Collections.sort(ranking);
        
        return ranking;
        
        
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
