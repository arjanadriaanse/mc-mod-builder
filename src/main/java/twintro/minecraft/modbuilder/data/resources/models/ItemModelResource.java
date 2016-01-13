package twintro.minecraft.modbuilder.data.resources.models;

import java.util.Map;

/**
 * Contains properties for the item model. This is what the model looks like when you hold it.
 * Blocks also have an item model, which decides how they look when placed in your inventory.
 */
public class ItemModelResource {
	public String parent;
	/**
	 * Map of all textures used for the model. The first string is an indicator for the place of the texture, like "layer0", and the second string is a reference to the file location of the texture.
	 */
	public Map<String, String> textures;
	/**
	 * How the item is displayed when you are holding it (on screen, so not in your inventory).
	 */
	public Display display;
	
	public static class Display {
		/**
		 * How the item looks in your hand when viewing from third person mode. 
		 */
		public Perspective thirdperson;
		/**
		 * How the item looks in your hand when viewing from first person mode. 
		 */
		public Perspective firstperson;
		
		private Display(int type){
			switch(type){
			case 0:
				firstperson = Perspective.firstPerson();
				thirdperson = Perspective.regularThirdPerson();
				break;
			case 1:
				firstperson = Perspective.firstPerson();
				thirdperson = Perspective.toolThirdPerson();
				break;
			case 2:
				thirdperson = Perspective.blockThirdPerson();
			}
		}
		
		public static Display regular(){
			return new Display(0);
		}
		
		public static Display tool(){
			return new Display(1);
		}
		
		public static Display block(){
			return new Display(2);
		}
	}
	
	public static class Perspective {
		public double[] rotation;
		public double[] translation;
		public double[] scale;

		private Perspective(double[] r, double[] t, double[] s){
			rotation = r;
			translation = t;
			scale = s;
		}
		
		public static Perspective firstPerson(){
			return new Perspective(new double[]{0, -135, 25},
					new double[]{0, 4, 2},
					new double[]{1.7, 1.7, 1.7});
		}
		
		public static Perspective regularThirdPerson(){
			return new Perspective(new double[]{-90, 0, 0},
					new double[]{0, 1, -3},
					new double[]{0.55, 0.55, 0.55});
		}
		
		public static Perspective toolThirdPerson(){
			return new Perspective(new double[]{0, 90, -35},
					new double[]{0, 1.25, -3.5},
					new double[]{0.85, 0.85, 0.85});
		}
		
		public static Perspective blockThirdPerson(){
			return new Perspective(new double[]{10, -45, 170},
					new double[]{0, 1.5, -2.75},
					new double[]{0.375, 0.375, 0.375});
		}
	}
}
