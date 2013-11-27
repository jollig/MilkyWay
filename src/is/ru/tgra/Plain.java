package is.ru.tgra;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

public class Plain {
	FloatBuffer vertexBuffer;
	FloatBuffer texCoordBuffer;
	
	Texture tex;
	
	public Plain(String textureImage)
	{
		vertexBuffer = BufferUtils.newFloatBuffer(12);
		vertexBuffer.put(new float[] {-0.5f, 0f, -0.5f, -0.5f, 0f, 0.5f,
				  					  0.5f, 0f, -0.5f, 0.5f, 0f, 0.5f
									/*-0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
									  0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
									  0.5f, -0.5f, -0.5f, 0.5f, 0.5f, -0.5f,
									  0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
									  0.5f, -0.5f, 0.5f, 0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, 0.5f, -0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, -0.5f, -0.5f, 0.5f, -0.5f,
									  -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f,
									  0.5f, 0.5f, -0.5f, 0.5f, 0.5f, 0.5f,
									  -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, 0.5f,
									  0.5f, -0.5f, -0.5f, 0.5f, -0.5f, 0.5f*/});
		vertexBuffer.rewind();
		
		texCoordBuffer = BufferUtils.newFloatBuffer(9);
		texCoordBuffer.put(new float[] {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f/*, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f,
										0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f*/});
		texCoordBuffer.rewind();
		
		Texture.setEnforcePotImages(false);
		tex = new Texture(Gdx.files.internal("assets/textures/" + textureImage));
	}
	
	public void draw() {
		Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		
		Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		
		Gdx.gl11.glEnable(Gdx.gl11.GL_BLEND);
		Gdx.gl11.glBlendFunc(Gdx.gl11.GL_SRC_ALPHA, Gdx.gl11.GL_ONE_MINUS_SRC_ALPHA);
		
		tex.bind();

		Gdx.gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordBuffer);
		Gdx.gl11.glNormal3f(0.0f, 0.0f, 0.0f);
		Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);
		//Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 16, 4);
		
		Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	}
}
