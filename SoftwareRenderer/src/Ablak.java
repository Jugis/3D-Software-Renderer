import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.image.DataBufferByte;
import javax.swing.JFrame;

public class Ablak extends Canvas
{
	private final JFrame         frame;
	private final RenderLogika  logika;
	private final BufferedImage  kep;
	private final byte[]         komponensek;
	private final BufferStrategy mutatottKep;
	private final Graphics       grafika;

     
        
	public RenderLogika getRendszerLogika() { return logika; }

	/**Inicializáljuk az ablakaot, beállítjuk a megfelelő értékeket.
         * 
         * @param szelesseg a frame szélessége
         * @param magassag  a frame magassága
         * @param cim  a frame címe
         */
	public Ablak(int szelesseg, int magassag, String cim)
	{
		//Méretek beállítása
		Dimension meret = new Dimension(szelesseg, magassag);
		setPreferredSize(meret);
                
		logika = new RenderLogika(szelesseg, magassag);
		kep = new BufferedImage(szelesseg, magassag, BufferedImage.TYPE_3BYTE_BGR);
		komponensek = 
			((DataBufferByte)kep.getRaster().getDataBuffer()).getData();

		logika.kepernyotTisztit((byte)0x80);
		logika.pixeltRajzol(100, 100, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xFF);
                
		frame = new JFrame();
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle(cim);
		frame.setSize(szelesseg, magassag);
		frame.setVisible(true);

		createBufferStrategy(1);
		mutatottKep = getBufferStrategy();
		grafika = mutatottKep.getDrawGraphics();
	}

	/**
	 * Ez a metódus mutatja végül az ablakot amiben a megfelelő kép van
	 */
	public void kepernyotRajzol()
	{       //A byte tömb a megfelelő pixelekkel bennük A Canvas-hez tartozó megfelelő metódusokat
		logika.CopyToByteArray(komponensek);
		grafika.drawImage(kep, 0, 0, 
			logika.getSzelesseg(), logika.getMagassag(), null);
		mutatottKep.show();
	}
}
