package com.example.dawid.projectpum.DAL;

/**
 * Created by Dawid on 23.04.2018.
 */

public class CheckboxesEnums {
    public enum SportActivity{
        FOOTBALL("Piłka nożna"),
        VOLLEYBALL("Siatkówka"),
        JOGGING("Bieganie"),
        GYM("Siłownia"),
        BIKE("Rower"),
        WALKING("Spacer"),
        SWIMMING("Pływanie"),
        CLIMBING("Wspinaczka"),
        FITNESS("Fitness"),
        IRONING("Prasowanie");

        private String name;

        public String getName() {
            return name;
        }

        SportActivity(String name) {

            this.name = name;
        }
    }
    public enum SportValues{
        LARGE("duża"),
        MEDIUM("średnia"),
        SMALL("mała");

        private String name;
        public String getName() {
            return name;
        }
        SportValues(String name) {
            this.name = name;
        }

        public static SportValues fromString(String text) {
            for (SportValues b : SportValues.values()) {
                if (b.name.equalsIgnoreCase(text)) {
                    return b;
                }
            }
            return null;
        }
    }
    public enum SportTime{
        HOURS2("120"),
        LARGE("90"),
        MEDIUM("60"),
        SMALL("30"),
        VERYSMALL("15");

        private String name;

        public String getName() {
            return name;
        }

        SportTime(String name) {

            this.name = name;
        }

    }
    public enum Diet {
        NORMAL("Normalna"),
        VEGAN("Wegetariańska"),
        HEALTHY("Zdrowa"),
        DIABETIC("Cukrzycowa"),
        ALTERNATIVE("Medycyna alternatywna"),
        LOVE_SUGAR("Lubię słodycze");

        private String name;

        Diet(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
    public enum Liquids{
        COFFEE("Kawa"),
        TEA("Herbata"),
        ALCOHOL("Alkohol"),
        ENERGY_DRINKS("Energetyki"),
        YERBA_MATE("Yerba mate");

        Liquids(String name) {
            this.name = name;
        }
        private String name;
        public String getName(){return name;}
    }
    public enum Job{
        ACADEMY_STUDENT("Student"),
        MENTAL_WORKER("Praca intelektualna"),
        PHYSICIAN("Praca fizyczna"),
        STUDENT("Uczeń"),
        OWN_BUSINESS("Własny biznes");

        private String name;

        Job(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
    public enum LifeStyle{
        PIECE("Spokojny"),
        FAST("Zabiegany"),
        STRESSFULL("Stresujący"),
        SEDENTARY_MODE("Siedzący tryb"),
        FIT("Aktywny"),
        CITY("Miasto"),
        VILLAGE("Wieś");

        private String name;

        public String getName() {
            return name;
        }

        LifeStyle(String name) {

            this.name = name;
        }
    }

}
