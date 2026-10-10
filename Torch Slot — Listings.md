# Torch Slot — Listings and Social Kit

Copy-paste text for CurseForge, GitHub and social media. Technical details are in
[Torch Slot — Handoff.md](Torch%20Slot%20—%20Handoff.md).

> **Before posting:** the images in `docs/images/` are mockups. Swap in real in-game screenshots
> and a short clip (see the [shot list](#screenshot-and-clip-shot-list)) before the store page
> goes live. Everything below only claims what the mod actually does in v1.0.1.

## Quick facts

| | |
|---|---|
| Name | Torch Slot |
| Tagline | Finally some convenient mobile lighting! |
| Version | 1.0.1 |
| Minecraft | 26.3 (Java) |
| Loader | NeoForge |
| Environment | Client **and** server |
| License | Apache-2.0 |
| Source | https://github.com/Ty10y/Torch-Slot |

### Taglines (pick one)

- Finally some convenient mobile lighting!
- Wear your light. Leave the torches at home.
- A slot for your lantern. Light that follows you.
- Your chestplate has a new neighbour.

---

## CurseForge

### Project name

```
Torch Slot
```

### Summary (short description field)

```
Adds a light slot beside your chestplate. Put in a torch, lantern or any light block and you glow, with smooth light that follows you as you move.
```

### Suggested categories

- **Utility & QoL** (primary)
- **Adventure and RPG**
- **Armor, Tools, and Weapons**

### Tags and keywords

`dynamic lights`, `lantern`, `torch`, `light`, `equipment slot`, `caving`, `quality of life`, `vanilla+`

### Description (markdown, paste into the editor)

> Image links need to be absolute URLs. After `docs/images` is pushed, use
> `https://raw.githubusercontent.com/Ty10y/Torch-Slot/main/docs/images/<file>.png`, or upload
> screenshots to the CurseForge gallery and link those.

```markdown
# Torch Slot

**Finally some convenient mobile lighting!**

Torch Slot adds one new equipment slot to your inventory: a **light slot**, right next to your chestplate.
Put a torch, lantern, glowstone or any other light block in it, and you light up the world around you as you walk.
No more spamming torches down every tunnel, and no more giving up your offhand.

![Light slot popped out](https://raw.githubusercontent.com/Ty10y/Torch-Slot/main/docs/images/inventory-open-lantern.png)

## How to use it

1. Open your inventory.
2. Click the little **+** beside your chestplate slot. A small tab pops out with the light slot.
   The empty slot shows a ghost lantern outline, just like the armor slots show their armor.
3. Drop in any light source. That's it: you're glowing.

Click **−** to tuck the tab away again. In creative mode, the slot is always shown right of the armor.

## Features

- **Any light block works.** Torches, lanterns, soul torches, glowstone, sea lanterns, jack o'lanterns, froglights, end rods, lava buckets, and modded light blocks too.
- **You glow as bright as the item.** Lantern 15, torch 14, soul torch 10, redstone torch 7.
- **Smooth, moving light.** Brightness fades gradually with distance, with no blocky steps, and the light glides along with you as you walk.
- **Mobs and items light up live.** Creatures, dropped items and even your own hand brighten and dim every frame as you move past.
- **Soft fade.** Equipping or removing your light fades it in and out instead of snapping.
- **Multiplayer ready.** Everyone sees everyone else's light.
- **Plays fair.** The slot holds one item, and it drops on death like the rest of your inventory (kept with `keepInventory`).
- **Looks vanilla.** The pop-out tab and ghost icon match Minecraft's own inventory style.

## Good to know

- Needs to be installed on **both the client and the server**.
- The light is distance-based, so it can shine through thin walls.
- Lighting rebuilds nearby chunks as you move. Fine on most PCs, but very large groups of glowing players may cost some FPS.
- Play-tested in a 30-mod modpack, working alongside Sodium, Iris, LambDynamicLights, ImmediatelyFast, JourneyMap, Jade, Sophisticated Backpacks and Mouse Tweaks.

## Requirements

- Minecraft **26.3** (Java Edition)
- **NeoForge** 26.3.0.6-beta or newer

## Links

- Source and issues: https://github.com/Ty10y/Torch-Slot
- License: Apache-2.0
```

### File upload: release notes and changelog field

**v1.0.1**

```
Torch Slot 1.0.1

- The + button beside the chestplate is now always visible, even with the recipe book open
- Clicking + while the recipe book is open closes the book and opens the light slot
- Opening the recipe book while the light slot is open tucks the slot away

Requires NeoForge for Minecraft 26.3, on client and server.
```

**v1.0.0**

```
Torch Slot 1.0.0 — first release

- New light slot beside the chestplate (click the + in your inventory)
- Any light-emitting block, or a lava bucket, makes you glow at its light level
- Smooth dynamic lighting that follows you; mobs, items and your hand update every frame
- Light fades in/out when equipped or removed
- Works in multiplayer; item drops on death unless keepInventory
- Creative inventory support

Requires NeoForge for Minecraft 26.3, on client and server.
```

- **Release type:** Release
- **Game version:** 26.3
- **Mod loader:** NeoForge
- **Environment:** Client and Server

---

## GitHub

### Repository "About" box

**Description:**

```
A Minecraft mod (NeoForge 26.3) that adds an armor slot for torches and lanterns. Finally some convenient mobile lighting!
```

**Topics:**

```
minecraft  minecraft-mod  neoforge  dynamic-lights  java  lighting  minecraft-26
```

### Release: v1.0.1

**Tag:** `v1.0.1`, **Title:** `Torch Slot 1.0.1`, **Attach:** `torch_slot-1.0.1.jar`

```markdown
Small update to how the light slot gets along with the recipe book.

### Changes
- The **+** beside your chestplate is now **always visible**. Before, it disappeared while the recipe book was open, which made the slot look missing.
- Clicking **+** while the recipe book is open now closes the book and opens the light slot.
- Opening the recipe book while the light slot is open tucks the slot away.

### Install
Replace the old `torch_slot` jar in your `mods` folder with `torch_slot-1.0.1.jar`, on both client and server. Your worn light items are kept.

### Compatibility
Play-tested in a 30-mod NeoForge 26.3 modpack, working alongside Sodium, Iris, LambDynamicLights, ImmediatelyFast, JourneyMap, Jade, Sophisticated Backpacks and Mouse Tweaks.
```

### Release: v1.0.0

**Tag:** `v1.0.0`, **Title:** `Torch Slot 1.0.0`, **Attach:** `torch_slot-1.0.0.jar`

```markdown
First release of **Torch Slot** for Minecraft 26.3 (NeoForge).

### What's in it
- A new **light slot** beside your chestplate. Click the **+** in your inventory to pop it out.
- Put in any light-emitting block (or a lava bucket) and you emit its light level.
- **Smooth dynamic lighting:** the light fades with distance and follows you as you move. Mobs, items and your hand update every frame.
- Light fades in/out when you equip or remove it.
- Multiplayer: everyone sees everyone's light.
- Drops on death unless `keepInventory` is on.
- Creative inventory support.

### Install
1. Install NeoForge 26.3.0.6-beta or newer.
2. Drop `torch_slot-1.0.0.jar` into your `mods` folder, **on both client and server**.

### Known limits
- Distance-based light can shine through thin walls.
```

---

## Social media

### X / Twitter (≤ 280 characters)

```
New Minecraft mod: Torch Slot 🔦

A light slot right next to your chestplate. Drop in a torch or lantern and you glow, with smooth light that follows you through caves.

NeoForge 26.3 · free & open source
https://github.com/Ty10y/Torch-Slot

#Minecraft #MinecraftMods #NeoForge
```

Alternative, shorter, for a post with a clip:

```
Stopped placing torches. Started wearing one. 🏮

Torch Slot: a light slot next to your chestplate, NeoForge 26.3.
#Minecraft #MinecraftMods
```

### Bluesky / Threads / Mastodon

```
I made a Minecraft mod: Torch Slot 🏮

It adds a little light slot next to your chestplate. Put a torch, lantern or glowstone in it and you carry that light with you: smooth falloff, follows you as you walk, and mobs light up as you pass.

NeoForge 26.3, open source (Apache-2.0):
https://github.com/Ty10y/Torch-Slot
```

### Reddit (r/feedthebeast, r/MinecraftMod, r/Minecraft)

**Title:**

```
[NeoForge 26.3] Torch Slot: wear a torch or lantern in a new slot next to your chestplate and glow as you walk
```

**Body:**

```markdown
I made a small vanilla-style mod called **Torch Slot**.

**What it does:** there's a little **+** beside your chestplate slot. Click it and a slot pops out (it even has a ghost lantern outline like the armor slots). Put any light source in it (torch, lantern, glowstone, sea lantern, lava bucket, modded lights) and you emit that light level.

**The lighting is smooth:** brightness falls off with real distance instead of block-by-block steps, the light follows you as you move, and mobs, items and your hand update every frame. It fades in and out when you equip or remove it.

- Works in multiplayer (everyone sees everyone's light)
- Drops on death unless keepInventory
- Needs to be on client and server
- Plays nicely with Sodium, Iris and LambDynamicLights (tested in a 30-mod pack)
- Known limit: distance-based light can shine through thin walls

Source and download: https://github.com/Ty10y/Torch-Slot (Apache-2.0)

Feedback welcome, especially on performance with lots of players!
```

> r/Minecraft has strict self-promotion rules. Post a clip with a short title there, and put the
> link in a comment if the rules allow it.

### Discord announcement

```
🏮 **Torch Slot 1.0.1 is out!**
A new light slot next to your chestplate. Drop in a torch, lantern or any light block and you glow, with smooth light that follows you as you move.

• Any light block (and lava buckets)
• Smooth falloff, mobs and items light up live
• Multiplayer ready · drops on death unless keepInventory
• NeoForge 26.3 · client + server

📥 https://github.com/Ty10y/Torch-Slot/releases/latest
```

### YouTube Shorts / TikTok caption

```
POV: you never place a torch again 🏮 #minecraft #minecraftmods #neoforge #minecraftcave
```

---

## Screenshot and clip shot list

Take these with shaders and other mods off, at GUI scale 3 or 4, and hide the HUD (F1) for the
world shots.

| # | Shot | Purpose |
|---|---|---|
| 1 | Inventory with the "+" visible, then the tab popped out with the ghost lantern | Shows the UI. Use as CurseForge gallery image 1. |
| 2 | Inventory with a lantern in the slot | Gallery image 2. |
| 3 | Dark cave, player with a lantern, third person (F5) | Hero image or project logo background. |
| 4 | Same cave spot with and without the lantern, side by side | Before/after comparison. |
| 5 | Soul torch versus lantern brightness | Shows that light levels differ. |
| 6 | Two players in a dark area, both glowing | Multiplayer. |
| **Clip (10–20 s)** | Walk down a dark mineshaft. Open the inventory, click +, drop in a lantern, close it. The tunnel lights up and the light glides along as you walk. | Social and Shorts post, CurseForge video. |

**Project icon idea:** a lantern sitting in an inventory slot, with a small "+" badge in the
corner, on a dark-blue background. Make it 400×400 or larger, and square.
