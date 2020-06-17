package com.et.gestionreclamo.entidad;

import java.io.Serializable;

public class Cierre implements Serializable {
    private Integer id;
    private Integer id_raiz;
    private String codigo;
    private String nombre;
    private Integer origen;
    private Integer causa;
    private Integer causaInterna;
    private String motivo;
    private String observacion;
    private String obsInterna;
    private Integer derivar_id;
    private String derivar_dsc;
    private Integer cantVolt;
    private boolean procedente;

    public Cierre(){
    }

    public Cierre(Integer id, Integer id_raiz, String codigo, String nombre, Integer origen, Integer causa, Integer causaInterna, String motivo, String observacion, String obsInterna, Integer derivar_id, String derivar_dsc, Integer cantVolt, boolean procedente) {
        this.id = id;
        this.id_raiz = id_raiz;
        this.codigo = codigo;
        this.nombre = nombre;
        this.origen = origen;
        this.causa = causa;
        this.causaInterna = causaInterna;
        this.motivo = motivo;
        this.observacion = observacion;
        this.obsInterna = obsInterna;
        this.derivar_id = derivar_id;
        this.derivar_dsc = derivar_dsc;
        this.cantVolt = cantVolt;
        this.procedente = procedente;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setProcedente(boolean procedente) {
        this.procedente = procedente;
    }

    public Integer getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isProcedente() {
        return procedente;
    }

    public Integer getId_raiz() {
        return id_raiz;
    }

    public void setId_raiz(Integer id_raiz) {
        this.id_raiz = id_raiz;
    }

    public Integer getCantVolt() {
        return cantVolt;
    }

    public void setCantVolt(Integer cantVolt) {
        this.cantVolt = cantVolt;
    }

    public Integer getOrigen() {
        return origen;
    }

    public void setOrigen(Integer origen) {
        this.origen = origen;
    }

    public Integer getCausa() {
        return causa;
    }

    public void setCausa(Integer causa) {
        this.causa = causa;
    }

    public Integer getCausaInterna() {
        return causaInterna;
    }

    public void setCausaInterna(Integer causaInterna) {
        this.causaInterna = causaInterna;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getObsInterna() {
        return obsInterna;
    }

    public void setObsInterna(String obsInterna) {
        this.obsInterna = obsInterna;
    }

    public Integer getDerivar_id() {
        return derivar_id;
    }

    public void setDerivar_id(Integer derivar_id) {
        this.derivar_id = derivar_id;
    }

    public String getDerivar_dsc() {
        return derivar_dsc;
    }

    public void setDerivar_dsc(String derivar_dsc) {
        this.derivar_dsc = derivar_dsc;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
