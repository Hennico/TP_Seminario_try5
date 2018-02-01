package organizaordemetas3

public class Meta {
	private Estado estado	
  	List<Meta> subMetas
	List<Meta> listeners
	String nombre
  	String descripcion
	Obligatoriedad obligatoriedad	
	CambioDeEstado metodoDeCambioDeEstado

 	public Meta (String nombre, String descripcion, Obligatoriedad obligatoriedad) {
	  	this.subMetas = []
  		this.listeners = []
		this.estado = Estado.PENDIENTE
  		this.nombre = nombre
  		this.descripcion = descripcion
		this.obligatoriedad = obligatoriedad
  	}	

  	public boolean estaCompleta() {
		return (estado == Estado.FINALIZADA || estado == Estado.CANCELADA)
  	}	
	
  	public void agregarSubMetas (Mata subMeta) {
  		if (this.estaCompleta())
			throw new CambioEstadoInvalido("No se puede agregar tarea portque esta finalizada/cancelada")
  		if (estado != Estado.PENDIENTE && subMeta.obligatoriedad == NECESARIO)
  			throw new CambioEstadoInvalido("No se puede agregar tarea obligatoria cuando ya se comenzo")

  		subMetas.add(subMeta)
  	}

  	protected void informarCambio() {
  		for(Objetivo listener : listeners) {
  			listener.informar()
  		}
  	}

  	protected void agragarListener(Objetivo listener) {
  		listeners.add(listener)
  	}

	void cambiarEstado(Estado nuevoEstado){
		cambiarEstado(this, nuevoEstado)
		padre.CambieEstado()
  	}
	
	void cambieEstado(){
		
	}
	
    static constraints = {
    }
}
