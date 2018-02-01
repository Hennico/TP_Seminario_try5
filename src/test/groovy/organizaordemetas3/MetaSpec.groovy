package organizaordemetas3

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class MetaSpec extends Specification implements DomainUnitTest<Meta> {

    def setup() {
    }

    def cleanup() {
    }

    void "inicia en pendiente"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
		when:
			
		expect:
			metaManual.estado == Estado.PENDIENTE
    }	
	
    void "PENDIENTE a EN_EJECUCION"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
			metaManual.cambiarEstado(Estado.EN_EJECUCION)
		when:
			
		expect:
			metaManual.estado == Estado.EN_EJECUCION
    }		

    void "EN_EJECUCION a FINALIZADA"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
			metaManual.cambiarEstado(Estado.EN_EJECUCION)
			metaManual.cambiarEstado(Estado.FINALIZADA)
		when:
			
		expect:
			metaManual.estado == Estado.FINALIZADA
    }	
	
	void "PENDIENTE a CANCELADA"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
			metaManual.cambiarEstado(Estado.CANCELADA)
		when:
			
		expect:
			metaManual.estado == Estado.CANCELADA
    }		

    void "EN_EJECUCION a CANCELADA"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
			metaManual.cambiarEstado(Estado.EN_EJECUCION)
			metaManual.cambiarEstado(Estado.CANCELADA)
		when:
			
		expect:
			metaManual.estado == Estado.CANCELADA
    }
	
	 void "FINALIZADA a CANCELADA"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
			metaManual.cambiarEstado(Estado.EN_EJECUCION)
			metaManual.cambiarEstado(Estado.FINALIZADA)
		when:
			metaManual.cambiarEstado(Estado.CANCELADA)
		then:
			thrown CambioEstadoInvalido
    }	
	
    void "cambiar estado de pendiente a finalizada con cambio manual da error"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
		
		when:
			metaManual.cambiarEstado(Estado.FINALIZADA)
			
		then:
			thrown CambioEstadoInvalido
    }
	
	void "cambiar estado de pendiente a en ejecucion con cambio manual no da error"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
		
		when:
			metaManual.cambiarEstado(Estado.EN_EJECUCION)
			
		then:
			notThrown CambioEstadoInvalido
    }
	
	void "tira error el intentar iniciar sin terminar los anteriores"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
			metaManual.agregarSubMetas(new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual()))
		when:
			metaManual.cambiarEstado(Estado.EN_EJECUCION)
			
		then:
			thrown CambioEstadoInvalido
    }
	
	void "no tira error el intentar iniciar habiendo cancelado los anteriores"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
			Meta subMeta = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
			metaManual.agregarSubMetas(subMeta)
			subMeta.cambiarEstado(Estado.CANCELADA)
		when:
			metaManual.cambiarEstado(Estado.EN_EJECUCION)
			
		then:
			notThrown CambioEstadoInvalido
    }
	
	void "no tira error el intentar iniciar habiendo Terminado los anteriores"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
			Meta subMeta = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
			metaManual.agregarSubMetas(subMeta)
			subMeta.cambiarEstado(Estado.EN_EJECUCION)
			subMeta.cambiarEstado(Estado.FINALIZADA)
		when:
			metaManual.cambiarEstado(Estado.EN_EJECUCION)
			
		then:
			notThrown CambioEstadoInvalido
    }
	
	void "no tira error el intentar iniciar no hbiendo terminado los Opcionales"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
			Meta subMeta = new Meta("textear", "textear programa", Obligatoriedad.OPCIONAL, new CambioDeEstadoManual())
			metaManual.agregarSubMetas(subMeta)
		when:
			metaManual.cambiarEstado(Estado.EN_EJECUCION)
			
		then:
			notThrown CambioEstadoInvalido
    }
	
	void "tira error el intentar terminar no hbiendo terminado los Opcionales"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
			Meta subMeta = new Meta("textear", "textear programa", Obligatoriedad.OPCIONAL, new CambioDeEstadoManual())
			metaManual.agregarSubMetas(subMeta)
			metaManual.cambiarEstado(Estado.EN_EJECUCION)
		when:
			metaManual.cambiarEstado(Estado.FINALIZADA)
			
		then:
			thrown CambioEstadoInvalido
    }
	
	void "no tira error el intentar terminar hbiendo terminado los Opcionales"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoManual())
			Meta subMeta = new Meta("textear", "textear programa", Obligatoriedad.OPCIONAL, new CambioDeEstadoManual())
			metaManual.agregarSubMetas(subMeta)
			metaManual.cambiarEstado(Estado.EN_EJECUCION)
			subMeta.cambiarEstado(Estado.EN_EJECUCION)
			subMeta.cambiarEstado(Estado.FINALIZADA)
		when:
			metaManual.cambiarEstado(Estado.FINALIZADA)
			
		then:
			notThrown CambioEstadoInvalido
    }

	void "Si se termina todas las SubMeta, se termina"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoAutomatico())
			Meta subMeta = new Meta("textear", "textear programa", Obligatoriedad.OPCIONAL, new CambioDeEstadoManual())
			metaManual.agregarSubMetas(subMeta)
			subMeta.cambiarEstado(Estado.EN_EJECUCION)
			subMeta.cambiarEstado(Estado.FINALIZADA)
		when:
			
		expect:
			metaManual.estado == Estado.FINALIZADA
    }
	
	void "Si no se termina todas las SubMeta, no se termina"() {
		setup:
			Meta metaManual = new Meta("textear", "textear programa", Obligatoriedad.NECESARIO, new CambioDeEstadoAutomatico())
			Meta subMeta = new Meta("textear", "textear programa", Obligatoriedad.OPCIONAL, new CambioDeEstadoManual())
			metaManual.agregarSubMetas(subMeta)
			Meta subMeta1 = new Meta("textear", "textear programa", Obligatoriedad.OPCIONAL, new CambioDeEstadoManual())
			metaManual.agregarSubMetas(subMeta1)
			subMeta.cambiarEstado(Estado.EN_EJECUCION)
			subMeta.cambiarEstado(Estado.FINALIZADA)
		when:
			
		expect:
			metaManual.estado == Estado.PENDIENTE
    }
	
}
