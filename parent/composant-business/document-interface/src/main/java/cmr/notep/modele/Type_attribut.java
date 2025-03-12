package cmr.notep.modele;

public enum Type_attribut {

    Number("Number"),
    Text("Text"),
    Checkbox("Checkbox"),
    Radio("Radio"),
    Date("Date"),
    Textarea("Textarea"),
    Email("Email"),
    Url("Url");


    private final String type_attribut;

    Type_attribut(String Type) {
        this.type_attribut = Type;
    }

    public String getType() {
        return this.type_attribut;
    }

}
