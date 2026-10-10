# Torch Slot

A Minecraft mod that adds an armor slot for torches and lanterns. Finally some convenient mobile lighting!

Torch Slot is a NeoForge mod that adds a **light slot** to the player inventory. Put a torch, lantern, or any
light-emitting block in it, and you light up the world around you as you move.

- **Minecraft:** Java Edition 26.3
- **Loader:** NeoForge 26.3.0.6-beta
- **Version:** 1.0.1

**Download:** grab the latest `.jar` from the [Releases page](../../releases/latest).

## Using it

- **Survival inventory:** click the small **+** on the left edge, beside the chestplate slot. A tab
  pops out with the light slot. The empty slot shows a ghost lantern outline. Click **−** to tuck it
  away again. The tab and the recipe book share the same spot, so opening one closes the other.
- **Creative inventory tab:** the slot is always shown, to the right of the armor.
- The slot holds one item. It accepts any block that gives off light, plus lava buckets.
- You give off the item's own light level: lantern 15, torch 14, soul torch 10, redstone torch 7, and so on.
- Other players see your light too.
- The item drops when you die, unless `keepInventory` is on.

## How the lighting works

The lighting runs on each player's own client. No light blocks are placed in the world.

- Brightness drops off smoothly with straight-line distance, in fractional light levels, so it
  doesn't step one block at a time.
- Mobs, items, and your own hand are re-lit every frame at their exact position.
- Terrain lighting is baked into chunk meshes. The nearby chunk sections are rebuilt whenever a
  light moves more than 0.1 blocks.
- Light fades in and out over about half a second when you equip or remove the item.

Known limit: the light is distance-based and doesn't flow around walls, so it can bleed through
thin walls within its radius. This works the same way as other dynamic-light mods.

## License

[Apache License 2.0](LICENSE)
