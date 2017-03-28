import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class Alakzat
{
	private List<Csucs>  csucsok;
	private List<Integer> indexek;

	public Csucs getCsucs(int i) { return csucsok.get(i); }
	public int getIndex(int i) { return indexek.get(i); }
	public int getIndexekMeret() { return indexek.size(); }

	public Alakzat(String fileName) throws IOException
	{
		IndexedModell modell = new OBJModell(fileName).indexedModelleParser();

		csucsok = new ArrayList<Csucs>();
		for(int i = 0; i < modell.getPoziciok().size(); i++)
		{
			csucsok.add(new Csucs(modell.getPoziciok().get(i),modell.getTexturaKoordinatak().get(i)));
		}

		indexek = modell.getIndexek();
	}
}
