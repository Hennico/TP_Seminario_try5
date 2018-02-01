package organizaordemetas3

public class Meta {
	public Estado estado	
  	public List<Meta> subMetas
	List<Meta> listeners
	String nombre
  	String descripcion
	Obligatoriedad obligatoriedad	
	CambioDeEstado metodoDeCambioDeEstado

 	public Meta (String nombre, String descripcion, Obligatoriedad obligatoriedad, CambioDeEstado modo) {
	  	this.subMetas = []
  		this.listeners = []
		this.estado = Estado.PENDIENTE
  		this.nombre = nombre
  		this.descripcion = descripcion
		this.obligatoriedad = obligatoriedad
		this.metodoDeCambioDeEstado = modo
  	}	

  	public boolean estaCompleta() {
		return (estado == Estado.FINALIZADA || estado == Estado.CANCELADA)
  	}	
	
  	public void agregarSubMetas (Meta subMeta) {
  		if (this.estaCompleta())
			throw new CambioEstadoInvalido("No se puede agregar tarea portque esta finalizada/cancelada")
  		if (estado != Estado.PENDIENTE && subMeta.obligatoriedad == NECESARIO)
  			throw new CambioEstadoInvalido("No se puede agregar tarea obligatoria cuando ya se comenzo")

  		subMetas.add(subMeta)
		subMeta.agragarListener(this)
  	}

  	protected void informarCambio() {
  		for(Meta listener : listeners) {
  			listener.informar()
  		}
  	}

	protected void informar() {
  		metodoDeCambioDeEstado.cambiarEstado(this)
  	}
	
  	protected void agragarListener(Meta listener) {
  		listeners.add(listener)
  	}

	void cambiarEstado(Estado nuevoEstado){
		metodoDeCambioDeEstado.cambiarEstado(this, nuevoEstado)
		informarCambio()
  	}

	
    static constraints = {
    }
}
