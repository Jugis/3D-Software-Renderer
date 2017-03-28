public class Csucs
{
	private Vector4f poziciosVektor;
	private Vector4f texturaVektor;

	
	public float getX() { return poziciosVektor.GetX(); }
	
	public float getY() { return poziciosVektor.GetY(); }

	public Vector4f getPozicio() { return poziciosVektor; }
	public Vector4f getTextura() { return texturaVektor; }

	public Csucs(Vector4f poz, Vector4f textVekt)
	{
		poziciosVektor = poz;
		texturaVektor = textVekt;
	}

	public Csucs transzformator(Matrix4f transzformator)
	{
		return new Csucs(transzformator.transzformator(poziciosVektor), texturaVektor);
	}

	public Csucs nezopontOsztas()
	{
		return new Csucs(new Vector4f(poziciosVektor.GetX()/poziciosVektor.GetW(), poziciosVektor.GetY()/poziciosVektor.GetW(),
                                poziciosVektor.GetZ()/poziciosVektor.GetW(), poziciosVektor.GetW()),    texturaVektor);
	}

	public float getHaromszogTerulet(Csucs b, Csucs c)
	{
		float x1 = b.getX() - poziciosVektor.GetX();
		float y1 = b.getY() - poziciosVektor.GetY();

		float x2 = c.getX() - poziciosVektor.GetX();
		float y2 = c.getY() - poziciosVektor.GetY();

		return (x1 * y2 - x2 * y1);
	}
}
