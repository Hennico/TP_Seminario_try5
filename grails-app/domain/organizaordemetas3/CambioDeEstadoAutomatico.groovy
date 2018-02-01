package organizaordemetas3

public class CambioDeEstadoAutomatico extends CambioDeEstado{

	void cambiarEstado(Meta meta){
		boolean puedoCambiar = false
		puedoCambiar = (!meta.subMetas.any { !it.estaCompleta()})
		if (puedoCambiar)
			meta.estado = Estado.FINALIZADA
  	}

    static constraints = {
    }
}
