package is.ru.tgra;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class GalaxyStarter {

	public static void main(String[] args)
	{
		new LwjglApplication(new Galaxy_3D(), "“I see Earth! It is so beautiful.” ― Yuri Gagarin", 1024, 768, false);
	}
}