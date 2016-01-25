package twintro.minecraft.modbuilder.editor.resources;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import twintro.minecraft.modbuilder.data.resources.ResourceDeserializer;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockModelResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockstateResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockstateResource.Variant;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;

public class BlockElement extends InventoryElement {
	public BaseBlockResource block;
	public BlockstateResource blockstate;
	public BlockModelResource blockModel;
	
	public static BlockElement getFromName(String name) throws Exception {
		BlockElement output = null;
		
		File blockFile = new File(ResourcePackIO.getURL(
				"assets/modbuilder/blocks/" + name + ".json"));
		if (blockFile.exists()){
			ResourceDeserializer deserializer = new ResourceDeserializer();
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(BaseBlockResource.class, deserializer);
			Gson gson = builder.create();
			
			BlockResource block = (BlockResource) 
					gson.fromJson(new FileReader(blockFile), BaseBlockResource.class);
			
			File blockstateFile = new File(ResourcePackIO.getURL(
					"assets/modbuilder/blockstates/" + name + ".json"));
			if (blockstateFile.exists()){
				BlockstateResource blockstate = 
						gson.fromJson(new FileReader(blockstateFile), BlockstateResource.class);
				
				output = new BlockElement();
				output.name = name;
				output.block = block;
				output.blockstate = blockstate;
				
				if (block.model.startsWith("modbuilder:")){
					String itemModelName = block.model.substring(11);
					File itemModelFile = new File(ResourcePackIO.getURL(
							"assets/modbuilder/models/item/" + itemModelName + ".json"));
					if (itemModelFile.exists()){
						ItemModelResource itemModel = 
								gson.fromJson(new FileReader(itemModelFile), ItemModelResource.class);
						output.itemModel = itemModel;
					}
				}
				
				String blockModelName = null;
				Map<String, Variant> blockstateVariants = blockstate.variants;
				if (blockstateVariants.containsKey("normal"))
					blockModelName = blockstateVariants.get("normal").model;
				else{
					Object[] variants = blockstateVariants.values().toArray();
					if (variants.length > 0) blockModelName = ((Variant) variants[0]).model;
				}
				
				if (blockModelName != null){
					if (blockModelName.startsWith("modbuilder:")){
						File blockModelFile = new File(ResourcePackIO.getURL(
								"assets/modbuilder/models/block/" + blockModelName.substring(11) + ".json"));
						if (blockModelFile.exists()){
							BlockModelResource blockModel = 
									gson.fromJson(new FileReader(blockModelFile), BlockModelResource.class);
							output.blockModel = blockModel;
						}
					}
				}
			}
		}
		return output;
	}
	
	public void setOpaqueAndCutout(){
		block.cutout = false;
		block.opaque = true;
		for (String textureName : blockModel.textures.values()){
			if (textureName.startsWith("modbuilder:") && hasTransparency(textureName)){
				block.cutout = true;
				block.opaque = false;
				break;
			}
		}
		if (blockModel.parent.equals("cross") || !block.solid){
			block.opaque = false;
		}
	}
	
	private boolean hasTransparency(String textureName){
		try {
			BufferedImage img = ImageIO.read(new File(ResourcePackIO.getURL("assets/modbuilder/textures/" + textureName.substring(11) + ".png")));
			for(int x=0;x<img.getWidth();x++)
				for(int y=0;y<img.getHeight();y++)
					if (new Color(img.getRGB(x, y),true).getAlpha()==0)
						return true;
			
			}
		catch (IOException e){
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public ImageIcon getImage() {
		try{
			String loc=ResourcePackIO.getURL("assets/modbuilder/textures/");
			if (blockModel.parent.equals("block/cross")) {
				return new ImageIcon(scaleImage(ImageIO.read(new File(loc+blockModel.textures.get("cross").split(":")[1] + ".png")),64,64));
			}
			else if (blockModel.parent.equals("block/cube_all")){
				BufferedImage img = scaleImage(ImageIO.read(new File(loc+blockModel.textures.get("all").split(":")[1] + ".png")),64,64);
				return new ImageIcon(scaleImage(get3DImage(img,img,img),64,64));
			}
			else if (blockModel.parent.equals("block/cube")){
				BufferedImage img1 = scaleImage(ImageIO.read(new File(loc+blockModel.textures.get("up").split(":")[1] + ".png")),64,64);
				BufferedImage img2 = scaleImage(ImageIO.read(new File(loc+blockModel.textures.get("south").split(":")[1] + ".png")),64,64);
				BufferedImage img3 = scaleImage(ImageIO.read(new File(loc+blockModel.textures.get("east").split(":")[1] + ".png")),64,64);
				return new ImageIcon(scaleImage(get3DImage(img1,img2,img3),64,64));
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return new ImageIcon("src/main/resources/icon.png");
	}
	
	private static BufferedImage get3DImage(BufferedImage img1, BufferedImage img2, BufferedImage img3){
		double a = Math.sqrt(2)/2;
		double b = a/Math.sqrt(3);
        BufferedImage img1t = transformImage(img1, a, b,-a,  b, 2*b  ,   0);
        BufferedImage img2t = transformImage(img2, a, b, 0,2*b, 2*b-a,   b);
        BufferedImage img3t = transformImage(img3, a,-b, 0,2*b, 2*b  , 2*b);
        
        BufferedImage bi = new BufferedImage((int)(b*256),(int)(b*256),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
	    g.drawImage(img1t, 0, 0, null);
	    g.drawImage(img2t, 0, 0, null);
	    g.drawImage(img3t, 0, 0, null);
		return bi;
	}
	
	private static BufferedImage transformImage(BufferedImage img, double m00, double m10, double m01, double m11, double m02, double m12){
        AffineTransform at = new AffineTransform(m00, m10, m01, m11, 64*m02, 64*m12);
        AffineTransformOp op = new AffineTransformOp(at, new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC));
        return op.filter(img, null);
	}
	
	private static BufferedImage scaleImage(BufferedImage img, int width, int height){
		BufferedImage bi = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = bi.createGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		return bi;
	}
}
