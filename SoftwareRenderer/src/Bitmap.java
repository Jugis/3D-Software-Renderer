import java.util.Arrays;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bitmap
{
	/** Az egyes kép pixelekben mért szélessége */
	private final int  szelesseg;
	/** Magasság */
	private final int  magassag;
	/** Minden pixel egy tömbben */
	private final byte[] komponensek;

	/** getter */
	public int getSzelesseg() { return szelesseg; }
	/** getter */
	public int getMagassag() { return magassag; }
        /** Egy komoponens helyét megadja a keresett indexnél a komponensek tömbben lényegében egy: ".get(i)"*/
	public byte keresettKomponens(int index) { return komponensek[index]; }

	/**
	 * A bitmap inicializációja
	 *
	 * @param szel Szélesség
	 * @param mag magsság
	 */
	public Bitmap(int szel, int mag)
	{
		szelesseg = szel;
		magassag = mag;
		komponensek = new byte[szelesseg * magassag * 4];
	}

	public Bitmap(String fileName) throws IOException
	{
		int lok_szelesseg = 0;
		int lok_magassag = 0;
		byte[] lok_komponensek = null;

		BufferedImage image = ImageIO.read(new File(fileName));

		lok_szelesseg = image.getWidth();
		lok_magassag = image.getHeight();

		int[] kep_pixelek = new int[lok_szelesseg * lok_magassag];
		image.getRGB(0, 0, lok_szelesseg, lok_magassag, kep_pixelek, 0, lok_szelesseg);
		lok_komponensek = new byte[lok_szelesseg * lok_magassag * 4];

		for(int i = 0; i < lok_szelesseg * lok_magassag; i++)
		{
			int pixel = kep_pixelek[i];
                        //Itt úgynevezett bitshiftelés történik, olyasmi mint ami assemblyben is van :)
			lok_komponensek[i * 4]     = (byte)((pixel >> 24) & 0xFF); // A
			lok_komponensek[i * 4 + 1] = (byte)((pixel      ) & 0xFF); // B
			lok_komponensek[i * 4 + 2] = (byte)((pixel >> 8 ) & 0xFF); // G
			lok_komponensek[i * 4 + 3] = (byte)((pixel >> 16) & 0xFF); // R
		}

		szelesseg = lok_szelesseg;
		magassag = lok_magassag;
		komponensek = lok_komponensek;
	}

	/**
	 * Szürkíti a bitmapot a megadott árnyalatával a fehér-szürke skálának
	 *
	 * @param arnyalat 0 fekete - 255 fehér
	 */
	public void kepernyotTisztit(byte arnyalat)
	{
		Arrays.fill(komponensek, arnyalat);
	}

	/**
	 * x.y koordinátánál lévő pixelt a megadott színűre színezi
	 *
	 * @param x Pixel x koordináta
	 * @param y Pixel y koordináta
	 * @param a Alpha komponens
	 * @param b Blue komponens
	 * @param g Green komponens
	 * @param r Red komponens
	 */
	public void pixeltRajzol(int x, int y, byte a, byte b, byte g, byte r)
	{
		int index = (x + y * szelesseg) * 4;
		komponensek[index    ] = a;
		komponensek[index + 1] = b;
		komponensek[index + 2] = g;
		komponensek[index + 3] = r;
	}

        /**Egy megadott pixel koordinátáit másolja át egy megadott célpixel koordinátáiba 
         * 
         * @param celX
         * @param celY
         * @param forrX
         * @param forrY
         * @param forras A bitmap ahonnan keressük a keresett pixelt
         */
	public void pixeltMasol(int celX, int celY, int forrX, int forrY, Bitmap forras)
	{
		int celIndex = (celX + celY * szelesseg) * 4;
		int forrasIndex = (forrX + forrY * forras.getSzelesseg()) * 4;
		komponensek[celIndex    ] = forras.keresettKomponens(forrasIndex);
		komponensek[celIndex + 1] = forras.keresettKomponens(forrasIndex + 1);
		komponensek[celIndex + 2] = forras.keresettKomponens(forrasIndex + 2);
		komponensek[celIndex + 3] = forras.keresettKomponens(forrasIndex + 3);
	}

	/**
	 * A bitampot belemásolja egy tömbbe
	 *
	 * @param cel A tömb amibe másolunk
	 */
	public void CopyToByteArray(byte[] cel)
	{
		for(int i = 0; i < szelesseg * magassag; i++)
		{
			cel[i * 3    ] = komponensek[i * 4 + 1];
			cel[i * 3 + 1] = komponensek[i * 4 + 2];
			cel[i * 3 + 2] = komponensek[i * 4 + 3];
		}
	}
}
