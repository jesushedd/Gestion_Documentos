package controladores.cli;

import modelo.Documento;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


public class FormatDocumento {

    static String format(List<Documento> documentos){
        return documentos.stream().map(FormatDocumento::format).collect(Collectors.joining("\n"));
    }

    static String format(Documento documento){
        String formatString = "\t\t%1$d\t\t%2$s\t\t%3$td-%3$tm-%3$tY\t\t%4$s\t\t%5$s\t\t%6$s";
        int id = documento.getId();
        Integer numExpediente = documento.getNumeroExpediente();
        String expediente = numExpediente != null ? numExpediente.toString() : "   ";
        LocalDate fecha = documento.getFecha();
        String tipo = documento.getTipo() != null ? documento.getTipo() : "   ";
        String remitente = documento.getRemitente() != null ? documento.getRemitente() : "  ";
        String estado = documento.getEstado() != null ? documento.getEstado().toString(): "  ";
        return String.format(formatString, id, expediente, fecha, tipo, remitente, estado);
    }


}
