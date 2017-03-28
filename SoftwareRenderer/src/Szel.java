
public class Szel {

    private float X;
    private float xLepes;
    private int yKezdo;
    private int yVeg;
    private float xTexturaKoordinata;
    private float xTexturaKoordinataLepes;
    private float yTexturaKoordinata;
    private float yTexturaKoordinataKepes;
    private float zOsztvaEgy;
    private float zOsztvaEgyLepes;
    private float melyseg;
    private float melysegLepes;

    public float getX() {
        return X;
    }

    public int getYKezdo() {
        return yKezdo;
    }

    public int getYVeg() {
        return yVeg;
    }

    public float getXTextKoordinata() {
        return xTexturaKoordinata;
    }

    public float getXTextKoordinataLep() {
        return yTexturaKoordinata;
    }

    public float getZOsztvaEgy() {
        return zOsztvaEgy;
    }

    public float getMelyseg() {
        return melyseg;
    }

    /**
     * Inicializálja a megfelelő szélet a megadott adatokból, valamint az ahhoz,
     * illű textúravektort
     *
     * @param gradiens
     * @param YMinCsucs
     * @param YMaxCsucs
     * @param YMinCsucsIndex
     */
    public Szel(Gradiens gradiens, Csucs YMinCsucs, Csucs YMaxCsucs, int YMinCsucsIndex) {
        yKezdo = (int) Math.ceil(YMinCsucs.getY());
        yVeg = (int) Math.ceil(YMaxCsucs.getY());

        float yTav = YMaxCsucs.getY() - YMinCsucs.getY();
        float xTav = YMaxCsucs.getX() - YMinCsucs.getX();

        float yKezdoLepes = yKezdo - YMinCsucs.getY();
        xLepes = (float) xTav / (float) yTav;
        X = YMinCsucs.getX() + yKezdoLepes * xLepes;
        float xKezdoLepes = X - YMinCsucs.getX();

        xTexturaKoordinata = gradiens.getTexKoord_X(YMinCsucsIndex) + gradiens.get_Tex_XX_Lepes() * xKezdoLepes + gradiens.get_Tex_XY_Lepes() * yKezdoLepes;
        xTexturaKoordinataLepes = gradiens.get_Tex_XY_Lepes() + gradiens.get_Tex_XX_Lepes() * xLepes;

        yTexturaKoordinata = gradiens.getTextKoord_Y(YMinCsucsIndex) + gradiens.get_Tex_YX_Lepes() * xKezdoLepes + gradiens.get_Tex_YY_Lepes() * yKezdoLepes;
        yTexturaKoordinataKepes = gradiens.get_Tex_YY_Lepes() + gradiens.get_Tex_YX_Lepes() * xLepes;

        zOsztvaEgy = gradiens.getEgyPer_Z(YMinCsucsIndex) + gradiens.get_EgyPer_ZX_Lepes() * xKezdoLepes + gradiens.get_EgyPer_YZ_Lepes() * yKezdoLepes;
        zOsztvaEgyLepes = gradiens.get_EgyPer_YZ_Lepes() + gradiens.get_EgyPer_ZX_Lepes() * xLepes;

        melyseg = gradiens.getMelyseg(YMinCsucsIndex) + gradiens.get_X_MelysegLepes() * xKezdoLepes + gradiens.get_Y_MelysegLepes() * yKezdoLepes;
        melysegLepes = gradiens.get_Y_MelysegLepes() + gradiens.get_X_MelysegLepes() * xLepes;
    }

    public void lep() {
        X += xLepes;
        xTexturaKoordinata += xTexturaKoordinataLepes;
        yTexturaKoordinata += yTexturaKoordinataKepes;
        zOsztvaEgy += zOsztvaEgyLepes;
        melyseg += melysegLepes;
    }
}
