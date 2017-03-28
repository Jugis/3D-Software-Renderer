public class Matrix4f
{
	private float[][] m;

	public Matrix4f()
	{
		m = new float[4][4];
	}

	public Matrix4f egysegMatrix()
	{
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;

		return this;
	}

	public Matrix4f kepernyoInicializalo(float felSzelesseg, float felMagassag)
	{
		m[0][0] = felSzelesseg;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = felSzelesseg;
		m[1][0] = 0;	m[1][1] = -felMagassag;	m[1][2] = 0;	m[1][3] = felMagassag;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;

		return this;
	}

        /**
         * 
         * @param x
         * @param y
         * @param z
         * @return 
         */
	public Matrix4f mozgatas(float x, float y, float z)
	{
		m[0][0] = 1;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = x;
		m[1][0] = 0;	m[1][1] = 1;	m[1][2] = 0;	m[1][3] = y;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = 1;	m[2][3] = z;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;

		return this;
	}

	public Matrix4f forgatas(float x, float y, float z, float angle)
	{
		float sin = (float)Math.sin(angle);
		float cos = (float)Math.cos(angle);

		m[0][0] = cos+x*x*(1-cos); m[0][1] = x*y*(1-cos)-z*sin; m[0][2] = x*z*(1-cos)+y*sin; m[0][3] = 0;
		m[1][0] = y*x*(1-cos)+z*sin; m[1][1] = cos+y*y*(1-cos);	m[1][2] = y*z*(1-cos)-x*sin; m[1][3] = 0;
		m[2][0] = z*x*(1-cos)-y*sin; m[2][1] = z*y*(1-cos)+x*sin; m[2][2] = cos+z*z*(1-cos); m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;

		return this;
	}

	public Matrix4f forgatas(float x, float y, float z)
	{
		Matrix4f rx = new Matrix4f();
		Matrix4f ry = new Matrix4f();
		Matrix4f rz = new Matrix4f();

		rz.m[0][0] = (float)Math.cos(z);                rz.m[0][1] = -(float)Math.sin(z);   rz.m[0][2] = 0;                     rz.m[0][3] = 0;
		rz.m[1][0] = (float)Math.sin(z);                rz.m[1][1] = (float)Math.cos(z);    rz.m[1][2] = 0;                     rz.m[1][3] = 0;
		rz.m[2][0] = 0;					rz.m[2][1] = 0;                     rz.m[2][2] = 1;                     rz.m[2][3] = 0;
		rz.m[3][0] = 0;					rz.m[3][1] = 0;                     rz.m[3][2] = 0;                     rz.m[3][3] = 1;

		rx.m[0][0] = 1;					rx.m[0][1] = 0;                     rx.m[0][2] = 0;                     rx.m[0][3] = 0;
		rx.m[1][0] = 0;					rx.m[1][1] = (float)Math.cos(x);    rx.m[1][2] = -(float)Math.sin(x);   rx.m[1][3] = 0;
		rx.m[2][0] = 0;					rx.m[2][1] = (float)Math.sin(x);    rx.m[2][2] = (float)Math.cos(x);    rx.m[2][3] = 0;
		rx.m[3][0] = 0;					rx.m[3][1] = 0;                     rx.m[3][2] = 0;			rx.m[3][3] = 1;

		ry.m[0][0] = (float)Math.cos(y);                ry.m[0][1] = 0;                     ry.m[0][2] = -(float)Math.sin(y);   ry.m[0][3] = 0;
		ry.m[1][0] = 0;					ry.m[1][1] = 1;                     ry.m[1][2] = 0;                     ry.m[1][3] = 0;
		ry.m[2][0] = (float)Math.sin(y);                ry.m[2][1] = 0;                     ry.m[2][2] = (float)Math.cos(y);    ry.m[2][3] = 0;
		ry.m[3][0] = 0;					ry.m[3][1] = 0;                     ry.m[3][2] = 0;			ry.m[3][3] = 1;

		m = rz.szorzas(ry.szorzas(rx)).getM();

		return this;
	}

	public Matrix4f skalazas(float x, float y, float z)
	{
		m[0][0] = x;	m[0][1] = 0;	m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;	m[1][1] = y;	m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = z;	m[2][3] = 0;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;

		return this;
	}

	public Matrix4f perspektivaInicializalas(float fov, float aspectRatio, float zKozel, float zMessze)
	{
		float felTanges = (float)Math.tan(fov / 2);
		float zTav = zKozel - zMessze;

		m[0][0] = 1.0f / (felTanges * aspectRatio);             m[0][1] = 0;					m[0][2] = 0;	m[0][3] = 0;
		m[1][0] = 0;						m[1][1] = 1.0f / felTanges;                     m[1][2] = 0;	m[1][3] = 0;
		m[2][0] = 0;						m[2][1] = 0;					m[2][2] = (-zKozel -zMessze)/zTav;	m[2][3] = 2 * zMessze * zKozel / zTav;
		m[3][0] = 0;						m[3][1] = 0;					m[3][2] = 1;	m[3][3] = 0;


		return this;
	}
/*
	public Matrix4f InitOrthographic(float left, float right, float bottom, float top, float near, float far)
	{
		float width = right - left;
		float height = top - bottom;
		float depth = far - near;

		m[0][0] = 2/width;m[0][1] = 0;	m[0][2] = 0;	m[0][3] = -(right + left)/width;
		m[1][0] = 0;	m[1][1] = 2/height;m[1][2] = 0;	m[1][3] = -(top + bottom)/height;
		m[2][0] = 0;	m[2][1] = 0;	m[2][2] = -2/depth;m[2][3] = -(far + near)/depth;
		m[3][0] = 0;	m[3][1] = 0;	m[3][2] = 0;	m[3][3] = 1;

		return this;
	}
*/
        /*
	public Matrix4f forgatas(Vector4f elore, Vector4f fel)
	{
		Vector4f f = elore.Normalized();

		Vector4f r = fel.Normalized();
		r = r.Cross(f);

		Vector4f u = f.Cross(r);

		return forgatas(f, u, r);
	}
*/
/*
	public Matrix4f forgatas(Vector4f elore, Vector4f fel, Vector4f jobbra)
	{
		Vector4f f = elore;
		Vector4f r = jobbra;
		Vector4f u = fel;

		m[0][0] = r.GetX();	m[0][1] = r.GetY();	m[0][2] = r.GetZ();	m[0][3] = 0;
		m[1][0] = u.GetX();	m[1][1] = u.GetY();	m[1][2] = u.GetZ();	m[1][3] = 0;
		m[2][0] = f.GetX();	m[2][1] = f.GetY();	m[2][2] = f.GetZ();	m[2][3] = 0;
		m[3][0] = 0;		m[3][1] = 0;		m[3][2] = 0;		m[3][3] = 1;

		return this;
	}
*/
	public Vector4f transzformator(Vector4f r)
	{
		return new Vector4f(m[0][0] * r.GetX() + m[0][1] * r.GetY() + m[0][2] * r.GetZ() + m[0][3] * r.GetW(),
		                    m[1][0] * r.GetX() + m[1][1] * r.GetY() + m[1][2] * r.GetZ() + m[1][3] * r.GetW(),
		                    m[2][0] * r.GetX() + m[2][1] * r.GetY() + m[2][2] * r.GetZ() + m[2][3] * r.GetW(),
							m[3][0] * r.GetX() + m[3][1] * r.GetY() + m[3][2] * r.GetZ() + m[3][3] * r.GetW());
	}

	public Matrix4f szorzas(Matrix4f r)
	{
		Matrix4f eredmeny = new Matrix4f();

		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				eredmeny.setMatrix(i, j, m[i][0] * r.getMatrix(0, j) +
						m[i][1] * r.getMatrix(1, j) +
						m[i][2] * r.getMatrix(2, j) +
						m[i][3] * r.getMatrix(3, j));
			}
		}

		return eredmeny;
	}

	public float[][] getM()
	{
		float[][] eredmeny = new float[4][4];

		for(int i = 0; i < 4; i++)
			for(int j = 0; j < 4; j++)
				eredmeny[i][j] = m[i][j];

		return eredmeny;
	}

	public float getMatrix(int x, int y)
	{
		return m[x][y];
	}

	public void setM(float[][] m)
	{
		this.m = m;
	}

	public void setMatrix(int x, int y, float ertek)
	{
		m[x][y] = ertek;
	}
}
