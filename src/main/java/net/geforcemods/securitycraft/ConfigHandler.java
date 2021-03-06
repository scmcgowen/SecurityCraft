package net.geforcemods.securitycraft;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class ConfigHandler {

	public static final ForgeConfigSpec CLIENT_SPEC;
	public static final Client CLIENT;
	public static final ForgeConfigSpec SERVER_SPEC;
	public static final Server SERVER;

	static {
		Pair<Client,ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
		Pair<Server,ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);

		CLIENT_SPEC = clientSpecPair.getRight();
		CLIENT = clientSpecPair.getLeft();
		SERVER_SPEC = serverSpecPair.getRight();
		SERVER = serverSpecPair.getLeft();
	}

	public static class Client
	{
		public BooleanValue sayThanksMessage;
		public DoubleValue cameraSpeed;

		Client(ForgeConfigSpec.Builder builder)
		{
			sayThanksMessage = builder
					.translation("config.securitycraft:sayThanksMessage")
					.comment("Display a 'tip' message at spawn?")
					.define("sayThanksMessage", true);

			cameraSpeed = builder
					.translation("config.securitycraft:cameraSpeed")
					.comment("How fast can you rotate when mounted to a camera and holding W-A-S-D?")
					.defineInRange("cameraSpeed", 2.0D, 0.0D, Double.MAX_VALUE);
		}
	}

	public static class Server
	{
		public BooleanValue allowCodebreakerItem;
		public BooleanValue allowAdminTool;
		public BooleanValue shouldSpawnFire;
		public BooleanValue ableToBreakMines;
		public BooleanValue ableToCraftKeycard1;
		public BooleanValue ableToCraftKeycard2;
		public BooleanValue ableToCraftKeycard3;
		public BooleanValue ableToCraftKeycard4;
		public BooleanValue ableToCraftKeycard5;
		public BooleanValue ableToCraftLUKeycard;
		public BooleanValue smallerMineExplosion;
		public BooleanValue mineExplodesWhenInCreative;
		public IntValue laserBlockRange;
		public IntValue inventoryScannerRange;
		public IntValue maxAlarmRange;
		public BooleanValue allowBlockClaim;
		public BooleanValue respectInvisibility;
		public BooleanValue reinforcedBlockTint;
		public BooleanValue ableToCraftMines;
		public BooleanValue retinalScannerFace;

		Server(ForgeConfigSpec.Builder builder)
		{
			allowCodebreakerItem = builder
					.translation("config.securitycraft:isCodebreakerAllowed")
					.comment("Can the codebreaker be used?")
					.define("allowCodebreakerItem", true);

			allowAdminTool = builder
					.translation("config.securitycraft:allowAdminTool")
					.comment("Can the admin tool be used?")
					.define("allowAdminTool", false);

			shouldSpawnFire = builder
					.translation("config.securitycraft:shouldSpawnFire")
					.comment("Should mines spawn fire after exploding?")
					.define("shouldSpawnFire", true);

			//TODO: worldRestart() -> mcRestart() once available
			ableToBreakMines = builder
					.translation("config.securitycraft:ableToBreakMines")
					.comment("Should players be able to break a mine without it exploding?")
					.worldRestart()
					.define("ableToBreakMines", true);

			//TODO: worldRestart() -> mcRestart() once available
			ableToCraftKeycard1 = builder
					.translation("config.securitycraft:ableToCraftKeycard1")
					.comment("Is the level 1 keycard craftable?")
					.worldRestart()
					.define("ableToCraftKeycard1", true);

			//TODO: worldRestart() -> mcRestart() once available
			ableToCraftKeycard2 = builder
					.translation("config.securitycraft:ableToCraftKeycard2")
					.comment("Is the level 2 keycard craftable?")
					.worldRestart()
					.define("ableToCraftKeycard2", true);

			//TODO: worldRestart() -> mcRestart() once available
			ableToCraftKeycard3 = builder
					.translation("config.securitycraft:ableToCraftKeycard3")
					.comment("Is the level 3 keycard craftable?")
					.worldRestart()
					.define("ableToCraftKeycard3", true);

			//TODO: worldRestart() -> mcRestart() once available
			ableToCraftKeycard4 = builder
					.translation("config.securitycraft:ableToCraftKeycard4")
					.comment("Is the level 4 keycard craftable?")
					.worldRestart()
					.define("ableToCraftKeycard4", true);

			//TODO: worldRestart() -> mcRestart() once available
			ableToCraftKeycard5 = builder
					.translation("config.securitycraft:ableToCraftKeycard5")
					.comment("Is the level 5 keycard craftable?")
					.worldRestart()
					.define("ableToCraftKeycard5", true);

			//TODO: worldRestart() -> mcRestart() once available
			ableToCraftLUKeycard = builder
					.translation("config.securitycraft:ableToCraftLUKeycard")
					.comment("Is the limited use keycard craftable?")
					.worldRestart()
					.define("ableToCraftLUKeycard", true);

			smallerMineExplosion = builder
					.translation("config.securitycraft:smallerMineExplosion")
					.comment("Should mines' explosions be smaller than usual.")
					.define("smallerMineExplosion", false);

			mineExplodesWhenInCreative = builder
					.translation("config.securitycraft:mineExplodesWhenInCreative")
					.comment("Should mines explode if broken while in Creative mode?")
					.define("mineExplodesWhenInCreative", true);

			laserBlockRange = builder
					.translation("config.securitycraft:laserBlockRange")
					.comment("From how many blocks away can a laser block connect to another laser block?")
					.defineInRange("laserBlockRange", 5, 0, Integer.MAX_VALUE);

			inventoryScannerRange = builder
					.translation("config.securitycraft:inventoryScannerRange")
					.comment("From how many blocks away can an inventory scanner connect to another inventory scanner?")
					.defineInRange("inventoryScannerRange", 2, 0, Integer.MAX_VALUE);

			maxAlarmRange = builder
					.translation("config.securitycraft:maxAlarmRange")
					.comment("What is the maximum value that can be set for an alarm's range option? Do note, that this may be limited by chunk loading distance. Higher values may also not be finetuneable.")
					.defineInRange("maxAlarmRange", 100, 1, Integer.MAX_VALUE);

			allowBlockClaim = builder
					.translation("config.securitycraft:allowBlockClaim")
					.comment("Allow claiming unowned blocks?")
					.define("allowBlockClaim", false);

			respectInvisibility = builder
					.translation("config.securitycraft:respectInvisibility")
					.comment("Should the sentry/inventory scanner/laser block/etc. ignore players and entities that are invisible?")
					.define("respect_invisibility", false);

			reinforcedBlockTint = builder
					.translation("config.securitycraft:reinforcedBlockTint")
					.comment("Should reinforced blocks' textures be slightly darker than their vanilla counterparts?")
					.define("reinforced_block_tint", true);

			ableToCraftMines = builder
					.translation("config.securitycraft:ableToCraftMines")
					.comment("Are all mines craftable?")
					.define("ableToCraftMines", true);

			retinalScannerFace = builder
					.translation("config.securitycraft:retinalScannerFace")
					.comment("Display owner face on retinal scanner?")
					.define("retinalScannerFace", true);
		}
	}
}
