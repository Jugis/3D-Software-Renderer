import java.util.ArrayList;
import java.util.List;

public class IndexedModell
{
	private List<Vector4f> poziciok;
	private List<Vector4f> texturaKoordinatak;
	private List<Vector4f> normalErtekek;
	private List<Vector4f> tangensek;
	private List<Integer>  indexek;

	public IndexedModell()
	{
		poziciok = new ArrayList<Vector4f>();
		texturaKoordinatak = new ArrayList<Vector4f>();
		normalErtekek = new ArrayList<Vector4f>();
		tangensek = new ArrayList<Vector4f>();
		indexek = new ArrayList<Integer>();
	}

	public void normalSzamitas()
	{
		for(int i = 0; i < indexek.size(); i += 3)
		{
			int i0 = indexek.get(i);
			int i1 = indexek.get(i + 1);
			int i2 = indexek.get(i + 2);

			Vector4f v1 = poziciok.get(i1).kivon(poziciok.get(i0));
			Vector4f v2 = poziciok.get(i2).kivon(poziciok.get(i0));

			Vector4f normal = v1.vektorialisSzorzat(v2).normalizacio();

			normalErtekek.set(i0, normalErtekek.get(i0).osszead(normal));
			normalErtekek.set(i1, normalErtekek.get(i1).osszead(normal));
			normalErtekek.set(i2, normalErtekek.get(i2).osszead(normal));
		}

		for(int i = 0; i < normalErtekek.size(); i++)
                {
			normalErtekek.set(i, normalErtekek.get(i).normalizacio());
                }
	}

	public void tangensSzamitas()
	{
		for(int i = 0; i < indexek.size(); i += 3)
		{
			int i0 = indexek.get(i);
			int i1 = indexek.get(i + 1);
			int i2 = indexek.get(i + 2);

			Vector4f csucs1 = poziciok.get(i1).kivon(poziciok.get(i0));
			Vector4f csucs2 = poziciok.get(i2).kivon(poziciok.get(i0));

			float deltaU1 = texturaKoordinatak.get(i1).GetX() - texturaKoordinatak.get(i0).GetX();
			float deltaV1 = texturaKoordinatak.get(i1).GetY() - texturaKoordinatak.get(i0).GetY();
			float deltaU2 = texturaKoordinatak.get(i2).GetX() - texturaKoordinatak.get(i0).GetX();
			float deltaV2 = texturaKoordinatak.get(i2).GetY() - texturaKoordinatak.get(i0).GetY();

			float osztoErtek = (deltaU1*deltaV2 - deltaU2*deltaV1);
			float f = osztoErtek == 0 ? 0.0f : 1.0f/osztoErtek;

			Vector4f tangent = new Vector4f(
					f * (deltaV2 * csucs1.GetX() - deltaV1 * csucs2.GetX()),
					f * (deltaV2 * csucs1.GetY() - deltaV1 * csucs2.GetY()),
					f * (deltaV2 * csucs1.GetZ() - deltaV1 * csucs2.GetZ()),
					0);
			
			tangensek.set(i0, tangensek.get(i0).osszead(tangent));
			tangensek.set(i1, tangensek.get(i1).osszead(tangent));
			tangensek.set(i2, tangensek.get(i2).osszead(tangent));
		}

		for(int i = 0; i < tangensek.size(); i++)
			tangensek.set(i, tangensek.get(i).normalizacio());
	}

	public List<Vector4f> getPoziciok() { return poziciok; }
	public List<Vector4f> getTexturaKoordinatak() { return texturaKoordinatak; }
	public List<Vector4f> getNormalErtekek() { return normalErtekek; }
	public List<Vector4f> getTangensek() { return tangensek; }
	public List<Integer>  getIndexek() { return indexek; }
}
