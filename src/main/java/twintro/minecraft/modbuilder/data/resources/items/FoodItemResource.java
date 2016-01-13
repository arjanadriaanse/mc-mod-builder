package twintro.minecraft.modbuilder.data.resources.items;

import java.util.List;
import java.util.Set;

/**
 * The extended item properties for food items.
 */
public class FoodItemResource extends BaseItemResource {
	/**
	 * The amount of hunger points restored when the food is eaten.
	 */
	public int amount;
	/**
	 * The amount of saturation points gained when the food is eaten. This increases the amount of time it takes before the player gets hungry again.
	 */
	public Float saturation;
	/**
	 * If the food can be given to a wolf. Most meat can be given, but things like bread cannot.
	 */
	public Boolean wolf;
	/**
	 * If the food is edible when at full hunger points. Most food can't be eaten when your hunger bar is full, but for example golden apples can (to gain the effects).
	 */
	public Boolean alwaysedible;
	/**
	 * The effects the player will gain when eating the food. Every effect is an integer array of length 3:
	 * The first integer is the ID of the effect (this ranges from 1 to 23).
	 * The second integer is the duration of the effect, calculated in game ticks. 1 second equals 20 game ticks.
	 * The third integer is the amplifier of the effect. An amplifier of 0 is the default effect.
	 * Some effects don't have any change with higher amplifiers (such as fire resistance).
	 */
	public Set<Integer[]> effects; //van de vorm {PotionID (van 1-23), PotionDuration (in ticks), PotionAmplifier (0 = effectx1}

	public FoodItemResource() {
		type = ItemType.food;
	}
}
