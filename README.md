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
  no permission: "&c&l(!) You don't have permission!"
  not dupable: "&c&l(!) Not Dupable."
  
settings:
  stack size: 64
  allow duping rarity books: true
  allow duping enchant books: true
  
blacklist:
  - "RAGE" # Prevents duping the rage enchant book
  - "ELITE" # Prevents duping the (unopened) elite enchantment book
```
</details>
