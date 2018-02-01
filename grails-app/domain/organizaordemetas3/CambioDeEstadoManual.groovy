package organizaordemetas3

public class CambioDeEstadoManual extends CambioDeEstado{

	void cambiarEstado(Meta meta, Estado nuevoEstado){
		boolean puedoCambiar = false;
  		switch(nuevoEstado) {
  			case Estado.CANCELADA:
  				puedoCambiar = meta.estado != Estado.FINALIZADA;
				break
				
  			case Estado.FINALIZADA:
				puedoCambiar = (meta.estado == Estado.EN_EJECUCION) && (!meta.subMetas.any { !it.estaCompleta()})
				break

  			case Estado.EN_EJECUCION:
				puedoCambiar = (meta.estado == Estado.PENDIENTE) && (!meta.subMetas.any { !it.estaCompleta() && it.obligatoriedad == Obligatoriedad.NECESARIO})
				break
				
  			case Estado.PENDIENTE:
  				puedoCambiar = false
				break
				
  		}
		if (puedoCambiar)
			meta.estado = nuevoEstado
		else
			throw new CambioEstadoInvalido("El estado que se utilizo no es valido")
	}

    static constraints = {
    }
}
