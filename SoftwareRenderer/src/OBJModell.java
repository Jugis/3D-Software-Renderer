import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class OBJModell
{
	private class OBJIndex
	{
		private int csucsIndex;
		private int texturaKoordIndex;
		private int normalizaltIndex;

		public int getCsucsIndex()   { return csucsIndex; }
		public int getTextKoordIndex() { return texturaKoordIndex; }
		public int getIndex()   { return normalizaltIndex; }

		public void setCsucsIndex(int val)   { csucsIndex = val; }
		public void setTextIndex(int val) { texturaKoordIndex = val; }
		public void setIndex(int val)   { normalizaltIndex = val; }

		@Override
		public boolean equals(Object obj)
		{
			OBJIndex index = (OBJIndex)obj;

			return csucsIndex == index.csucsIndex   && texturaKoordIndex == index.texturaKoordIndex    && normalizaltIndex == index.normalizaltIndex;
		}

		@Override
		public int hashCode()
		{
			final int ALAP = 17;
			final int SZORZO = 31;

			int eredmeny = ALAP;

			eredmeny = SZORZO * eredmeny + csucsIndex;
			eredmeny = SZORZO * eredmeny + texturaKoordIndex;
			eredmeny = SZORZO * eredmeny + normalizaltIndex;

			return eredmeny;
		}
	}

	private List<Vector4f> poziciok;
	private List<Vector4f> textKoordinatak;
;	private List<Vector4f> normalErtekek;
	private List<OBJIndex> Indexek;
	private boolean        vanTextKoordinataja;
	private boolean        vanNormalKoordinataj;

	private static String[] UresStringKiurito(String[] adat)
	{
		List<String> eredmeny = new ArrayList<String>();
		
		for(int i = 0; i < adat.length; i++)
			if(!adat[i].equals(""))
				eredmeny.add(adat[i]);
		
		String[] ered = new String[eredmeny.size()];
		eredmeny.toArray(ered);
		
		return ered;
	}

	public OBJModell(String fileName) throws IOException
	{
		poziciok = new ArrayList<Vector4f>();
		textKoordinatak = new ArrayList<Vector4f>();
		normalErtekek = new ArrayList<Vector4f>();
		Indexek = new ArrayList<OBJIndex>();
		vanTextKoordinataja = false;
		vanNormalKoordinataj = false;

		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String sor;

		while((sor = reader.readLine()) != null)
		{
			String[] tokenek = sor.split(" ");
			tokenek = UresStringKiurito(tokenek);

			if(tokenek.length == 0 || tokenek[0].equals("#"))
				continue;
			else if(tokenek[0].equals("v"))
			{
				poziciok.add(new Vector4f(Float.valueOf(tokenek[1]),    Float.valueOf(tokenek[2]),  Float.valueOf(tokenek[3]),1));
			}
			else if(tokenek[0].equals("vt"))
			{   textKoordinatak.add(new Vector4f(Float.valueOf(tokenek[1]), 1.0f - Float.valueOf(tokenek[2]),0,0));
			}
			else if(tokenek[0].equals("vn"))
			{   normalErtekek.add(new Vector4f(Float.valueOf(tokenek[1]),   Float.valueOf(tokenek[2]),  Float.valueOf(tokenek[3]),0));
			}
			else if(tokenek[0].equals("f"))
			{
				for(int i = 0; i < tokenek.length - 3; i++)
				{   Indexek.add(OBJIndexParser(tokenek[1])); Indexek.add(OBJIndexParser(tokenek[2 + i])); Indexek.add(OBJIndexParser(tokenek[3 + i]));
				}
			}
		}

		
		reader.close();
	}

	public IndexedModell indexedModelleParser()
	{
		IndexedModell eredmeny = new IndexedModell();
		IndexedModell normalModell = new IndexedModell();
		Map<OBJIndex, Integer> eredmenyIndexMap = new HashMap<OBJIndex, Integer>();
		Map<Integer, Integer> normalIndexMap = new HashMap<Integer, Integer>();
		Map<Integer, Integer> indexMap = new HashMap<Integer, Integer>();

		for(int i = 0; i < Indexek.size(); i++)
		{
			OBJIndex jelenlegiIndex = Indexek.get(i);

			Vector4f jelenlegiPozicio = poziciok.get(jelenlegiIndex.getCsucsIndex());
			Vector4f jelenlegiTextKoordinata;
			Vector4f jelenlegiNormal;

			if(vanTextKoordinataja){
				jelenlegiTextKoordinata = textKoordinatak.get(jelenlegiIndex.getTextKoordIndex());
                        }
                        else{
				jelenlegiTextKoordinata = new Vector4f(0,0,0,0);
                        }

			if(vanNormalKoordinataj){
				jelenlegiNormal = normalErtekek.get(jelenlegiIndex.getIndex());
                        }
                        else{
				jelenlegiNormal = new Vector4f(0,0,0,0);
                        }

			Integer modelCsucsIndex = eredmenyIndexMap.get(jelenlegiIndex);

			if(modelCsucsIndex == null)
			{
				modelCsucsIndex = eredmeny.getPoziciok().size();
				eredmenyIndexMap.put(jelenlegiIndex, modelCsucsIndex);

				eredmeny.getPoziciok().add(jelenlegiPozicio);
				eredmeny.getTexturaKoordinatak().add(jelenlegiTextKoordinata);
				if(vanNormalKoordinataj)
					eredmeny.getNormalErtekek().add(jelenlegiNormal);
			}

			Integer normalModelIndex = normalIndexMap.get(jelenlegiIndex.getCsucsIndex());

			if(normalModelIndex == null)
			{
				normalModelIndex = normalModell.getPoziciok().size();
				normalIndexMap.put(jelenlegiIndex.getCsucsIndex(), normalModelIndex);
				normalModell.getPoziciok().add(jelenlegiPozicio);
				normalModell.getTexturaKoordinatak().add(jelenlegiTextKoordinata);
				normalModell.getNormalErtekek().add(jelenlegiNormal);
				normalModell.getTangensek().add(new Vector4f(0,0,0,0));
			}

			eredmeny.getIndexek().add(modelCsucsIndex);
			normalModell.getIndexek().add(normalModelIndex);
			indexMap.put(modelCsucsIndex, normalModelIndex);
		}

		if(!vanNormalKoordinataj)
		{
			normalModell.normalSzamitas();

			for(int i = 0; i < eredmeny.getPoziciok().size(); i++){
                            
				eredmeny.getNormalErtekek().add(normalModell.getNormalErtekek().get(indexMap.get(i)));
                        }
		}

		normalModell.tangensSzamitas();

		for(int i = 0; i < eredmeny.getPoziciok().size(); i++)
			eredmeny.getTangensek().add(normalModell.getTangensek().get(indexMap.get(i)));

		return eredmeny;
	}

	private OBJIndex OBJIndexParser(String token)
	{
		String[] ertekek = token.split("/");

		OBJIndex eredmeny = new OBJIndex();
		eredmeny.setCsucsIndex(Integer.parseInt(ertekek[0]) - 1);

		if(ertekek.length > 1)
		{
			if(!ertekek[1].isEmpty())
			{
				vanTextKoordinataja = true;
				eredmeny.setTextIndex(Integer.parseInt(ertekek[1]) - 1);
			}

			if(ertekek.length > 2)
			{
				vanNormalKoordinataj = true;
				eredmeny.setIndex(Integer.parseInt(ertekek[2]) - 1);
			}
		}

		return eredmeny;
	}
}
