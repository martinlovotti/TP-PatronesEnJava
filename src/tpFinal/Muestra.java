package tpFinal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;



public class Muestra {
    Vinchuca opinion;
    Ubicacion ubicacion;
    private int usuario;
    private Usuario propietario;
    private List<Vinchuca> opiniones;
    EstadoMuestra estadoActual;
    private LocalDate creacion;
    private LocalDate fechaUltimaVotacion;
    
    //Constructor
    public Muestra(Vinchuca opinion, Ubicacion ubicacion, Usuario propietario) {
        this.opinion = opinion;
        this.ubicacion = ubicacion;
        this.usuario = propietario.getId();
        this.creacion = LocalDate.now();
        this.opiniones = new ArrayList<>();
        this.fechaUltimaVotacion = LocalDate.now();
        this.setPropietario(propietario);
        this.estadoActual = new EstadoMuestraProceso();
        this.agregarOpinion(opinion, propietario, this);
        
    }
    
    //Getters y Setters
    public LocalDate getFechaCreacion() {
    	return creacion;
    }
    
    public LocalDate getFechaUltimaVotacion() {
        return this.fechaUltimaVotacion;
    }
    
    public void actualizarUltimaVotacion() {
    	this.fechaUltimaVotacion = LocalDate.now();
    }
    
    public void setEstadoMuestra(EstadoMuestra e) {
    	this.estadoActual = e;
    }
    
    public void setOpinion(Vinchuca v) {
    	this.opinion = v;
    }
    
    public int identificacion() {
        return usuario;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }
    
    public EstadoMuestra getEstadoActual() {
    	return estadoActual;
    }
    
    public Vinchuca getOpinion() {
    	return this.opinion;
    }
    
    //Cada vez que se solicite el resultado dependerá del estado en el que se encuentra
    public Vinchuca getResultadoActual() {
       return estadoActual.calcularResultado(this);
    }
    
 
    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    public int getPropietarioId() {
        return this.propietario.getId();
    }
    
  
  
    //Métodos de muestra
    public void agregarOpinion(Vinchuca v, Usuario u, Muestra m) {
        estadoActual.agregarOpinion(v,u,m);
    }
    
    public void addOpinion(Vinchuca v) {
    	this.opiniones.add(v);
    }
    
    public int obtenerVotosDe(Vinchuca v) {
        return Collections.frequency(opiniones, v);
    }
    
    public boolean fueVotado(Vinchuca v) {
    	return opiniones.contains(v);
    }
    
    //public Vinchuca obtenerVinchucaConMasVotos() {
    //    Vinchuca resultado = null;
    //    int maxVotos = -1;

    //    for (Map.Entry<Vinchuca, Integer> entrada : historial.entrySet()) {
    //        if (entrada.getValue() > maxVotos) {
    //            maxVotos = entrada.getValue();
    //            resultado = entrada.getKey();
    //        }
    //    }
    //    return resultado;
    //}
    
    //Metodo que encapsula operaciones de opinar
    public void realizarOpinion(Vinchuca v) {
    	this.addOpinion(v);
    	this.setOpinion(getResultadoActual()); //El getResultadoActualDependerá del estado
    	this.actualizarUltimaVotacion();
    }
    
    public Vinchuca obtenerVinchucaConMasVotos() {
        return opiniones.stream()
            .collect(Collectors.groupingBy(
                Function.identity(),
                Collectors.counting() //agrupa los elementos por tipo de Vinchuca y los cuenta.
            ))
            .entrySet().stream() // convierte el Map a un stream de pares (clave = tipo de vinchuca, valor = cantidad).
            .max(Map.Entry.comparingByValue()) //busca el tipo con la mayor cantidad
            .map(Map.Entry::getKey)
            .orElse(null); //obtiene la clave (el tipo de vinchuca) o null si no hay votos.
    }

}