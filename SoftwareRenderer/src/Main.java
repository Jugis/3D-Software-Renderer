import java.io.IOException;

public class Main
{
    public static final String texturaEleres = "./res/levelek2.jpg";
    private static final String modellEleres = "./res/monkey2.obj";
	public static void main(String[] args) throws IOException
	{
		Ablak ablak = new Ablak(800, 600, "Forgó majomfej");
		RenderLogika logika = ablak.getRendszerLogika();

		

		Csucs YMinCsucs = new Csucs(new Vector4f(-1, -1, 0, 1), new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
		Csucs YKozepCsucs = new Csucs(new Vector4f(0, 1, 0, 1), new Vector4f(0.5f, 1.0f, 0.0f, 0.0f));
		Csucs YMaxCsucs = new Csucs(new Vector4f(1, -1, 0, 1), new Vector4f(1.0f, 0.0f, 0.0f, 0.0f));

                Bitmap textura = new Bitmap(texturaEleres);
		Alakzat alak = new Alakzat(modellEleres);
                
		Matrix4f perspektivaProjekcio = new Matrix4f().perspektivaInicializalas((float)Math.toRadians(800.0f), (float)logika.getSzelesseg()/(float)logika.getMagassag(), 0.1f, 1000.0f);
	 
		float fordulat = 0.0f;
		long elozoido = System.nanoTime();
		while(true)
		{
			long rendszerIdo = System.nanoTime();
			float IdoKulonbseg = (float)((rendszerIdo - elozoido)/1000000000.0);
			elozoido = rendszerIdo;


			fordulat += IdoKulonbseg;
			Matrix4f mozgatas = new Matrix4f().mozgatas(0.0f, 0.0f, 3.0f);
			Matrix4f forgatas = new Matrix4f().forgatas(fordulat, 0.0f, fordulat);
			Matrix4f transzformacio = perspektivaProjekcio.szorzas(mozgatas.szorzas(forgatas));

			logika.kepernyotTisztit((byte)0x00);
			logika.bufferUrites();
			logika.alakzatRajzolas(alak, transzformacio, textura);

			ablak.kepernyotRajzol();
		}
	}
}
