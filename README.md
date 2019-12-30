# RandomDupe
Addon for RandomPackage for duping books (Opened or Unopened)

~~For 1.13.x/1.12.x - Use Build 2~~ 1.13/1.12 wont work on new versions of rp<br>
For 1.8.x - Use [Latest](https://github.com/Puyodead1/RandomDupe/releases/latest)

## Commands:
- /dupe - main dupe command

## Permissions:
- randomdupe.dupe - permission for /dupe

## Configuration File:
<details>
  <summary>Click to expand</summary>
  
```
messages:
  not dupable: # Valid placeholders: {ENCHANT_NAME}, {RARITY_NAME}, {DUST_NAME}
    ENCHANT: "&c&l(!) {ENCHANT_NAME} &cenchant is not dupable!"
    RARITY: "&c&l(!) {RARITY_NAME} &crarity books are not dupable!"
    WHITE_SCROLL: "&c&l(!) White Scrolls &care not dupable!"
    BLACK_SCROLL: "&c&l(!) Black Scrolls &care not dupable!"
    TRANSMOG_SCROLL: "&c&l(!) Transmog Scrolls &care not dupable!"
    MAGIC_DUST: "&c&l(!) {DUST_NAME} &cis not dupable!"
  no permission: "&c&l(!) &cYou don't have permission!"
  item not dupable: "&c&l(!) {ITEM_TYPE} &cis not dupable!" # Valid placeholders: {ITEM_DISPLAYNAME} and {ITEM_TYPE}
  dupe success: "&a&l(!) {ITEM_DISPLAYNAME} &aduped successfully!" # Valid placeholders: {ITEM_DISPLAYNAME} and {ITEM_TYPE}
  cannot dupe air: "&c&l(!) &cYou can't dupe air!"
  identifier: "&eIdentifier for item &6&l{ITEM_DISPLAYNAME} &eis &6&l{IDENTIFIER}&e."
  no identifier: "&c&l(!) {ITEM_DISPLAYNAME} &cdoes not have an identifier!"
  
settings:
  stack size: 64
  allow duping:
    ENCHANT: true
    RARITY: true
    WHITE_SCROLL: false
    BLACK_SCROLL: false
    TRANSMOG_SCROLL: false
    MAGIC_DUST: false # blacklist certain dusts with the blacklist below
  
blacklist: # All names in this list must be UPPERCASE
  - "ENCHANT:RAGE" # Prevents duping the rage enchant book
  - "RARITY:ELITE" # Prevents duping the (unopened) elite enchantment book
  - "DUST:ELITE_REGULAR" # Prevents duping regular elite magic dust
```
</details>
https://discord.gg/tMzrSxQ
