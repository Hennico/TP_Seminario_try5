package organizadordemetas

public enum Estado {
	PENDIENTE ("Pendiente"),
	EN_EJECUCION ("En ejecucion"),
	CANCELADA ("Cancelada"),
	FINALIZADA ("Finalizada")

	final String value

	Estado(String value) { this.value = value }

	@Override
	String toString() { value }
	String getKey() { name() }

	static Estado convertirEnEnum( String value ) {
    values().find { it.value == value }
	}
}
