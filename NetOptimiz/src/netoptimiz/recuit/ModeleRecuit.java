package netoptimiz.recuit;

import netoptimiz.graphe.Graphe;

public abstract class ModeleRecuit {

    private double temperature;
    private int decroissanceTemp;
    private int iterationsInternes;
    private int nombrePalliers;

    public ModeleRecuit () {

    }

    public int getDecroissanceTemp () {
        return decroissanceTemp;
    }

    public void setDecroissanceTemp (int val) {
        this.decroissanceTemp = val;
    }

    public int getIterationsInternes () {
        return iterationsInternes;
    }

    public void setIterationsInternes (int val) {
        this.iterationsInternes = val;
    }

    public double getTemperature () {
        return temperature;
    }

    public void setTemperature (double val) {
        this.temperature = val;
    }

    public int getNombrePalliers () {
        return nombrePalliers;
    }

    public void setNombrePalliers (int val) {
        this.nombrePalliers = val;
    }

    public void tempInitiale () {
    }

    public abstract double resoudre ();

    public abstract void faireMvt (Graphe g);

    public abstract boolean accepterMVT (Graphe g);

    public abstract double calculerCout (Graphe g);

    public abstract double deltaCout ();

    public abstract void refuserMvt ();

}

