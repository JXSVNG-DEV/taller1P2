/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo.interfaces;

import java.time.LocalDateTime;

public interface Auditable {
    LocalDateTime obtenerFechaCreacion();
    LocalDateTime obtenerUltimaModificacion();
    String obtenerUsuarioModificacion();
    void registrarModificacion(String usuario);
}
