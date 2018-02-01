package organizaordemetas3

package private class CambioDeEstadoAutomatico extends cambiarDeEstado{

	cambiarEstado(Meta meta, Estado nuevoEstado){
		bool puedoCambiar = false;
  		switch(nuevoEstado) {
  			case Estado.CANCELADA:
  				puedoCambiar = meta.estado != Estado.FINALIZADA;

  			case Estado.FINALIZADA:
				puedoCambiar = (meta.estado == Estado.EN_EJECUCION) && (!plan.any { !it.estaCompleta()})

  			case Estado.EN_EJECUCION:
				puedoCambiar = (meta.estado == Estado.PENDIENTE) && (!plan.any { !it.estaCompleta() && it.obligatoriedad == Obligatoriedad.NECESARIO})

  			case Estado.PENDIENTE:
  				puedoCambiar = false
  		}
		if (puedoCambiar)
			meta.estado = nuevoEstado
		else
			throw new CambioEstadoInvalido("El estado que se utilizo no es valido")
  	}

    static constraints = {
    }
}
