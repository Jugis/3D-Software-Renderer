import java.util.List;

public class RenderLogika extends Bitmap
{
	private float[] zBuffer;

	public RenderLogika(int szel, int mag)
	{
		super(szel, mag);
		zBuffer = new float[szel * mag];
	}

	public void bufferUrites()
	{
		for(int i = 0; i < zBuffer.length; i++)
		{
			zBuffer[i] = Float.MAX_VALUE;
		}
	}

	public void alakzatRajzolas(Alakzat alak, Matrix4f transzformMatrix, Bitmap textura)
	{
		for(int i = 0; i < alak.getIndexekMeret(); i += 3)
		{
			haromszogKitoltes(alak.getCsucs(alak.getIndex(i)).transzformator(transzformMatrix),alak.getCsucs(alak.getIndex(i + 1)).transzformator(transzformMatrix),
					alak.getCsucs(alak.getIndex(i + 2)).transzformator(transzformMatrix),textura);
		}
	}

	public void haromszogKitoltes(Csucs v1, Csucs v2, Csucs v3, Bitmap textura)
	{
		Matrix4f kepernyoInicializacio = new Matrix4f().kepernyoInicializalo(getSzelesseg()/2, getMagassag()/2);
		Csucs YMinCsucs = v1.transzformator(kepernyoInicializacio).nezopontOsztas();
		Csucs YKozepCsucs = v2.transzformator(kepernyoInicializacio).nezopontOsztas();
		Csucs YMaxCsucs = v3.transzformator(kepernyoInicializacio).nezopontOsztas();

                //Ez it azért van, mert azok a háromszögek, amelyek úgymond "nem a képernyő felé néznek, tehát a vektoroális szorzata
                //a két élnek nagyobbegyenlő nulla, akkor nem rajzolódik ki egészen egyszerűen, háromszögterületet vektoriális szorzattal számolunk
                //tehát a getHaromSzogTerulet teljesen megfelelő a célra, ennek az a célja, hogy azok a pixelek amelyek ugymond nem a  képernyő felé néznek
                //azok fedésben vannak egy másik háromszög által, tehát csak bezavarnának
                
		if(YMinCsucs.getHaromszogTerulet(YMaxCsucs, YKozepCsucs) >= 0)
		{
                    return;
		}
                //a megfelelő felosztás

		if(YMaxCsucs.getY() < YKozepCsucs.getY())
		{   Csucs temp = YMaxCsucs; YMaxCsucs = YKozepCsucs;    YKozepCsucs = temp;
		}

		if(YKozepCsucs.getY() < YMinCsucs.getY())
		{   Csucs temp = YKozepCsucs;   YKozepCsucs = YMinCsucs;    YMinCsucs = temp;
		}

		if(YMaxCsucs.getY() < YKozepCsucs.getY())
		{   Csucs temp = YMaxCsucs; YMaxCsucs = YKozepCsucs;    YKozepCsucs = temp;
		}

		ScanTriangle(YMinCsucs, YKozepCsucs, YMaxCsucs, YMinCsucs.getHaromszogTerulet(YMaxCsucs, YKozepCsucs) >= 0,textura);
	}

	private void ScanTriangle(Csucs minYVert, Csucs midYVert, 
			Csucs maxYVert, boolean oldal, Bitmap texture)
	{
		Gradiens gradiens = new Gradiens(minYVert, midYVert, maxYVert);
		Szel fentrolKozepre    = new Szel(gradiens, minYVert, maxYVert, 0);
		Szel fentrolLe    = new Szel(gradiens, minYVert, midYVert, 0);
		Szel kozeprolLe = new Szel(gradiens, midYVert, maxYVert, 1);

		szelRajzolas(fentrolKozepre, fentrolLe, oldal, texture);
		szelRajzolas(fentrolKozepre, kozeprolLe, oldal, texture);
	}

	private void szelRajzolas(Szel a, Szel b, boolean oldal, Bitmap textura)
	{
		Szel bal = a;
		Szel jobb = b;
		if(oldal == true)
		{   Szel temp = bal;
                    bal = jobb;
                    jobb = temp;
		}

		int yKez = b.getYKezdo();
		int yVe   = b.getYVeg();
		for(int j = yKez; j < yVe; j++)
		{   vonalatRajzol(bal, jobb, j, textura);
                    bal.lep();
                    jobb.lep();
		}
	}

	private void vonalatRajzol(Szel bal, Szel jobb, int j, Bitmap textur)
	{
		int xMin = (int)Math.ceil(bal.getX());
		int xMax = (int)Math.ceil(jobb.getX());
		float xEloreLepes = xMin - bal.getX();

		float xTavolsag = jobb.getX() - bal.getX();
		float textXLepes = (jobb.getXTextKoordinata() - bal.getXTextKoordinata())/xTavolsag;
		float textYXLepes = (jobb.getXTextKoordinataLep() - bal.getXTextKoordinataLep())/xTavolsag;
		float egyPerZLepes = (jobb.getZOsztvaEgy() - bal.getZOsztvaEgy())/xTavolsag;
		float melysegXLepes = (jobb.getMelyseg() - bal.getMelyseg())/xTavolsag;

		float texX = bal.getXTextKoordinata() + textXLepes * xEloreLepes;
		float texY = bal.getXTextKoordinataLep() + textYXLepes * xEloreLepes;
		float egyPerZ = bal.getZOsztvaEgy() + egyPerZLepes * xEloreLepes;
		float melyseg = bal.getMelyseg() + melysegXLepes * xEloreLepes;

		for(int i = xMin; i < xMax; i++)
		{
			int index = i + j * getSzelesseg();
			if(melyseg < zBuffer[index])
			{   zBuffer[index] = melyseg;   float z = 1.0f/egyPerZ; int srcX = (int)((texX * z) * (float)(textur.getSzelesseg() - 1) + 0.5f);
				int srcY = (int)((texY * z) * (float)(textur.getMagassag() - 1) + 0.5f);   pixeltMasol(i, j, srcX, srcY, textur);
			}

			egyPerZ += egyPerZLepes;
			texX += textXLepes;
			texY += textYXLepes;
			melyseg += melysegXLepes;
		}
	}
}
