package is.ru.tgra;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.BufferUtils;

public class Sphere
{
	private int stacks;
	private int slices;
	private float texS;
	private float texT;
	private FloatBuffer vertexBuffer;
	private FloatBuffer normalBuffer;
	private FloatBuffer texCoordBuffer;
	private int vertexCount;
	Texture tex;
	
	public Sphere(int i_stacks, int i_slices, String filename) {
		stacks = i_stacks;
		slices = i_slices;
		texT = 1.0f/i_stacks;
		texS = 1.0f/i_slices;
		vertexCount = 0;
		float[] array = new float[(stacks)*(slices+1)*6];
		float[] textureArray = new float[(stacks)*(slices+1)*4];
		float stackInterval = (float)Math.PI / (float)stacks;
		float sliceInterval = 2.0f*(float)Math.PI / (float)slices;
		float stackAngle, sliceAngle;
		for(int stackCount = 0; stackCount < stacks; stackCount++) {
			stackAngle = stackCount * stackInterval;
			for(int sliceCount = 0; sliceCount < slices+1; sliceCount++) {
				sliceAngle = sliceCount * sliceInterval;
				array[vertexCount*3] = 	 (float)Math.sin(stackAngle) * (float)Math.cos(sliceAngle);
				array[vertexCount*3 + 1] = (float)Math.cos(stackAngle);
				array[vertexCount*3 + 2] = (float)Math.sin(stackAngle) * (float)Math.sin(sliceAngle);

				array[vertexCount*3 + 3] = (float)Math.sin(stackAngle + stackInterval) * (float)Math.cos(sliceAngle);
				array[vertexCount*3 + 4] = (float)Math.cos(stackAngle + stackInterval);
				array[vertexCount*3 + 5] = (float)Math.sin(stackAngle + stackInterval) * (float)Math.sin(sliceAngle);
				
				textureArray[vertexCount*2] = sliceCount * texS + texS;
				textureArray[vertexCount*2 + 1] = stackCount * texT;
				textureArray[vertexCount*2 + 2] = sliceCount * texS + texS;
				textureArray[vertexCount*2 + 3] = stackCount * texT + texT;
				
				vertexCount += 2;
			}
		}
		vertexBuffer = BufferUtils.newFloatBuffer(vertexCount*3);
		vertexBuffer.put(array);
		vertexBuffer.rewind();
		normalBuffer = BufferUtils.newFloatBuffer(vertexCount*3);
		normalBuffer.put(array);
		normalBuffer.rewind();
		texCoordBuffer = BufferUtils.newFloatBuffer(vertexCount*2);
		texCoordBuffer.put(textureArray);
		texCoordBuffer.rewind();
		
		Texture.setEnforcePotImages(false);
		tex = new Texture(Gdx.files.internal("assets/textures/" + filename));
	}

	public void draw() {
		//Gdx.gl11.glShadeModel(GL11.GL_SMOOTH);
		Gdx.gl11.glEnableClientState(GL11.GL_NORMAL_ARRAY);
		Gdx.gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, vertexBuffer);
		Gdx.gl11.glNormalPointer(GL11.GL_FLOAT, 0, normalBuffer);
		
		Gdx.gl11.glEnable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		Gdx.gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, texCoordBuffer);
		tex.bind();
		
		for(int i = 0; i < vertexCount; i += (slices+1)*2) {
			Gdx.gl11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, i, (slices+1)*2);
		}
		
		Gdx.gl11.glDisable(GL11.GL_TEXTURE_2D);
		Gdx.gl11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	}
}
