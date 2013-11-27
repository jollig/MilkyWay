package is.ru.tgra;

import is.ru.tgra.Point3D;
import is.ru.tgra.Vector3D;

public class Vector3D {
	public float x;
	public float y;
	public float z;
	
	public Vector3D(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float length() {
		return (float)Math.sqrt(x*x+y*y+z*z);
	}
	
	public void normalize() {
		float len = length();
		x = x / len;
		y = y / len;
		z = z / len;
	}
	
	public static Vector3D normalize(Vector3D v) {
		float len = v.length();
		v.x = v.x / len;
		v.y = v.y / len;
		v.z = v.z / len;
		return v;
	}
	
	public static Vector3D difference(Point3D P1, Point3D P2) {
		return new Vector3D(P1.x - P2.x, P1.y - P2.y, P1.z - P2.z);
	}
	
	public static float dot(Vector3D v1, Vector3D v2) {
		return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
	}
	
	public static Vector3D cross(Vector3D P1, Vector3D P2) {
		return new Vector3D(P1.y*P2.z - P1.z*P2.y, P1.z*P2.x - P1.x*P2.z, P1.x*P2.y - P1.y*P2.x);
	}
	
	public static Vector3D mult(float s, Vector3D v) {
		return new Vector3D(s*v.x, s*v.y, s*v.z);
	}

	public static Vector3D sum(Vector3D v1, Vector3D v2) {
		return new Vector3D(v1.x+v2.x, v1.y+v2.y, v1.z+v2.z);
	}
}
