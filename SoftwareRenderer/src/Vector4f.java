public class Vector4f
{
	private final float x;
	private final float y;
	private final float z;
	private final float w;

	public Vector4f(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public float hossz()
	{
		return (float)Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public float max()
	{
		return Math.max(Math.max(x, y), Math.max(z, w));
	}

	public float skalarSzorzat(Vector4f r)
	{
		return x * r.GetX() + y * r.GetY() + z * r.GetZ() + w * r.GetW();
	}

	public Vector4f vektorialisSzorzat(Vector4f r)
	{
		float x_ = y * r.GetZ() - z * r.GetY();
		float y_ = z * r.GetX() - x * r.GetZ();
		float z_ = x * r.GetY() - y * r.GetX();

		return new Vector4f(x_, y_, z_, 0);
	}

	public Vector4f normalizacio()
	{
		float hosszusag = hossz();

		return new Vector4f(x / hosszusag, y / hosszusag, z / hosszusag, w / hosszusag);
	}

	public Vector4f forgatas(Vector4f axis, float szog)
	{
		float szinus = (float)Math.sin(-szog);
		float koszinusz = (float)Math.cos(-szog);

		return this.vektorialisSzorzat(axis.szorouz(szinus)).osszead((this.szorouz(koszinusz)).osszead(axis.szorouz(this.skalarSzorzat(axis.szorouz(1 - koszinusz))))); //Rotation on local Y
	}

//	public Vector3f rotate(Quaternion rotation)
//	{
//		Quaternion conjugate = rotation.conjugate();
//
//		Quaternion w = rotation.mul(this).mul(conjugate);
//
//		return new Vector3f(w.getX(), w.getY(), w.getZ());
//	}

	public Vector4f lerp(Vector4f dest, float lerpFactor)
	{
		return dest.kivon(this).szorouz(lerpFactor).osszead(this);
	}

	public Vector4f osszead(Vector4f r)
	{
		return new Vector4f(x + r.GetX(), y + r.GetY(), z + r.GetZ(), w + r.GetW());
	}

	public Vector4f osszead(float r)
	{
		return new Vector4f(x + r, y + r, z + r, w + r);
	}

	public Vector4f kivon(Vector4f r)
	{
		return new Vector4f(x - r.GetX(), y - r.GetY(), z - r.GetZ(), w - r.GetW());
	}

	public Vector4f kivon(float r)
	{
		return new Vector4f(x - r, y - r, z - r, w - r);
	}

	public Vector4f szorouz(Vector4f r)
	{
		return new Vector4f(x * r.GetX(), y * r.GetY(), z * r.GetZ(), w * r.GetW());
	}

	public Vector4f szorouz(float r)
	{
		return new Vector4f(x * r, y * r, z * r, w * r);
	}

	public Vector4f oszt(Vector4f r)
	{
		return new Vector4f(x / r.GetX(), y / r.GetY(), z / r.GetZ(), w / r.GetW());
	}

	public Vector4f oszt(float r)
	{
		return new Vector4f(x / r, y / r, z / r, w / r);
	}

	public Vector4f abszolut()
	{
		return new Vector4f(Math.abs(x), Math.abs(y), Math.abs(z), Math.abs(w));
	}

	public String toString()
	{
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
	}

	public float GetX()
	{
		return x;
	}

	public float GetY()
	{
		return y;
	}

	public float GetZ()
	{
		return z;
	}

	public float GetW()
	{
		return w;
	}

	public boolean equals(Vector4f r)
	{
		return x == r.GetX() && y == r.GetY() && z == r.GetZ() && w == r.GetW();
	}
}
