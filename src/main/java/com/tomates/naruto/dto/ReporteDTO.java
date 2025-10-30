package com.tomates.naruto.dto;

import java.util.List;

public class ReporteDTO {

    private List<AldeaDTO> aldeas;
    private List<NinjaDTO> ninjas;
    private List<MisionDTO> misiones;

    public ReporteDTO(List<AldeaDTO> aldeas, List<NinjaDTO> ninjas, List<MisionDTO> misiones) {
        this.aldeas = aldeas;
        this.ninjas = ninjas;
        this.misiones = misiones;
    }

    public List<AldeaDTO> getAldeas() { return aldeas; }
    public List<NinjaDTO> getNinjas() { return ninjas; }
    public List<MisionDTO> getMisiones() { return misiones; }
}
