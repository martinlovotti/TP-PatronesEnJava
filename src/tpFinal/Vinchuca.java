package tpFinal;

public enum Vinchuca {
	Infestans("Infestans"),
	Sordida("Sordida"),
	Guasayana("Guasayana"),
	ChincheFoliada("ChincheFoliada"),
	PhtiaChinche("Phtia-Chinche"),
	Ninguna("Ninguna"),
	ImagenPocoClara("ImagenPocoClara");
	
	String tipo;
	
	Vinchuca(String tipo){
		this.tipo = tipo;
	}
	
	public String imprimir() {
		return this.tipo;
	}
}
