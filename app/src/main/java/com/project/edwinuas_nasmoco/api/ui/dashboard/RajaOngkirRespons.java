package com.project.edwinuas_nasmoco.api.ui.dashboard;


import java.util.List;

public class RajaOngkirRespons {
    private RajaOngkir rajaongkir;

    public RajaOngkir getRajaongkir() { return rajaongkir; }

    public class RajaOngkir {
        private List<Province> results;

        public List<Province> getResults() { return results; }
    }
}
