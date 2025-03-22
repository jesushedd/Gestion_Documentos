package modelo;

import java.util.Comparator;

public final class ComparadoresDocumento {
    // Comparadores para fecha
    public static final Comparator<Documento> FECHA_ASCENDENTE = Comparator.comparing(Documento::getFecha);
    public static final Comparator<Documento> FECHA_DESCENDENTE = FECHA_ASCENDENTE.reversed();

    // Comparadores para número de expediente
    public static final Comparator<Documento> EXPEDIENTE_ASCENDENTE = Comparator.comparing(Documento::getNumeroExpediente);
    public static final Comparator<Documento> EXPEDIENTE_DESCENDENTE = EXPEDIENTE_ASCENDENTE.reversed();

    // Comparador para tipo
    public static final Comparator<Documento> POR_TIPO = Comparator.comparing(Documento::getTipo);

}
