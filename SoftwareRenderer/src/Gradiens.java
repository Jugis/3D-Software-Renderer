public class Gradiens
{
	private float[] xTextKoord;
	private float[] yTextKoord;
	private float[] zOsztvaEgy;
	private float[] melyseg;

	private float XX_TextLepes;
	private float XY_TextLepes;
	private float YX_TextLepes;
	private float YY_TextLepes;
	private float EgyPer_ZX_Lepes;
	private float EgyPer_ZY_Lepes;
	private float X_MelysegLepes;
	private float Y_MelysegLepes;

	public float getTexKoord_X(int loc) { return xTextKoord[loc]; }
	public float getTextKoord_Y(int loc) { return yTextKoord[loc]; }
	public float getEgyPer_Z(int loc) { return zOsztvaEgy[loc]; }
	public float getMelyseg(int loc) { return melyseg[loc]; }

	public float get_Tex_XX_Lepes() { return XX_TextLepes; }
	public float get_Tex_XY_Lepes() { return XY_TextLepes; }
	public float get_Tex_YX_Lepes() { return YX_TextLepes; }
	public float get_Tex_YY_Lepes() { return YY_TextLepes; }
	public float get_EgyPer_ZX_Lepes() { return EgyPer_ZX_Lepes; }
	public float get_EgyPer_YZ_Lepes() { return EgyPer_ZY_Lepes; }
	public float get_X_MelysegLepes() { return X_MelysegLepes; }
	public float get_Y_MelysegLepes() { return Y_MelysegLepes; }

	private float XLepesSzamlalas(float[] ertekTomb, Csucs Y_MinCsucs, Csucs Y_KozepCsucs, Csucs Y_MaxCsucs, float X_egyPer){
            return  (((ertekTomb[1] - ertekTomb[2]) *(Y_MinCsucs.getY() - Y_MaxCsucs.getY())) - ((ertekTomb[0] - ertekTomb[2]) *(Y_KozepCsucs.getY() - Y_MaxCsucs.getY()))) * X_egyPer;
	}

	private float CalcYStep(float[] ertekTomb, Csucs Y_MinCsucs, Csucs Y_KozepCsics, Csucs Y_MaxCsucs, float Y_egyPer){
		return (((ertekTomb[1] - ertekTomb[2]) *(Y_MinCsucs.getX() - Y_MaxCsucs.getX())) - ((ertekTomb[0] - ertekTomb[2]) *(Y_KozepCsics.getX() - Y_MaxCsucs.getX()))) * Y_egyPer;
	}

	public Gradiens(Csucs YMinCsucs, Csucs YKozepCsucs, Csucs YMaxCsucs)
	{
		float egyPerX = 1.0f / (((YKozepCsucs.getX() - YMaxCsucs.getX()) * (YMinCsucs.getY() - YMaxCsucs.getY())) - ((YMinCsucs.getX() - YMaxCsucs.getX()) * (YKozepCsucs.getY() - YMaxCsucs.getY())));

		float egyPerY = -egyPerX;

		zOsztvaEgy = new float[3];
		xTextKoord = new float[3];
		yTextKoord = new float[3];
		melyseg = new float[3];

		melyseg[0] = YMinCsucs.getPozicio().GetZ();
		melyseg[1] = YKozepCsucs.getPozicio().GetZ();
		melyseg[2] = YMaxCsucs.getPozicio().GetZ();

                
                //w az a szempont Z érték
                //a Z komponens a "valami más pixel mögött levő" pixelekhez
		zOsztvaEgy[0] = 1.0f/YMinCsucs.getPozicio().GetW();
		zOsztvaEgy[1] = 1.0f/YKozepCsucs.getPozicio().GetW();
		zOsztvaEgy[2] = 1.0f/YMaxCsucs.getPozicio().GetW();

		xTextKoord[0] = YMinCsucs.getTextura().GetX() * zOsztvaEgy[0];
		xTextKoord[1] = YKozepCsucs.getTextura().GetX() * zOsztvaEgy[1];
		xTextKoord[2] = YMaxCsucs.getTextura().GetX() * zOsztvaEgy[2];

		yTextKoord[0] = YMinCsucs.getTextura().GetY() * zOsztvaEgy[0];
		yTextKoord[1] = YKozepCsucs.getTextura().GetY() * zOsztvaEgy[1];
		yTextKoord[2] = YMaxCsucs.getTextura().GetY() * zOsztvaEgy[2];

		XX_TextLepes = XLepesSzamlalas(xTextKoord, YMinCsucs, YKozepCsucs, YMaxCsucs, egyPerX);
		XY_TextLepes = CalcYStep(xTextKoord, YMinCsucs, YKozepCsucs, YMaxCsucs, egyPerY);
		YX_TextLepes = XLepesSzamlalas(yTextKoord, YMinCsucs, YKozepCsucs, YMaxCsucs, egyPerX);
		YY_TextLepes = CalcYStep(yTextKoord, YMinCsucs, YKozepCsucs, YMaxCsucs, egyPerY);
		EgyPer_ZX_Lepes = XLepesSzamlalas(zOsztvaEgy, YMinCsucs, YKozepCsucs, YMaxCsucs, egyPerX);
		EgyPer_ZY_Lepes = CalcYStep(zOsztvaEgy, YMinCsucs, YKozepCsucs, YMaxCsucs, egyPerY);
		X_MelysegLepes = XLepesSzamlalas(melyseg, YMinCsucs, YKozepCsucs, YMaxCsucs, egyPerX);
		Y_MelysegLepes = CalcYStep(melyseg, YMinCsucs, YKozepCsucs, YMaxCsucs, egyPerY);
	}
}
