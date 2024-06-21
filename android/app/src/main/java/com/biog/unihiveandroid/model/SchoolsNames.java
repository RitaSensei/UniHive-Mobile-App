package com.biog.unihiveandroid.model;

public enum SchoolsNames {
    ENSA_AGADIR("ENSA AGADIR"),
    EPA("EPA"),
    ENSA_AL_HOCEIMA("ENSA AL HOCEIMA"),
    ARTS_ET_METIERS_CAMPUS_DE_RABAT("ARTS ET METIERS CAMPUS DE RABAT"),
    EMINES("EMINES"),
    ENSA_BENI_MELLAL("ENSA BENI MELLAL"),
    FST_BENI_MELLAL("FST BENI MELLAL"),
    ENSA_BERRECHID("ENSA BERRECHID"),
    ERN("ERN"),
    EHTP("EHTP"),
    IGA("IGA"),
    ENSEM("ENSEM"),
    EMSI("EMSI"),
    ESTEM_CASABLANCA("ESTEM Casablanca"),
    ESITH("ESITH"),
    ENSAM_CASABLANCA("ENSAM Casablanca"),
    AIAC("AIAC"),
    EMG("EMG"),
    UIC("UIC"),
    ECC("ECC"),
    ESGB("ESGB"),
    ENSA_EL_JADIDA("ENSA EL JADIDA"),
    FST_ERRACHIDIA("FST Errachidia"),
    ENSA_FES("ENSA FES"),
    FSI_UPF("FSI - UPF"),
    INSA("INSA"),
    FST_FES("FST Fès"),
    ISGA_FES("ISGA Fès"),
    ENSA_KENITRA("ENSA KENITRA"),
    ENSC_KENITRA("ENSC KENITRA"),
    ENSA_KHOURIBGA("ENSA KHOURIBGA"),
    ERA("ERA"),
    ENSA_MARRAKECH("ENSA MARRAKECH"),
    EIIM_UPM("EIIM - UPM"),
    FST_MARRAKECH("FST Marrakech"),
    ISGA_MARRAKECH("ISGA Marrakech"),
    ENAM("ENAM"),
    ENSAM_MEKNES("ENSAM Méknes"),
    ENSET("ENSET"),
    FST_MOHAMMEDIA("FST Mohammedia"),
    ENSA_OUJDA("ENSA OUJDA"),
    EMI("EMI"),
    INSEA("INSEA"),
    INPT("INPT"),
    IAV("IAV"),
    ENSMR("ENSMR"),
    ENSIAS("ENSIAS"),
    ISGA_RABAT("ISGA Rabat"),
    UIR("UIR"),
    ESI("ESI"),
    ENSAM_RABAT("ENSAM Rabat"),
    ENSA_SAFI("ENSA SAFI"),
    ENFI("ENFI"),
    FST_SETTAT("FST Settat"),
    ENSA_TANGER("ENSA TANGER"),
    FST_TANGER("FST Tanger"),
    ENSA_TETOUAN("ENSA TETOUAN");

    private final String schoolName;

    SchoolsNames(String schoolName) {
        this.schoolName = schoolName;
    }
    public String getSchoolName() {
        return schoolName;
    }
}
