package tpFinal;

public abstract class Usuario {
	private int id;
	private boolean esExperto;
	
	public abstract void SubirMuestra(Muestra m);
	public void opinar(Muestra m, Vinchuca v) {
		m.agregarOpinion(v, this,m);
	}
	
	public boolean isEsExperto() {
		return esExperto;
	}
	public void setEsExperto(boolean esExperto) {
		this.esExperto = esExperto;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
