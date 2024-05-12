package com.biog.unihiveandroid.model;

public enum CitiesName {
    AGADIR("Agadir"),
    AL_HOCEIMA("Al Hoceima"),
    RABAT("Rabat"),
    BEN_GUERIR("Ben Guerir"),
    BENI_MELLAL("Beni Mellal"),
    BERRECHID("Berrechid"),
    CASABLANCA("Casablanca"),
    EL_JADIDA("El Jadida"),
    ERRACHIDIA("Errachidia"),
    FES("Fes"),
    KENITRA("Kenitra"),
    KHOURIBGA("Khouribga"),
    MARRAKECH("Marrakech"),
    MEKNES("Meknes"),
    MOHAMMEDIA("Mohammedia"),
    OUJDA("Oujda"),
    SAFI("Safi"),
    SETTAT("Settat"),
    TANGER("Tanger"),
    TETOUAN("Tetouan");

    private final String cityName;
    CitiesName(String cityName) {
        this.cityName = cityName;
    }
    public String getCityName() {
        return cityName;
    }
}
